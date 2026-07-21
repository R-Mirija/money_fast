package com.moneyfast.model;
import java.time.LocalDateTime;

public class Compte {
	private Long idCompte;
    private Long idClient;
    private int numeroCompte;
    private int devise;
    private double solde;
    private double plafondJournalier;
    private double plafondTransaction;
    private LocalDateTime dateOuverture;
    private String statutCompte;

    public Compte() {
    }
    
    public Compte(Long idCompte, Long idClient, int numeroCompte, int devise, double solde, 
            double plafondJournalier, double plafondTransaction, LocalDateTime dateOuverture, 
            String statutCompte) {
			  this.idCompte = idCompte;
			  this.idClient = idClient;
			  this.numeroCompte = numeroCompte;
			  this.devise = devise;
			  this.solde = solde;
			  this.plafondJournalier = plafondJournalier;
			  this.plafondTransaction = plafondTransaction;
			  this.dateOuverture = dateOuverture;
			  this.statutCompte = statutCompte;
			}
    public static int genererNumero() {
        return (int) (Math.random() * 900_000_000) + 100_000_000;

    }

	public Long getIdCompte() {
		return idCompte;
	}

	public void setIdCompte(Long idCompte) {
		this.idCompte = idCompte;
	}

	public Long getIdClient() {
		return idClient;
	}

	public void setIdClient(Long idClient) {
		this.idClient = idClient;
	}

	public int getNumeroCompte() {
		return numeroCompte;
	}

	public void setNumeroCompte(int numeroCompte) {
		this.numeroCompte = numeroCompte;
	}

	public int getDevise() {
		return devise;
	}

	public void setDevise(int devise) {
		this.devise = devise;
	}

	public double getSolde() {
		return solde;
	}

	public void setSolde(double solde) {
		this.solde = solde;
	}

	public double getPlafondJournalier() {
		return plafondJournalier;
	}

	public void setPlafondJournalier(double plafondJournalier) {
		this.plafondJournalier = plafondJournalier;
	}

	public double getPlafondTransaction() {
		return plafondTransaction;
	}

	public void setPlafondTransaction(double plafondTransaction) {
		this.plafondTransaction = plafondTransaction;
	}

	public LocalDateTime getDateOuverture() {
		return dateOuverture;
	}

	public void setDateOuverture(LocalDateTime dateOuverture) {
		this.dateOuverture = dateOuverture;
	}

	public String getStatutCompte() {
		return statutCompte;
	}

	public void setStatutCompte(String statutCompte) {
		this.statutCompte = statutCompte;
	}
    
    
}
