package com.asn.data.services.impl;


import java.util.List;

import com.asn.data.entities.Client;
import com.asn.data.entities.User;
import com.asn.data.repositories.ClientRepository;
import com.asn.data.services.ClientService;

public class ClientServiceImpl implements ClientService {
    private ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
    public int save(Client user) {
        return this.clientRepository.insert(user);
    }

    public Client getByPhone(String phone) {
        return clientRepository.selectByPhone(phone);
    }
    @Override
    public List<Client> show() {
        return clientRepository.selectAll(Client.class);
    }
    @Override
    public Client getById(int id) {
        return clientRepository.selectById(id);
    }
    @Override
    public Client getBySurname(String surname) {
        return clientRepository.selectBySurname(surname);
    }
    @Override
    public List<Client> getClientAccount() {
        return clientRepository.selectClientAccount();
    }
    @Override
    public List<Client> getClientNoAccount() {
        return clientRepository.selectClientNoAccount();
    }
    @Override
    public void updateUserId(Client object, int id) {
        clientRepository.updateUserId(object, id);
    }
    @Override
    public Client getByUser(User user) {
        return clientRepository.selectByUser(user);
    }
    
    
}
