package com.moneyfast.repository.repository_impl;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.moneyfast.repository.DBConnection;
import com.moneyfast.repository.StatsRepository;

public class StatsRepositoryImpl implements StatsRepository {

    @Override
    public double getTotalRecettes() {
        String sql = "SELECT SUM(frais) as total FROM transferts";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) return rs.getDouble("total");
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    @Override
    public Map<String, Double> getVolumeParDevise() {
        Map<String, Double> stats = new HashMap<>();
        String sql = "SELECT d.libelle, SUM(t.montant_envoye) as volume " +
                     "FROM transferts t JOIN devises d ON t.devise_source = d.id_devise " +
                     "GROUP BY d.libelle";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                stats.put(rs.getString("libelle"), rs.getDouble("volume"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return stats;
    }

    @Override
    public Map<String, Integer> getTopClients() {
        Map<String, Integer> stats = new LinkedHashMap<>();

        String sql = "SELECT c.nom, COUNT(t.id_transfert) as nb " +
                     "FROM transferts t JOIN comptes co ON t.id_compte_source = co.id_compte " +
                     "JOIN clients c ON co.id_client = c.id_client " +
                     "GROUP BY c.id_client ORDER BY nb DESC LIMIT 5";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                stats.put(rs.getString("nom"), rs.getInt("nb"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return stats;
    }
}