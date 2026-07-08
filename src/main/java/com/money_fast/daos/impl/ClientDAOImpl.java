package com.money_fast.daos.impl;

import com.money_fast.daos.ClientDAO;
import com.money_fast.beans.Client;
import com.money_fast.beans.enums.Sexe;
import com.money_fast.beans.enums.StatutClient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAOImpl implements ClientDAO {
	private final Connection connection;

	public ClientDAOImpl(Connection connection) {
		this.connection = connection;
	}

	@Override
	public boolean create(Client client) {
		if (existsByEmailOrPhone(client.getMail(), client.getNumeroTelephone())) {
			throw new RuntimeException("Erreur : Le numéro de téléphone ou l'adresse email existe déjà !");
		}

		String sql = "INSERT INTO clients (numero_telephone, nom, prenom, sexe, pays, devise_preferee, mail, password, statut_client, date_derniere_connexion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, client.getNumeroTelephone());
			ps.setString(2, client.getNom());
			ps.setString(3, client.getPrenom());
			ps.setString(4, client.getSexe().name().toLowerCase());
			ps.setInt(5, client.getPays());
			ps.setInt(6, client.getDevisePreferee());
			ps.setString(7, client.getMail());
			ps.setString(8, client.getPassword());
			ps.setString(9, client.getStatutClient().name().toLowerCase());
			ps.setTimestamp(10,
					client.getDateDerniereConnexion() != null ? Timestamp.valueOf(client.getDateDerniereConnexion())
							: null);

			ps.executeUpdate();
			try (ResultSet gk = ps.getGeneratedKeys()) {
				if (gk.next())
					client.setIdClient(gk.getInt(1));
			}

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(Client client) {
		String sql = "UPDATE clients SET numero_telephone = ?, nom = ?, prenom = ?, pays = ?, devise_preferee = ?, mail = ? WHERE id_client = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, client.getNumeroTelephone());
			ps.setString(2, client.getNom());
			ps.setString(3, client.getPrenom());
			ps.setInt(4, client.getPays());
			ps.setInt(5, client.getDevisePreferee());
			ps.setString(6, client.getMail());
			ps.setInt(7, client.getIdClient());
			ps.executeUpdate();

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(Integer idClient) {
		if (!canDeleteClient(idClient)) {
			throw new RuntimeException(
					"Impossible de supprimer le client : Des comptes possèdent un solde positif ou des transferts sont en cours.");
		}

		String deleteComptesSql = "DELETE FROM comptes WHERE id_client = ?";
		String deleteClientSql = "DELETE FROM clients WHERE id_client = ?";
		try {
			connection.setAutoCommit(false);
			try (PreparedStatement ps1 = connection.prepareStatement(deleteComptesSql);
					PreparedStatement ps2 = connection.prepareStatement(deleteClientSql)) {

				ps1.setInt(1, idClient);
				ps1.executeUpdate();

				ps2.setInt(1, idClient);
				ps2.executeUpdate();

				connection.commit();
				return true;
			} catch (SQLException e) {
				connection.rollback();
				throw e;
			} finally {
				connection.setAutoCommit(true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Client> findLike(Client client) {
		List<Client> clients = new ArrayList<>();
		StringBuilder sql = new StringBuilder("SELECT * FROM clients WHERE 1=1 ");

		// Construction dynamique de la requête selon les critères fournis
		if (client.getNom() != null && !client.getNom().isEmpty()) {
			sql.append("AND nom LIKE ? ");
		}
		if (client.getNumeroTelephone() != null && !client.getNumeroTelephone().isEmpty()) {
			sql.append("AND numero_telephone LIKE ? ");
		}
		if (client.getMail() != null && !client.getMail().isEmpty()) {
			sql.append("AND mail LIKE ? ");
		}

		try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
			int paramIndex = 1;

			// Injection dynamique des paramètres
			if (client.getNom() != null && !client.getNom().isEmpty()) {
				ps.setString(paramIndex++, "%" + client.getNom() + "%");
			}
			if (client.getNumeroTelephone() != null && !client.getNumeroTelephone().isEmpty()) {
				ps.setString(paramIndex++, "%" + client.getNumeroTelephone() + "%");
			}
			if (client.getMail() != null && !client.getMail().isEmpty()) {
				ps.setString(paramIndex++, "%" + client.getMail() + "%");
			}

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					clients.add(mapResultSetToClient(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return clients;
	}

	@Override
	public List<Client> findAll() {
		List<Client> clients = new ArrayList<>();
		StringBuilder sql = new StringBuilder("SELECT * FROM clients;");

		try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					clients.add(mapResultSetToClient(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return clients;
	}

	// private utilities
	private boolean existsByEmailOrPhone(String email, String phone) {
		String sql = "SELECT COUNT(*) FROM clients WHERE mail = ? OR numero_telephone = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, email);
			ps.setString(2, phone);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next())
					return rs.getInt(1) > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private boolean canDeleteClient(Integer idClient) {
		String sqlSolde = "SELECT SUM(solde) FROM comptes WHERE id_client = ?";
		String sqlTransferts = "SELECT COUNT(*) FROM transferts t "
				+ "JOIN comptes c ON t.id_compte_source = c.id_compte "
				+ "OR t.id_compte_destination = c.id_compte WHERE c.id_client = ? "
				+ "AND t.statut_transfert IN ('en attente', 'en cours')";

		try (PreparedStatement ps1 = connection.prepareStatement(sqlSolde);
				PreparedStatement ps2 = connection.prepareStatement(sqlTransferts)) {

			ps1.setInt(1, idClient);
			try (ResultSet rs1 = ps1.executeQuery()) {
				if (rs1.next() && rs1.getDouble(1) > 0)
					return false;
			}

			ps2.setInt(1, idClient);
			try (ResultSet rs2 = ps2.executeQuery()) {
				if (rs2.next() && rs2.getInt(1) > 0)
					return false;
			}

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private Client mapResultSetToClient(ResultSet rs) throws SQLException {
		Client c = new Client();
		c.setIdClient(rs.getInt("id_client"));
		c.setNumeroTelephone(rs.getString("numero_telephone"));
		c.setNom(rs.getString("nom"));
		c.setPrenom(rs.getString("prenom"));
		c.setSexe(Sexe.valueOf(rs.getString("sexe").toUpperCase()));
		c.setPays(rs.getInt("pays"));
		c.setDevisePreferee(rs.getInt("devise_preferee"));
		c.setMail(rs.getString("mail"));
		c.setStatutClient(StatutClient.valueOf(rs.getString("statut_client").toUpperCase().replace("É", "E")));
		c.setDateInscription(rs.getTimestamp("date_inscription").toLocalDateTime());
		return c;
	}
}