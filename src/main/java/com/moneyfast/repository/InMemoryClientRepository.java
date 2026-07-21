package com.moneyfast.repository;

import java.util.ArrayList;
import java.util.List;
import com.moneyfast.model.Client;

public class InMemoryClientRepository implements ClientRepository {
    
    private static final List<Client> clients = new ArrayList<>();
    private static Long currentId = 1L;

    @Override
    public void save(Client client) {
        client.setIdClient(currentId++);
        clients.add(client);
    }

    @Override
    public Client findById(Long id) {
        for (Client c : clients) {
            if (c.getIdClient().equals(id)) {
                return c;
            }
        }
        return null;
    }

    @Override
    public Client findByEmail(String email) {
        for (Client c : clients) {
            if (c.getMail().equalsIgnoreCase(email)) {
                return c;
            }
        }
        return null;
    }

    @Override
    public Client findByTelephone(String telephone) {
        for (Client c : clients) {
            if (c.getNumeroTelephone().equals(telephone)) {
                return c;
            }
        }
        return null;
    }

    @Override
    public List<Client> findAll() {
        return clients;
    }

    @Override
    public void update(Client client) {
        Client existing = findById(client.getIdClient());
        if (existing != null) {
            existing.setNom(client.getNom());
            existing.setPrenom(client.getPrenom());
            existing.setNumeroTelephone(client.getNumeroTelephone());
            existing.setPays(client.getPays());
            existing.setDevisePreferee(client.getDevisePreferee());
            existing.setMail(client.getMail());
            existing.setStatutClient(client.getStatutClient());
        }
    }

    @Override
    public void delete(Long id) {
        clients.removeIf(c -> c.getIdClient().equals(id));
    }
}