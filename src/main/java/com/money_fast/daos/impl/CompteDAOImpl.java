package com.money_fast.daos.impl;

import com.money_fast.beans.Compte;
import com.money_fast.daos.CompteDAO;
import java.sql.*;
import java.util.List;

public class CompteDAOImpl implements CompteDAO {
	private final Connection connection;

	public CompteDAOImpl(Connection connection) {
		this.connection = connection;
	}

	@Override
	public boolean create(Compte compte) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Compte compte) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Integer idCompte) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Compte findById(Integer idCompte) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Compte> findByIdClient(Integer idClient) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Compte> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
