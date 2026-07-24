package com.moneyfast.model;

import java.time.LocalDateTime;

import com.moneyfast.enums.StatutTransfertEnum;

public class Transfert {
    private Long idTransfert;
    private int codeTransfert;
    private Long idCompteSource;
    private Long idCompteDestination;
    private double montantEnvoye;
    private double montantRecu;
    private double frais;
    private int tauxApplique;
    private int deviseSource;
    private int deviseDestination;
    private LocalDateTime dateTransfert;
    private String raison;
    private StatutTransfertEnum statutTransfert;
    private LocalDateTime dateConfirmation;
    private String referenceExterne;

    public Transfert() {
    }

    public Transfert(Long idTransfert, int codeTransfert, Long idCompteSource, Long idCompteDestination,
            double montantEnvoye, double montantRecu, double frais, int tauxApplique,
            int deviseSource, int deviseDestination, LocalDateTime dateTransfert, String raison,
            StatutTransfertEnum statutTransfert, LocalDateTime dateConfirmation, String referenceExterne) {
        this.idTransfert = idTransfert;
        this.codeTransfert = codeTransfert;
        this.idCompteSource = idCompteSource;
        this.idCompteDestination = idCompteDestination;
        this.montantEnvoye = montantEnvoye;
        this.montantRecu = montantRecu;
        this.frais = frais;
        this.tauxApplique = tauxApplique;
        this.deviseSource = deviseSource;
        this.deviseDestination = deviseDestination;
        this.dateTransfert = dateTransfert;
        this.raison = raison;
        this.statutTransfert = statutTransfert;
        this.dateConfirmation = dateConfirmation;
        this.referenceExterne = referenceExterne;
    }

    public static int genererCodeTransfert() {
        return (int) (Math.random() * 900_000_000) + 100_000_000;
    }

    public Long getIdTransfert() {
        return idTransfert;
    }

    public void setIdTransfert(Long idTransfert) {
        this.idTransfert = idTransfert;
    }

    public int getCodeTransfert() {
        return codeTransfert;
    }

    public void setCodeTransfert(int codeTransfert) {
        this.codeTransfert = codeTransfert;
    }

    public Long getIdCompteSource() {
        return idCompteSource;
    }

    public void setIdCompteSource(Long idCompteSource) {
        this.idCompteSource = idCompteSource;
    }

    public Long getIdCompteDestination() {
        return idCompteDestination;
    }

    public void setIdCompteDestination(Long idCompteDestination) {
        this.idCompteDestination = idCompteDestination;
    }

    public double getMontantEnvoye() {
        return montantEnvoye;
    }

    public void setMontantEnvoye(double montantEnvoye) {
        this.montantEnvoye = montantEnvoye;
    }

    public double getMontantRecu() {
        return montantRecu;
    }

    public void setMontantRecu(double montantRecu) {
        this.montantRecu = montantRecu;
    }

    public double getFrais() {
        return frais;
    }

    public void setFrais(double frais) {
        this.frais = frais;
    }

    public int getTauxApplique() {
        return tauxApplique;
    }

    public void setTauxApplique(int tauxApplique) {
        this.tauxApplique = tauxApplique;
    }

    public int getDeviseSource() {
        return deviseSource;
    }

    public void setDeviseSource(int deviseSource) {
        this.deviseSource = deviseSource;
    }

    public int getDeviseDestination() {
        return deviseDestination;
    }

    public void setDeviseDestination(int deviseDestination) {
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

    public StatutTransfertEnum getStatutTransfert() {
        return statutTransfert;
    }

    public void setStatutTransfert(StatutTransfertEnum statutTransfert) {
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