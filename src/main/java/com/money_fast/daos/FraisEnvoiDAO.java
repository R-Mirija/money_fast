package com.money_fast.daos;

import java.io.Serializable;
import java.time.LocalDateTime;

public class FraisEnvoiDAO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer idFrais;
	private Integer codeFrais;
	private Integer deviseFrais;
	private Double montantMin;
	private Double montantMax;
	private Integer typeFrais;
	private Double valeurFrais;
	private LocalDateTime dateDebutValidite;
	private LocalDateTime dateFinValidite;
	private Boolean active;

	public FraisEnvoiDAO() {
	}

	// Getters & Setters ...
	public Integer getIdFrais() {
		return idFrais;
	}

	public void setIdFrais(Integer idFrais) {
		this.idFrais = idFrais;
	}

	public Integer getCodeFrais() {
		return codeFrais;
	}

	public void setCodeFrais(Integer codeFrais) {
		this.codeFrais = codeFrais;
	}

	public Integer getDeviseFrais() {
		return deviseFrais;
	}

	public void setDeviseFrais(Integer deviseFrais) {
		this.deviseFrais = deviseFrais;
	}

	public Double getMontantMin() {
		return montantMin;
	}

	public void setMontantMin(Double montantMin) {
		this.montantMin = montantMin;
	}

	public Double getMontantMax() {
		return montantMax;
	}

	public void setMontantMax(Double montantMax) {
		this.montantMax = montantMax;
	}

	public Integer getTypeFrais() {
		return typeFrais;
	}

	public void setTypeFrais(Integer typeFrais) {
		this.typeFrais = typeFrais;
	}

	public Double getValeurFrais() {
		return valeurFrais;
	}

	public void setValeurFrais(Double valeurFrais) {
		this.valeurFrais = valeurFrais;
	}

	public LocalDateTime getDateDebutValidite() {
		return dateDebutValidite;
	}

	public void setDateDebutValidite(LocalDateTime dateDebutValidite) {
		this.dateDebutValidite = dateDebutValidite;
	}

	public LocalDateTime getDateFinValidite() {
		return dateFinValidite;
	}

	public void setDateFinValidite(LocalDateTime dateFinValidite) {
		this.dateFinValidite = dateFinValidite;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
}