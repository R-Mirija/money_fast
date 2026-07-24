package com.moneyfast.repository;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.List;

import com.moneyfast.enums.TopClientFilterEnum;
import com.moneyfast.model.ActiveClientStat;
import com.moneyfast.model.Statistics;

public interface StatisticsRepository {

  Map<String, Double> getVolumeParDevise(LocalDateTime dateDebut, LocalDateTime dateFin);

  List<ActiveClientStat> findTopClients(TopClientFilterEnum filter, int limit, LocalDateTime dateDebut,
      LocalDateTime dateFin);

  Statistics getGlobalStatistics(TopClientFilterEnum filter, int topLimit, LocalDateTime dateDebut,
      LocalDateTime dateFin);
}