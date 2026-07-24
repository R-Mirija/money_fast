package com.moneyfast.enums;

public enum StatutTransfertEnum {
  EN_ATTENTE("en attente"),
  CONFIRME("confirmé"),
  COMPLETE("complété"),
  EN_COURS("en cours"),
  ECHONE("échoué"),
  ANNULE("annulé");

  private final String libelle;

  StatutTransfertEnum(String libelle) {
    this.libelle = libelle;
  }

  public String getLibelle() {
    return libelle;
  }

  public static StatutTransfertEnum fromLibelle(String text) {
    if (text == null) {
      return null;
    }
    for (StatutTransfertEnum statut : StatutTransfertEnum.values()) {
      if (statut.libelle.equalsIgnoreCase(text.trim())) {
        return statut;
      }
    }
    throw new IllegalArgumentException("Statut de transfert inconnu : " + text);
  }

  @Override
  public String toString() {
    return this.libelle;
  }
}