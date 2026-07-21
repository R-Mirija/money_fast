package com.moneyfast.repository;

import com.moneyfast.model.Admin;

public interface AdminRepository {
    void save(Admin admin);
    Admin findByUsername(String username);
    Admin findByEmail(String email);
    boolean hasAnyAdmin();
}