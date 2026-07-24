package com.moneyfast.repository.repository_impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.moneyfast.enums.TopClientFilterEnum;
import com.moneyfast.model.ActiveClientStat;
import com.moneyfast.model.Client;
import com.moneyfast.model.Statistics;
import com.moneyfast.repository.DBConnection;
import com.moneyfast.repository.StatisticsRepository;

public class StatisticsRepositoryImpl implements StatisticsRepository {

  @Override
  public Map<String, Double> getVolumeParDevise(LocalDateTime dateDebut, LocalDateTime dateFin) {
    Map<String, Double> volumes = new LinkedHashMap<>();

    String sql = """
        SELECT d.libelle,
               SUM(t.montant_envoye)
        FROM transferts t
        JOIN devises d
            ON d.id_devise = t.devise_source
        WHERE LOWER(t.statut_transfert) IN ('confirmé')
          AND t.date_transfert BETWEEN ? AND ?
        GROUP BY d.libelle
        ORDER BY SUM(t.montant_envoye) DESC
        """;

    try (Connection cn = DBConnection.getConnection();
        PreparedStatement ps = cn.prepareStatement(sql)) {

      ps.setObject(1, dateDebut);
      ps.setObject(2, dateFin);

      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          volumes.put(rs.getString(1), rs.getDouble(2));
        }
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return volumes;
  }

  @Override
  public List<ActiveClientStat> findTopClients(TopClientFilterEnum filter, int limit, LocalDateTime dateDebut,
      LocalDateTime dateFin) {

    List<ActiveClientStat> list = new ArrayList<>();

    String orderByClause = (filter == TopClientFilterEnum.BY_TOTAL_MONTANT)
        ? "totalMontant DESC, transactionsCount DESC"
        : "transactionsCount DESC, totalMontant DESC";

    String sql = """
        SELECT
            c.id_client,
            c.nom,
            c.prenom,
            c.numero_telephone,
            COUNT(*) AS transactionsCount,
            SUM(t.montant_envoye) AS totalMontant
        FROM transferts t
        JOIN comptes cp
            ON cp.id_compte = t.id_compte_source
        JOIN clients c
            ON c.id_client = cp.id_client
        WHERE LOWER(t.statut_transfert) IN ('confirmé')
          AND t.date_transfert BETWEEN ? AND ?
        GROUP BY c.id_client, c.nom, c.prenom
        ORDER BY %s
        LIMIT ?
        """.formatted(orderByClause);

    try (Connection cn = DBConnection.getConnection();
        PreparedStatement ps = cn.prepareStatement(sql)) {

      ps.setObject(1, dateDebut);
      ps.setObject(2, dateFin);
      ps.setInt(3, limit);

      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          Client client = new Client();
          client.setIdClient((long) rs.getInt("id_client"));
          client.setNom(rs.getString("nom"));
          client.setPrenom(rs.getString("prenom"));
          client.setNumeroTelephone(rs.getString("numero_telephone"));

          ActiveClientStat stat = new ActiveClientStat();
          stat.setClient(client);
          stat.setTransactionsCount(rs.getInt("transactionsCount"));
          stat.setTotalMontant(rs.getDouble("totalMontant"));

          list.add(stat);
        }
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return list;
  }

  @Override
  public Statistics getGlobalStatistics(TopClientFilterEnum filter,
      int topLimit,
      LocalDateTime dateDebut,
      LocalDateTime dateFin) {

    Statistics statistics = new Statistics();
    statistics.setTotalRecette(getTotalRecette(dateDebut, dateFin));
    statistics.setTotalTransfert(getVolumeParDevise(dateDebut, dateFin));
    statistics.setTopClients(findTopClients(filter, topLimit, dateDebut, dateFin));

    return statistics;
  }

  private double getTotalRecette(LocalDateTime dateDebut, LocalDateTime dateFin) {
    String sql = """
        SELECT COALESCE(SUM(frais), 0)
        FROM transferts
        WHERE date_transfert BETWEEN ? AND ?
        """;

    try (Connection cn = DBConnection.getConnection();
        PreparedStatement ps = cn.prepareStatement(sql)) {

      ps.setObject(1, dateDebut);
      ps.setObject(2, dateFin);

      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return rs.getDouble(1);
        }
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return 0.0;
  }
}