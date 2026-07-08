package com.money_fast.daos;

import java.util.List;

import com.money_fast.beans.TauxDeChange;

public interface TauxDeChangeDAO {
    void create(TauxDeChange tauxDeChange);
    TauxDeChange findById(Integer idTaux);
    TauxDeChange findByCode(Integer codeTaux);
    // Recherche le taux en vigueur pour une conversion spécifique
    TauxDeChange findCurrentRate(Integer idDeviseSource, Integer idDeviseDest, Double montant);
    List<TauxDeChange> findAllActive();
    void update(TauxDeChange tauxDeChange);
}