package com.money_fast.daos;

import java.util.List;

import com.money_fast.beans.Client;

public interface ClientDAO {
	boolean create(Client client);

	boolean update(Client client);

	boolean delete(Integer idClient);

	List<Client> findLike(Client client);

	List<Client> findAll();
}
