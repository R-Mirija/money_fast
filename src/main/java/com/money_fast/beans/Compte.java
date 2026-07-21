package com.money_fast.beans;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.money_fast.beans.enums.StatutCompte;

public class Compte implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer idCompte;
	private Integer idClient;
	private Integer numeroCompte;
	private Integer devise;
	private Double solde;
	private Double plafondJournalier;
	private Double plafondTransaction;
	private LocalDateTime dateOuverture;
	private StatutCompte statutCompte;

	public Compte() {
	}

	// Getters & Setters ...
	public Integer getIdCompte() {
		return idCompte;
	}

	public void setIdCompte(Integer idCompte) {
		this.idCompte = idCompte;
	}

	public Integer getIdClient() {
		return idClient;
	}

	public void setIdClient(Integer idClient) {
		this.idClient = idClient;
	}

	public Integer getNumeroCompte() {
		return numeroCompte;
	}

	public void setNumeroCompte(Integer numeroCompte) {
		this.numeroCompte = numeroCompte;
	}

	public Integer getDevise() {
		return devise;
	}

	public void setDevise(Integer devise) {
		this.devise = devise;
	}

	public Double getSolde() {
		return solde;
	}

	public void setSolde(Double solde) {
		this.solde = solde;
	}

	public Double getPlafondJournalier() {
		return plafondJournalier;
	}

	public void setPlafondJournalier(Double plafondJournalier) {
		this.plafondJournalier = plafondJournalier;
	}

	public Double getPlafondTransaction() {
		return plafondTransaction;
	}

	public void setPlafondTransaction(Double plafondTransaction) {
		this.plafondTransaction = plafondTransaction;
	}

	public LocalDateTime dateOuverture() {
		return dateOuverture;
	}

	public void setDateOuverture(LocalDateTime dateOuverture) {
		this.dateOuverture = dateOuverture;
	}

	public StatutCompte getStatutCompte() {
		return statutCompte;
	}

	public void setStatutCompte(StatutCompte statutCompte) {
		this.statutCompte = statutCompte;
	}
}
