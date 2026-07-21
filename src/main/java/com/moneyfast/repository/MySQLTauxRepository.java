package com.moneyfast.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.moneyfast.model.TauxDeChange;

public class MySQLTauxRepository implements TauxRepository {

    @Override
    public void save(TauxDeChange taux) {
        String sql = "INSERT INTO taux_de_change (code_taux, devise_source, devise_destination, montant_min, montant_max, taux_application, active) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, taux.getCodeTaux());
            stmt.setInt(2, taux.getDeviseSource());
            stmt.setInt(3, taux.getDeviseDestination());
            stmt.setDouble(4, taux.getMontantMin());
            stmt.setDouble(5, taux.getMontantMax());
            stmt.setFloat(6, taux.getTauxApplication());
            stmt.setBoolean(7, taux.isActive());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<TauxDeChange> findAll() {
        List<TauxDeChange> liste = new ArrayList<>();
        String sql = "SELECT * FROM taux_de_change";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                liste.add(mapResultSetToTaux(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }

    @Override
    public TauxDeChange findActifByDevises(int deviseSource, int deviseDestination, double montant) {
        String sql = "SELECT * FROM taux_de_change WHERE devise_source = ? AND devise_destination = ? AND ? BETWEEN montant_min AND montant_max AND active = 1 LIMIT 1";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, deviseSource);
            stmt.setInt(2, deviseDestination);
            stmt.setDouble(3, montant);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToTaux(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateTaux(int idTaux, float nouveauTaux, boolean active) {
        String sql = "UPDATE taux_de_change SET taux_application = ?, active = ? WHERE id_taux = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setFloat(1, nouveauTaux);
            stmt.setBoolean(2, active);
            stmt.setInt(3, idTaux);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private TauxDeChange mapResultSetToTaux(ResultSet rs) throws SQLException {
        TauxDeChange t = new TauxDeChange();
        t.setIdTaux(rs.getInt("id_taux"));
        t.setCodeTaux(rs.getInt("code_taux"));
        t.setDeviseSource(rs.getInt("devise_source"));
        t.setDeviseDestination(rs.getInt("devise_destination"));
        t.setMontantMin(rs.getDouble("montant_min"));
        t.setMontantMax(rs.getDouble("montant_max"));
        t.setTauxApplication(rs.getFloat("taux_application"));
        t.setActive(rs.getBoolean("active"));
        return t;
    }
    @Override
    public void delete(int idTaux) {
        String sql = "DELETE FROM taux_de_change WHERE id_taux = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idTaux);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression du taux de change : " + e.getMessage(), e);
        }
    }
}