package com.moneyfast.repository.repository_impl;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.moneyfast.enums.StatutTransfertEnum;
import com.moneyfast.model.Transfert;
import com.moneyfast.repository.DBConnection;
import com.moneyfast.repository.TransfertRepository;

public class TransfertRepositoryImpl implements TransfertRepository {

    @Override
    public void save(Transfert transfert) {
        String sql = "INSERT INTO transferts (code_transfert, id_compte_source, id_compte_destination, montant_envoye, montant_recu, frais, taux_applique, devise_source, devise_destination, date_transfert, raison, statut_transfert, date_confirmation, reference_externe) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, transfert.getCodeTransfert());
            stmt.setLong(2, transfert.getIdCompteSource());
            stmt.setLong(3, transfert.getIdCompteDestination());
            stmt.setDouble(4, transfert.getMontantEnvoye());
            stmt.setDouble(5, transfert.getMontantRecu());
            stmt.setDouble(6, transfert.getFrais());
            stmt.setInt(7, transfert.getTauxApplique());
            stmt.setInt(8, transfert.getDeviseSource());
            stmt.setInt(9, transfert.getDeviseDestination());
            stmt.setTimestamp(10, Timestamp.valueOf(transfert.getDateTransfert()));
            stmt.setString(11, transfert.getRaison());
            stmt.setString(12, transfert.getStatutTransfert().getLibelle());

            if (transfert.getDateConfirmation() != null) {
                stmt.setTimestamp(13, Timestamp.valueOf(transfert.getDateConfirmation()));
            } else {
                stmt.setNull(13, Types.TIMESTAMP);
            }

            stmt.setString(14, transfert.getReferenceExterne());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    transfert.setIdTransfert(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la sauvegarde du transfert : " + e.getMessage(), e);
        }
    }

    @Override
    public Transfert findById(Long id) {
        String sql = "SELECT * FROM transferts WHERE id_transfert = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToTransfert(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Transfert findByCode(int codeTransfert) {
        String sql = "SELECT * FROM transferts WHERE code_transfert = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codeTransfert);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToTransfert(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Transfert> findByCompteSource(Long idCompteSource) {
        List<Transfert> transferts = new ArrayList<>();
        String sql = "SELECT * FROM transferts WHERE id_compte_source = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, idCompteSource);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    transferts.add(mapResultSetToTransfert(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transferts;
    }

    @Override
    public List<Transfert> findByCompteDestination(Long idCompteDestination) {
        List<Transfert> transferts = new ArrayList<>();
        String sql = "SELECT * FROM transferts WHERE id_compte_destination = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, idCompteDestination);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    transferts.add(mapResultSetToTransfert(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transferts;
    }

    @Override
    public List<Transfert> findByDate(LocalDate date) {
        List<Transfert> transferts = new ArrayList<>();
        String sql = "SELECT * FROM transferts WHERE DATE(date_transfert) = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(date));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    transferts.add(mapResultSetToTransfert(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transferts;
    }

    @Override
    public List<Transfert> findByCompteAndMonth(Long idCompte, int annee, int mois) {
        List<Transfert> transferts = new ArrayList<>();
        String sql = "SELECT * FROM transferts WHERE (id_compte_source = ? OR id_compte_destination = ?) " +
                "AND YEAR(date_transfert) = ? AND MONTH(date_transfert) = ? ORDER BY date_transfert ASC";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, idCompte);
            stmt.setLong(2, idCompte);
            stmt.setInt(3, annee);
            stmt.setInt(4, mois);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    transferts.add(mapResultSetToTransfert(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transferts;
    }

    @Override
    public void updateStatut(Long idTransfert, StatutTransfertEnum statut) {
        String sql = "UPDATE transferts SET statut_transfert = ?, date_confirmation = ? WHERE id_transfert = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, statut.getLibelle());
            stmt.setTimestamp(2, Timestamp.valueOf(java.time.LocalDateTime.now()));
            stmt.setLong(3, idTransfert);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Transfert mapResultSetToTransfert(ResultSet rs) throws SQLException {
        Transfert t = new Transfert();
        t.setIdTransfert(rs.getLong("id_transfert"));
        t.setCodeTransfert(rs.getInt("code_transfert"));
        t.setIdCompteSource(rs.getLong("id_compte_source"));
        t.setIdCompteDestination(rs.getLong("id_compte_destination"));
        t.setMontantEnvoye(rs.getDouble("montant_envoye"));
        t.setMontantRecu(rs.getDouble("montant_recu"));
        t.setFrais(rs.getFloat("frais"));
        t.setTauxApplique(rs.getInt("taux_applique"));
        t.setDeviseSource(rs.getInt("devise_source"));
        t.setDeviseDestination(rs.getInt("devise_destination"));

        Timestamp transfertDate = rs.getTimestamp("date_transfert");
        if (transfertDate != null) {
            t.setDateTransfert(transfertDate.toLocalDateTime());
        }

        t.setRaison(rs.getString("raison"));
        t.setStatutTransfert(StatutTransfertEnum.fromLibelle(rs.getString("statut_transfert")));

        Timestamp confirmationDate = rs.getTimestamp("date_confirmation");
        if (confirmationDate != null) {
            t.setDateConfirmation(confirmationDate.toLocalDateTime());
        }

        t.setReferenceExterne(rs.getString("reference_externe"));
        return t;
    }

    @Override
    public List<Transfert> findByCompte(Long idCompte) {
        List<Transfert> transferts = new ArrayList<>();
        String sql = "SELECT * FROM transferts WHERE id_compte_source = ? OR id_compte_destination = ? ORDER BY date_transfert DESC";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, idCompte);
            stmt.setLong(2, idCompte);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    transferts.add(mapResultSetToTransfert(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transferts;
    }
}