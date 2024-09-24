package com.asn.data.services;


import java.util.List;

import com.asn.data.entities.Client;
import com.asn.data.repositories.ClientRepository;

public class ClientServiceImpl implements ClientService {
    private ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
    public boolean save(Client user) {
        this.clientRepository.insert(user);
        return true;
    }

    public Client getByPhone(String phone) {
        return clientRepository.selectByPhone(phone);
    }
    @Override
    public List<Client> show() {
        return clientRepository.selectAll();
    }
    @Override
    public Client getById(int id) {
        return clientRepository.selectById(id);
    }
    @Override
    public Client getBySurname(String surname) {
        return clientRepository.selectBySurname(surname);
    }
    
    
}
