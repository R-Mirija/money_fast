package com.moneyfast.repository;
import java.util.List;
import com.moneyfast.model.Compte;

public interface CompteRepository {
	void save(Compte compte);
	
	Compte findById(Long id);
	
	Compte findByNumero(String numeroCompte);
	
	Compte findByTelephone(String telephone);
	
	List<Compte> findByClientId(Long idClient);
	
	void updateSolde(int numeroCompte, double nouveauSolde);
	
	void updateStatut(int numeroCompte, String statut);
}
