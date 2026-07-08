package com.money_fast.daos;

import java.io.Serializable;
import java.time.LocalDateTime;

public class TauxDeChangeDAO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer idTaux;
	private Integer codeTaux;
	private Integer deviseSource;
	private Integer deviseDestination;
	private Double montantMin;
	private Integer montantMax;
	private Float tauxApplication;
	private LocalDateTime dateDebutValidite;
	private LocalDateTime dateFinValidite;
	private Boolean active;

	public TauxDeChangeDAO() {
	}

	// Getters & Setters ...
	public Integer getIdTaux() {
		return idTaux;
	}

	public void setIdTaux(Integer idTaux) {
		this.idTaux = idTaux;
	}

	public Integer getCodeTaux() {
		return codeTaux;
	}

	public void setCodeTaux(Integer codeTaux) {
		this.codeTaux = codeTaux;
	}

	public Integer getDeviseSource() {
		return deviseSource;
	}

	public void setDeviseSource(Integer deviseSource) {
		this.deviseSource = deviseSource;
	}

	public Integer getDeviseDestination() {
		return deviseDestination;
	}

	public void setDeviseDestination(Integer deviseDestination) {
		this.deviseDestination = deviseDestination;
	}

	public Double getMontantMin() {
		return montantMin;
	}

	public void setMontantMin(Double montantMin) {
		this.montantMin = montantMin;
	}

	public Integer getMontantMax() {
		return montantMax;
	}

	public void setMontantMax(Integer montantMax) {
		this.montantMax = montantMax;
	}

	public Float getTauxApplication() {
		return tauxApplication;
	}

	public void setTauxApplication(Float tauxApplication) {
		this.tauxApplication = tauxApplication;
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