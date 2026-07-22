package com.moneyfast.repository.repository_impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.moneyfast.model.Pays;
import com.moneyfast.repository.DBConnection;
import com.moneyfast.repository.MetadataRepository;
import com.moneyfast.model.Devise;

public class MetadataRepositoryImpl implements MetadataRepository {

	@Override
	public List<Pays> findAllPays() {
	    List<Pays> liste = new ArrayList<>();
	    String sql = "SELECT * FROM pays ORDER BY libelle ASC";
	    try (Connection conn = DBConnection.getConnection();
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(sql)) {
	        while (rs.next()) {
	            liste.add(new Pays(rs.getInt("id_pays"), rs.getString("libelle"), rs.getString("statut")));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return liste;
	}

    @Override
    public List<Devise> findAllDevises() {
        List<Devise> liste = new ArrayList<>();
        String sql = "SELECT * FROM devises ORDER BY libelle ASC";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                liste.add(new Devise(rs.getInt("id_devise"), rs.getString("libelle")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }
    @Override
    public String findLibelleDevise(int idDevise) {
        String sql = "SELECT libelle FROM devises WHERE id_devise = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idDevise);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String fullLibelle = rs.getString("libelle");
                    if (fullLibelle.contains("(") && fullLibelle.contains(")")) {
                        return fullLibelle.substring(fullLibelle.indexOf("(") + 1, fullLibelle.indexOf(")"));
                    }
                    return fullLibelle;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "ID " + idDevise;
    }
    @Override
    public void updatePaysStatut(int idPays, String statut) {
        String sql = "UPDATE pays SET statut = ? WHERE id_pays = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, statut);
            stmt.setInt(2, idPays);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}