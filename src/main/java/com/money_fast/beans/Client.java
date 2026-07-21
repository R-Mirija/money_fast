package com.money_fast.beans;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.money_fast.beans.enums.Sexe;
import com.money_fast.beans.enums.StatutClient;

public class Client implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer idClient;
	private String numeroTelephone;
	private String nom;
	private String prenom;
	private Sexe sexe;
	private Integer pays;
	private Integer devisePreferee;
	private String mail;
	private String password;
	private LocalDateTime dateInscription;
	private StatutClient statutClient;
	private LocalDateTime dateDerniereConnexion;

	public Client() {
	}

	// Getters & Setters ...
	public Integer getIdClient() {
		return idClient;
	}

	public void setIdClient(Integer idClient) {
		this.idClient = idClient;
	}

	public String getNumeroTelephone() {
		return numeroTelephone;
	}

	public void setNumeroTelephone(String numeroTelephone) {
		this.numeroTelephone = numeroTelephone;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public Sexe getSexe() {
		return sexe;
	}

	public void setSexe(Sexe sexe) {
		this.sexe = sexe;
	}

	public Integer getPays() {
		return pays;
	}

	public void setPays(Integer pays) {
		this.pays = pays;
	}

	public Integer getDevisePreferee() {
		return devisePreferee;
	}

	public void setDevisePreferee(Integer devisePreferee) {
		this.devisePreferee = devisePreferee;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDateTime getDateInscription() {
		return dateInscription;
	}

	public void setDateInscription(LocalDateTime dateInscription) {
		this.dateInscription = dateInscription;
	}

	public StatutClient getStatutClient() {
		return statutClient;
	}

	public void setStatutClient(StatutClient statutClient) {
		this.statutClient = statutClient;
	}

	public LocalDateTime getDateDerniereConnexion() {
		return dateDerniereConnexion;
	}

	public void setDateDerniereConnexion(LocalDateTime dateDerniereConnexion) {
		this.dateDerniereConnexion = dateDerniereConnexion;
	}
}
