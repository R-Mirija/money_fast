package com.moneyfast.model;

import java.sql.Timestamp;

public class Frais {

    public static final int TYPE_FIXE = 1;
    public static final int TYPE_POURCENTAGE = 2;

    private int idFrais;
    private int codeFrais;
    private int deviseFrais;
    private double montantMin;
    private double montantMax;
    private int typeFrais;
    private double valeurFrais;
    private Timestamp dateDebutValidite;
    private Timestamp dateFinValidite;
    private boolean active;

    public Frais() {
    }

    public int getIdFrais() {
        return idFrais;
    }

    public void setIdFrais(int idFrais) {
        this.idFrais = idFrais;
    }

    public int getCodeFrais() {
        return codeFrais;
    }

    public void setCodeFrais(int codeFrais) {
        this.codeFrais = codeFrais;
    }

    public int getDeviseFrais() {
        return deviseFrais;
    }

    public void setDeviseFrais(int deviseFrais) {
        this.deviseFrais = deviseFrais;
    }

    public double getMontantMin() {
        return montantMin;
    }

    public void setMontantMin(double montantMin) {
        this.montantMin = montantMin;
    }

    public double getMontantMax() {
        return montantMax;
    }

    public void setMontantMax(double montantMax) {
        this.montantMax = montantMax;
    }

    public int getTypeFrais() {
        return typeFrais;
    }

    public void setTypeFrais(int typeFrais) {
        this.typeFrais = typeFrais;
    }

    /** Libellé lisible du type de frais, pour l'affichage JSP. */
    public String getTypeFraisLibelle() {
        return typeFrais == TYPE_POURCENTAGE ? "Pourcentage" : "Montant fixe";
    }

    public double getValeurFrais() {
        return valeurFrais;
    }

    public void setValeurFrais(double valeurFrais) {
        this.valeurFrais = valeurFrais;
    }

    public Timestamp getDateDebutValidite() {
        return dateDebutValidite;
    }

    public void setDateDebutValidite(Timestamp dateDebutValidite) {
        this.dateDebutValidite = dateDebutValidite;
    }

    public Timestamp getDateFinValidite() {
        return dateFinValidite;
    }

    public void setDateFinValidite(Timestamp dateFinValidite) {
        this.dateFinValidite = dateFinValidite;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    /** Calcule le montant du frais pour un montant transféré donné. */
    public double calculerMontantFrais(double montantTransfere) {
        if (typeFrais == TYPE_POURCENTAGE) {
            return montantTransfere * (valeurFrais / 100.0);
        }
        return valeurFrais;
    }
}