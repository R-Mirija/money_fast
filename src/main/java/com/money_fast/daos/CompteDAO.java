package com.money_fast.daos;

import java.util.List;

import com.money_fast.beans.Compte;

public interface CompteDAO {
	boolean create(Compte compte);

	boolean update(Compte compte);

	boolean delete(Integer idCompte);
	
	Compte findById(Integer idCompte);

	List<Compte> findByIdClient(Integer idClient);

	List<Compte> findAll();
}
