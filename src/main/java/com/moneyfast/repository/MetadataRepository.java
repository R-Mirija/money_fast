package com.moneyfast.repository;

import java.util.List;
import com.moneyfast.model.Pays;
import com.moneyfast.model.Devise;

public interface MetadataRepository {
    List<Pays> findAllPays();
    List<Devise> findAllDevises();
    String findLibelleDevise(int idDevise);
    void updatePaysStatut(int idPays, String statut);

}