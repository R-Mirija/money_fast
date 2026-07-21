package com.moneyfast.repository;

import java.time.LocalDate;
import java.util.List;
import com.moneyfast.model.Transfert;

public interface TransfertRepository {
    
	void save(Transfert transfert);
    
	Transfert findById(Long id);
    
	Transfert findByCode(int codeTransfert);
    
	List<Transfert> findByCompteSource(Long idCompteSource);
    
	List<Transfert> findByCompteDestination(Long idCompteDestination);
    
	List<Transfert> findByDate(LocalDate date);
    
	List<Transfert> findByCompte(Long idCompte);
    
	List<Transfert> findByCompteAndMonth(Long idCompte, int annee, int mois);
    
	void updateStatut(Long idTransfert, String statut);
}