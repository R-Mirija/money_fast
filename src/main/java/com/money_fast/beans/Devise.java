package com.money_fast.beans;

import java.io.Serializable;

public class Devise implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer idDevise;
    private String libelle;

    public Devise() {}

    public Integer getIdDevise() { return idDevise; }
    public void setIdDevise(Integer idDevise) { this.idDevise = idDevise; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
}