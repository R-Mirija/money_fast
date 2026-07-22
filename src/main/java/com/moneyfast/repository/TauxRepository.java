package com.moneyfast.repository;

import java.util.List;
import com.moneyfast.model.TauxDeChange;

public interface TauxRepository {
    void save(TauxDeChange taux);

    List<TauxDeChange> findAll();

    TauxDeChange findActifByDevises(int deviseSource, int deviseDestination, double montant);

    void updateTaux(int idTaux, float nouveauTaux, boolean active);

    void delete(int idTaux);
}