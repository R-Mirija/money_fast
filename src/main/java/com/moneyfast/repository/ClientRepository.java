package com.moneyfast.repository;

import java.util.List;
import com.moneyfast.model.Client;

public interface ClientRepository {
    void save(Client client);             
    Client findById(Long id);             
    Client findByEmail(String email);     
    Client findByTelephone(String telephone);
    List<Client> findAll();                
    void update(Client client);            
    void delete(Long id);                  
}