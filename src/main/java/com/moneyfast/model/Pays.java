package com.moneyfast.model;

public class Pays {
    private int idPays;
    private String libelle;
    private String statut;

    public Pays() {}

    public Pays(int idPays, String libelle, String statut) {
        this.idPays = idPays;
        this.libelle = libelle;
        this.statut = statut;
    }

    public int getIdPays() { return idPays; }
    public void setIdPays(int idPays) { this.idPays = idPays; }
    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
}