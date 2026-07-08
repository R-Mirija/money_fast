package com.money_fast.daos;

import java.util.List;

import com.money_fast.beans.Pays;

public interface PaysDAO {
    Pays findById(Integer idDevise);
    List<Pays> findAll();
}