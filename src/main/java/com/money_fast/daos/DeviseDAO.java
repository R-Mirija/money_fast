package com.money_fast.daos;

import java.util.List;

import com.money_fast.beans.Devise;

public interface DeviseDAO {
	Devise findById(Integer idDevise);

	List<Devise> findAll();
}