package com.moneyfast.model;

public class Devise {
    private int idDevise;
    private String libelle;

    public Devise() {}

    public Devise(int idDevise, String libelle) {
        this.idDevise = idDevise;
        this.libelle = libelle;
    }

    public int getIdDevise() { return idDevise; }
    public void setIdDevise(int idDevise) { this.idDevise = idDevise; }
    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
}