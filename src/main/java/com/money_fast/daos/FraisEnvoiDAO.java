package com.money_fast.daos;

import java.util.List;

import com.money_fast.beans.FraisEnvoi;

public interface FraisEnvoiDAO {
	void create(FraisEnvoi fraisEnvoi);

	FraisEnvoi findById(Integer idFrais);

	FraisEnvoi findByCode(Integer codeFrais);

	// Recherche les frais applicables pour une devise et un montant donné
	FraisEnvoi findApplicableFrais(Integer idDevise, Double montant);

	List<FraisEnvoi> findAllActive();

	void update(FraisEnvoi fraisEnvoi);
}