package com.moneyfast.repository;

import java.util.List;

import com.moneyfast.model.Frais;

public interface FraisRepository {

    List<Frais> findAll();

    Frais findById(int idFrais);

    /**
     * Retourne le frais actif applicable à une devise et un montant donnés
     * (montant_min <= montant <= montant_max), en tenant compte de la
     * période de validité si elle est renseignée. Retourne null si aucun
     * frais ne correspond.
     */
    Frais findApplicable(int idDevise, double montant);

    void save(Frais frais);

    void delete(int idFrais);
}