package com.money_fast.beans;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.money_fast.beans.enums.StatutTransfert;

public class Transfert implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer idTransfert;
	private Integer codeTransfert;
	private Integer idCompteSource;
	private Integer idCompteDestination;
	private Double montantEnvoye;
	private Double montantRecu;
	private Double frais;
	private Integer tauxApplique;
	private Integer deviseSource;
	private Integer deviseDestination;
	private LocalDateTime dateTransfert;
	private String raison;
	private StatutTransfert statutTransfert;
	private LocalDateTime dateConfirmation;
	private String referenceExterne;

	public Transfert() {
	}

	// Getters & Setters ...
	public Integer getIdTransfert() {
		return idTransfert;
	}

	public void setIdTransfert(Integer idTransfert) {
		this.idTransfert = idTransfert;
	}

	public Integer getCodeTransfert() {
		return codeTransfert;
	}

	public void setCodeTransfert(Integer codeTransfert) {
		this.codeTransfert = codeTransfert;
	}

	public Integer getIdCompteSource() {
		return idCompteSource;
	}

	public void setIdCompteSource(Integer idCompteSource) {
		this.idCompteSource = idCompteSource;
	}

	public Integer getIdCompteDestination() {
		return idCompteDestination;
	}

	public void setIdCompteDestination(Integer idCompteDestination) {
		this.idCompteDestination = idCompteDestination;
	}

	public Double getMontantEnvoye() {
		return montantEnvoye;
	}

	public void setMontantEnvoye(Double montantEnvoye) {
		this.montantEnvoye = montantEnvoye;
	}

	public Double getMontantRecu() {
		return montantRecu;
	}

	public void setMontantRecu(Double montantRecu) {
		this.montantRecu = montantRecu;
	}

	public Double getFrais() {
		return frais;
	}

	public void setFrais(Double frais) {
		this.frais = frais;
	}

	public Integer getTauxApplique() {
		return tauxApplique;
	}

	public void setTauxApplique(Integer tauxApplique) {
		this.tauxApplique = tauxApplique;
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

	public LocalDateTime getDateTransfert() {
		return dateTransfert;
	}

	public void setDateTransfert(LocalDateTime dateTransfert) {
		this.dateTransfert = dateTransfert;
	}

	public String getRaison() {
		return raison;
	}

	public void setRaison(String raison) {
		this.raison = raison;
	}

	public StatutTransfert getStatutTransfert() {
		return statutTransfert;
	}

	public void setStatutTransfert(StatutTransfert statutTransfert) {
		this.statutTransfert = statutTransfert;
	}

	public LocalDateTime getDateConfirmation() {
		return dateConfirmation;
	}

	public void setDateConfirmation(LocalDateTime dateConfirmation) {
		this.dateConfirmation = dateConfirmation;
	}

	public String getReferenceExterne() {
		return referenceExterne;
	}

	public void setReferenceExterne(String referenceExterne) {
		this.referenceExterne = referenceExterne;
	}
}