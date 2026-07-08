package com.money_fast.daos;

import java.util.List;

import com.money_fast.beans.Client;

public interface ClientDAO {
	void create(Client client);

	Client findById(Integer idClient);

	Client findByMail(String mail);

	List<Client> findAll();

	void update(Client client);

	void delete(Integer idClient);
}
