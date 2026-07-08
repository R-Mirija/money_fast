package com.money_fast.daos;

import java.util.List;

import com.money_fast.beans.Transfert;

public interface TransfertDAO {
	void create(Transfert transfert);

	Transfert findById(Integer idTransfert);

	List<Transfert> findByCompteSource(Integer idCompteSource);

	List<Transfert> findAll();

	void updateStatus(Integer idTransfert, String statut);
}