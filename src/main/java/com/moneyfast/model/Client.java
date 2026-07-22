package com.moneyfast.model;

import java.time.LocalDateTime;

public class Client {
    
    private Long id_client;
    private String numero_telephone;
    private String nom;
    private String prenom;
    private String sexe;
    private int pays;
    private int devise_preferee;
    private String mail;
    private String password;
    private LocalDateTime date_inscription;
    private String statut_client;
    private LocalDateTime date_derniere_connexion;

    public Client() {
    }

    public Client(Long idClient, String numeroTelephone, String nom, String prenom, String sexe, 
                  int pays, int devisePreferee, String mail, String password, 
                  LocalDateTime dateInscription, String statutClient) {
        this.id_client = idClient;
        this.numero_telephone = numeroTelephone;
        this.nom = nom;
        this.prenom = prenom;
        this.sexe = sexe;
        this.pays = pays;
        this.devise_preferee = devisePreferee;
        this.mail = mail;
        this.password = password;
        this.date_inscription = dateInscription;
        this.statut_client = statutClient;
    }

	public Long getIdClient() {
		return id_client;
	}

	public void setIdClient(Long idClient) {
		this.id_client = idClient;
	}

	public String getNumeroTelephone() {
		return numero_telephone;
	}

	public void setNumeroTelephone(String numeroTelephone) {
		this.numero_telephone = numeroTelephone;
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

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	public int getPays() {
		return pays;
	}

	public void setPays(int pays) {
		this.pays = pays;
	}

	public int getDevisePreferee() {
		return devise_preferee;
	}

	public void setDevisePreferee(int devisePreferee) {
		this.devise_preferee = devisePreferee;
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
		return date_inscription;
	}

	public void setDateInscription(LocalDateTime dateInscription) {
		this.date_inscription = dateInscription;
	}

	public String getStatutClient() {
		return statut_client;
	}

	public void setStatutClient(String statutClient) {
		this.statut_client = statutClient;
	}

	public LocalDateTime getDateDerniereConnexion() {
		return date_derniere_connexion;
	}

	public void setDateDerniereConnexion(LocalDateTime dateDerniereConnexion) {
		this.date_derniere_connexion = dateDerniereConnexion;
	}
    
    
}