package com.moneyfast.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.moneyfast.model.Compte;

public class MySQLCompteRepository implements CompteRepository {

    @Override
    public void save(Compte compte) {
        String sql = "INSERT INTO comptes (id_client, numero_compte, devise, solde, plafond_journalier, plafond_transaction, date_ouverture, statut_compte) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setLong(1, compte.getIdClient());
            stmt.setInt(2, compte.getNumeroCompte());
            stmt.setInt(3, compte.getDevise());
            stmt.setDouble(4, compte.getSolde());
            stmt.setDouble(5, compte.getPlafondJournalier());
            stmt.setDouble(6, compte.getPlafondTransaction());
            stmt.setTimestamp(7, Timestamp.valueOf(compte.getDateOuverture()));
            stmt.setString(8, compte.getStatutCompte());
            
            stmt.executeUpdate();
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    compte.setIdCompte(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Compte findById(Long id) {
        String sql = "SELECT * FROM comptes WHERE id_compte = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
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
    public Compte findByNumero(String numeroCompte) {
        String sql = "SELECT * FROM comptes WHERE numero_compte = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, numeroCompte);
            try (ResultSet rs = stmt.executeQuery()) {
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
    public List<Compte> findByClientId(Long idClient) {
        List<Compte> comptes = new ArrayList<>();
        String sql = "SELECT * FROM comptes WHERE id_client = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, idClient);
            try (ResultSet rs = stmt.executeQuery()) {
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
    public Compte findByTelephone(String telephone) {
        String sql = "SELECT c.* FROM comptes c JOIN clients cl ON c.id_client = cl.id_client WHERE cl.numero_telephone = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, telephone);
            try (ResultSet rs = stmt.executeQuery()) {
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
    public void updateSolde(int numeroCompte, double nouveauSolde) {
        String sql = "UPDATE comptes SET solde = ? WHERE numero_compte = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, nouveauSolde);
            stmt.setInt(2, numeroCompte);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateStatut(int numeroCompte, String statut) {
        String sql = "UPDATE comptes SET statut_compte = ? WHERE numero_compte = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, statut);
            stmt.setInt(2, numeroCompte);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Compte mapResultSetToCompte(ResultSet rs) throws SQLException {
        Compte compte = new Compte();
        compte.setIdCompte(rs.getLong("id_compte"));
        compte.setIdClient(rs.getLong("id_client"));
        compte.setNumeroCompte(rs.getInt("numero_compte"));
        compte.setDevise(rs.getInt("devise"));
        compte.setSolde(rs.getDouble("solde"));
        compte.setPlafondJournalier(rs.getDouble("plafond_journalier"));
        compte.setPlafondTransaction(rs.getDouble("plafond_transaction"));
        
        Timestamp ouverture = rs.getTimestamp("date_ouverture");
        if (ouverture != null) {
            compte.setDateOuverture(ouverture.toLocalDateTime());
        }
        
        compte.setStatutCompte(rs.getString("statut_compte"));
        return compte;
    }
}