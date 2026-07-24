package com.moneyfast.model;

import java.util.List;
import java.util.Map;

public class Statistics {
  private double totalRecette;
  private Map<String, Double> totalTransfert;
  private List<ActiveClientStat> topClients;

  public double getTotalRecette() {
    return this.totalRecette;
  }

  public Map<String, Double> getTotalTransfert() {
    return this.totalTransfert;
  }

  public List<ActiveClientStat> getTopClients() {
    return this.topClients;
  }


  public void setTotalRecette(double recette) {
    this.totalRecette = recette; 
  }

  public void setTotalTransfert(Map<String, Double> transfert) {
    this.totalTransfert = transfert;
  }

  public void setTopClients(List<ActiveClientStat> clients) {
    this.topClients = clients;
  }
}
