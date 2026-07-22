package com.moneyfast.repository.repository_impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.moneyfast.model.Frais;
import com.moneyfast.repository.DBConnection;
import com.moneyfast.repository.FraisRepository;

public class FraisRepositoryImpl implements FraisRepository {

  @Override
  public List<Frais> findAll() {
    String sql = "SELECT * FROM frais_envoi ORDER BY id_frais DESC";
    List<Frais> resultats = new ArrayList<>();

    try (Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()) {

      while (rs.next()) {
        resultats.add(mapRow(rs));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return resultats;
  }

  @Override
  public Frais findById(int idFrais) {
    String sql = "SELECT * FROM frais_envoi WHERE id_frais = ?";

    try (Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {

      ps.setInt(1, idFrais);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return mapRow(rs);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public Frais findApplicable(int idDeviseSource, double montant) {
    String sql = "SELECT * FROM frais_envoi " +
        "WHERE devise_frais = ? AND montant_min <= ? AND montant_max >= ? AND active = 1 " +
        "AND (date_debut_validite IS NULL OR date_debut_validite <= NOW()) " +
        "AND (date_fin_validite IS NULL OR date_fin_validite >= NOW()) " +
        "ORDER BY id_frais DESC LIMIT 1";

    try (Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {

      ps.setInt(1, idDeviseSource);
      ps.setDouble(2, montant);
      ps.setDouble(3, montant);

      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return mapRow(rs);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public void save(Frais frais) {
    String sql = "INSERT INTO frais_envoi " +
        "(code_frais, devise_frais, montant_min, montant_max, type_frais, valeur_frais, date_debut_validite, date_fin_validite, active) "
        +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

      int codeFrais = frais.getCodeFrais() != 0 ? frais.getCodeFrais() : (int) (Math.random() * 90000) + 10000;

      ps.setInt(1, codeFrais);
      ps.setInt(2, frais.getDeviseFrais());
      ps.setDouble(3, frais.getMontantMin());
      ps.setDouble(4, frais.getMontantMax());
      ps.setInt(5, frais.getTypeFrais());
      ps.setDouble(6, frais.getValeurFrais());
      ps.setTimestamp(7, frais.getDateDebutValidite());
      ps.setTimestamp(8, frais.getDateFinValidite());
      ps.setBoolean(9, frais.isActive());

      ps.executeUpdate();

      try (ResultSet keys = ps.getGeneratedKeys()) {
        if (keys.next()) {
          frais.setIdFrais(keys.getInt(1));
        }
      }
      frais.setCodeFrais(codeFrais);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void update(Frais frais) {
    String sql = "UPDATE frais_envoi SET " +
        "code_frais = ?, " +
        "devise_frais = ?, " +
        "montant_min = ?, " +
        "montant_max = ?, " +
        "type_frais = ?, " +
        "valeur_frais = ?, " +
        "date_debut_validite = ?, " +
        "date_fin_validite = ?, " +
        "active = ? " +
        "WHERE id_frais = ?";

    try (Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {

      ps.setInt(1, frais.getCodeFrais());
      ps.setInt(2, frais.getDeviseFrais());
      ps.setDouble(3, frais.getMontantMin());
      ps.setDouble(4, frais.getMontantMax());
      ps.setInt(5, frais.getTypeFrais());
      ps.setDouble(6, frais.getValeurFrais());
      ps.setTimestamp(7, frais.getDateDebutValidite());
      ps.setTimestamp(8, frais.getDateFinValidite());
      ps.setBoolean(9, frais.isActive());
      ps.setInt(10, frais.getIdFrais());

      ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void delete(int idFrais) {
    String sql = "DELETE FROM frais_envoi WHERE id_frais = ?";

    try (Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {

      ps.setInt(1, idFrais);
      ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private Frais mapRow(ResultSet rs) throws SQLException {
    Frais f = new Frais();
    f.setIdFrais(rs.getInt("id_frais"));
    f.setCodeFrais(rs.getInt("code_frais"));
    f.setDeviseFrais(rs.getInt("devise_frais"));
    f.setMontantMin(rs.getDouble("montant_min"));
    f.setMontantMax(rs.getDouble("montant_max"));
    f.setTypeFrais(rs.getInt("type_frais"));
    f.setValeurFrais(rs.getDouble("valeur_frais"));
    f.setDateDebutValidite(rs.getTimestamp("date_debut_validite"));
    f.setDateFinValidite(rs.getTimestamp("date_fin_validite"));
    f.setActive(rs.getBoolean("active"));
    return f;
  }
}