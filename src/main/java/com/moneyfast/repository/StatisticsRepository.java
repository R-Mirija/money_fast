package com.moneyfast.repository;

import java.sql.Timestamp;
import java.util.Map;
import java.util.List;

import com.moneyfast.enums.TopClientFilterEnum;
import com.moneyfast.model.ActiveClientStat;
import com.moneyfast.model.Statistics;

public interface StatisticsRepository {

  Map<String, Double> getVolumeParDevise(Timestamp dateDebut, Timestamp dateFin);

  List<ActiveClientStat> findTopClients(TopClientFilterEnum filter, int limit, Timestamp dateDebut, Timestamp dateFin);

  Statistics getGlobalStatistics(TopClientFilterEnum filter, int topLimit, Timestamp dateDebut, Timestamp dateFin);
}