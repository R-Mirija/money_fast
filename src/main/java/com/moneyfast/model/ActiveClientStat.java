package com.moneyfast.model;

public class ActiveClientStat {
  private Client client;
  private int transactionsCount;
  private double totalMontant;

  public Client getClient() {
    return this.client;
  }

  public int getTransactionsCount() {
    return this.transactionsCount;
  }

  public double getTotalMontant() {
    return this.totalMontant;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public void setTransactionsCount(int count) {
    this.transactionsCount = count;
  }

  public void setTotalMontant(double montant) {
    this.totalMontant = montant;
  }
}
