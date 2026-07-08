package com.money_fast.daos.impl;

import com.money_fast.beans.Compte;
import com.money_fast.beans.enums.StatutCompte;
import com.money_fast.daos.CompteDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CompteDAOImpl implements CompteDAO {
	private final Connection connection;

	public CompteDAOImpl(Connection connection) {
		this.connection = connection;
	}

	@Override
	public boolean create(Compte compte) {
		compte.setSolde(0.0);
		compte.setNumeroCompte(generateUniqueNumeroCompte());
		
		if (compte.getStatutCompte() == null) {
			compte.setStatutCompte(StatutCompte.ACTIF);
		}

		String sql = "INSERT INTO comptes (id_client, numero_compte, devise, solde, plalfond_journalier, plafond_transaction, statut_compte) VALUES (?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			ps.setInt(1, compte.getIdClient());
			ps.setInt(2, compte.getNumeroCompte());
			ps.setInt(3, compte.getDevise());
			ps.setDouble(4, compte.getSolde());
			ps.setDouble(5, compte.getPlafondJournalier());
			ps.setDouble(6, compte.getPlafondTransaction());
			ps.setString(7, compte.getStatutCompte().name().toLowerCase());

			int affectedRows = ps.executeUpdate();
			if (affectedRows > 0) {
				try (ResultSet gk = ps.getGeneratedKeys()) {
					if (gk.next()) {
						compte.setIdCompte(gk.getInt(1));
					}
				}
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(Compte compte) {
		String sql = "UPDATE comptes SET plafond_journalier = ?, plafond_transaction = ?, statut_compte = ? WHERE id_compte = ?";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setDouble(1, compte.getPlafondJournalier());
			ps.setDouble(2, compte.getPlafondTransaction());
			ps.setString(3, compte.getStatutCompte().name().toLowerCase());
			ps.setInt(4, compte.getIdCompte());

			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(Integer idCompte) {
		String sql = "DELETE FROM comptes WHERE id_compte = ? AND solde = 0";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, idCompte);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Compte findById(Integer idCompte) {
		String sql = "SELECT * FROM comptes WHERE id_compte = ?";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, idCompte);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return mapResultSetToCompte(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Compte> findByIdClient(Integer idClient) {
		List<Compte> comptes = new ArrayList<>();
		String sql = "SELECT * FROM comptes WHERE id_client = ?";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, idClient);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					comptes.add(mapResultSetToCompte(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return comptes;
	}

	@Override
	public List<Compte> findAll() {
		List<Compte> comptes = new ArrayList<>();
		String sql = "SELECT * FROM comptes";

		try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				comptes.add(mapResultSetToCompte(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return comptes;
	}

	// --- Méthodes utilitaires privées ---

	private Integer generateUniqueNumeroCompte() {
		Random random = new Random();
		while (true) {
			// Génère un numéro aléatoire à 8 chiffres entre 10000000 et 99999999
			int num = 10000000 + random.nextInt(90000000);
			String sql = "SELECT COUNT(*) FROM comptes WHERE numero_compte = ?";
			try (PreparedStatement ps = connection.prepareStatement(sql)) {
				ps.setInt(1, num);
				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next() && rs.getInt(1) == 0) {
						return num; // Unique !
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private Compte mapResultSetToCompte(ResultSet rs) throws SQLException {
		Compte c = new Compte();
		c.setIdCompte(rs.getInt("id_compte"));
		c.setIdClient(rs.getInt("id_client"));
		c.setNumeroCompte(rs.getInt("numero_compte"));
		c.setDevise(rs.getInt("devise"));
		c.setSolde(rs.getDouble("solde"));
		c.setPlafondJournalier(rs.getDouble("plalfond_journalier"));
		c.setPlafondTransaction(rs.getDouble("plafond_transaction"));

		// Conversion du Timestamp SQL vers LocalDateTime
		if (rs.getTimestamp("date_ouverture") != null) {
			c.setDateOuverture(rs.getTimestamp("date_ouverture").toLocalDateTime());
		}

		// Mapping de l'enum en nettoyant les accents éventuels (ex: bloqué -> BLOQUE)
		String statutStr = rs.getString("statut_compte").toUpperCase().replace("É", "E");
		c.setStatutCompte(StatutCompte.valueOf(statutStr));

		return c;
	}
}