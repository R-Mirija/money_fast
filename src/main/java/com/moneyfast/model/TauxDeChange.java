package com.moneyfast.model;

import java.time.LocalDateTime;

public class TauxDeChange {
    private int idTaux;
    private int codeTaux;
    private int deviseSource;
    private int deviseDestination;
    private double montantMin;
    private double montantMax;
    private float tauxApplication;
    private LocalDateTime dateDebutValidite;
    private LocalDateTime dateFinValidite;
    private boolean active;

    public TauxDeChange() {}

    public int getIdTaux() { return idTaux; }
    public void setIdTaux(int idTaux) { this.idTaux = idTaux; }
    public int getCodeTaux() { return codeTaux; }
    public void setCodeTaux(int codeTaux) { this.codeTaux = codeTaux; }
    public int getDeviseSource() { return deviseSource; }
    public void setDeviseSource(int deviseSource) { this.deviseSource = deviseSource; }
    public int getDeviseDestination() { return deviseDestination; }
    public void setDeviseDestination(int deviseDestination) { this.deviseDestination = deviseDestination; }
    public double getMontantMin() { return montantMin; }
    public void setMontantMin(double montantMin) { this.montantMin = montantMin; }
    public double getMontantMax() { return montantMax; }
    public void setMontantMax(double montantMax) { this.montantMax = montantMax; }
    public float getTauxApplication() { return tauxApplication; }
    public void setTauxApplication(float tauxApplication) { this.tauxApplication = tauxApplication; }
    public LocalDateTime getDateDebutValidite() { return dateDebutValidite; }
    public void setDateDebutValidite(LocalDateTime dateDebutValidite) { this.dateDebutValidite = dateDebutValidite; }
    public LocalDateTime getDateFinValidite() { return dateFinValidite; }
    public void setDateFinValidite(LocalDateTime dateFinValidite) { this.dateFinValidite = dateFinValidite; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}