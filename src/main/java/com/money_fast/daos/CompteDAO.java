package com.money_fast.daos;

import java.util.List;

import com.money_fast.beans.Compte;

public interface CompteDAO {
	void create(Compte compte);

	Compte findById(Integer idCompte);

	List<Compte> findByIdClient(Integer idClient);

	List<Compte> findAll();

	void update(Compte compte);

	void delete(Integer idCompte);
}
