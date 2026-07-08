package com.money_fast.daos;

import java.io.Serializable;

public class PaysDAO implements Serializable {
	private static final long serialVersionUID = 1L;

    private Integer idPays;
    private String libelle;

    public PaysDAO() {}

    public Integer getIdPays() { return idPays; }
    public void setIdPays(Integer idPays) { this.idPays = idPays; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
}
