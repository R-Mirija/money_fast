package com.money_fast.daos;

import java.util.List;

import com.money_fast.beans.Client;

public interface ClientDAO {
	void create(Client client);

	Client findById(Integer idClient);

	List<Client> findLike(Client client);

	void update(Client client);

	void delete(Integer idClient);
}
