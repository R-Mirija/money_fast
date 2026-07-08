package com.money_fast.daos;

import java.io.Serializable;

public class DeviseDAO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer idDevise;
    private String libelle;

    public DeviseDAO() {}

    public Integer getIdDevise() { return idDevise; }
    public void setIdDevise(Integer idDevise) { this.idDevise = idDevise; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
}