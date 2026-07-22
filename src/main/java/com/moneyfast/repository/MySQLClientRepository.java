package com.moneyfast.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.moneyfast.model.Client;

public class MySQLClientRepository implements ClientRepository {

	@Override
    public void save(Client client) {
        String sql = "INSERT INTO clients (numero_telephone, nom, prenom, sexe, pays, devise_preferee, mail, password, date_inscription, statut_client) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, client.getNumeroTelephone());
            stmt.setString(2, client.getNom());
            stmt.setString(3, client.getPrenom());
            stmt.setString(4, client.getSexe());
            stmt.setInt(5, client.getPays());
            stmt.setInt(6, client.getDevisePreferee());
            stmt.setString(7, client.getMail());
            stmt.setString(8, client.getPassword());
            stmt.setTimestamp(9, Timestamp.valueOf(client.getDateInscription()));
            stmt.setString(10, client.getStatutClient());
            
            stmt.executeUpdate();
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    client.setIdClient(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur de Base de Données : " + e.getMessage(), e);
        }
    }
    

    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM clients";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                clients.add(mapResultSetToClient(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    @Override
    public Client findById(Long id) {
        String sql = "SELECT * FROM clients WHERE id_client = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToClient(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Client findByEmail(String email) {
        String sql = "SELECT * FROM clients WHERE mail = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToClient(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Client findByTelephone(String telephone) {
        String sql = "SELECT * FROM clients WHERE numero_telephone = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, telephone);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToClient(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Client client) {
        String sql = "UPDATE clients SET nom = ?, prenom = ?, numero_telephone = ?, pays = ?, devise_preferee = ?, mail = ?, statut_client = ? WHERE id_client = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, client.getNom());
            stmt.setString(2, client.getPrenom());
            stmt.setString(3, client.getNumeroTelephone());
            stmt.setInt(4, client.getPays());
            stmt.setInt(5, client.getDevisePreferee());
            stmt.setString(6, client.getMail());
            stmt.setString(7, client.getStatutClient());
            stmt.setLong(8, client.getIdClient());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM clients WHERE id_client = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Client mapResultSetToClient(ResultSet rs) throws SQLException {
        Client client = new Client();
        client.setIdClient(rs.getLong("id_client"));
        client.setNumeroTelephone(rs.getString("numero_telephone"));
        client.setNom(rs.getString("nom"));
        client.setPrenom(rs.getString("prenom"));
        client.setSexe(rs.getString("sexe"));
        client.setPays(rs.getInt("pays"));
        client.setDevisePreferee(rs.getInt("devise_preferee"));
        client.setMail(rs.getString("mail"));
        client.setPassword(rs.getString("password"));
        
        Timestamp inscription = rs.getTimestamp("date_inscription");
        if (inscription != null) {
            client.setDateInscription(inscription.toLocalDateTime());
        }
        
        client.setStatutClient(rs.getString("statut_client"));
        
        Timestamp connexion = rs.getTimestamp("date_derniere_connexion");
        if (connexion != null) {
            client.setDateDerniereConnexion(connexion.toLocalDateTime());
        }
        
        return client;
    }
}