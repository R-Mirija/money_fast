package com.moneyfast.repository;

import java.util.Map;

public interface StatsRepository {
    double getTotalRecettes();
    Map<String, Double> getVolumeParDevise();
    Map<String, Integer> getTopClients();
}