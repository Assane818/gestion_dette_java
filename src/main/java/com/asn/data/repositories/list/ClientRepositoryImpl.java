package com.asn.data.repositories.list;

import com.asn.core.repository.impl.RepositoryImpl;
import com.asn.data.entities.Client;
import com.asn.data.repositories.ClientRepository;

public class ClientRepositoryImpl extends RepositoryImpl<Client> implements ClientRepository{

    public Client selectByPhone(String phone) {
        for (Client client: list) {
            if (client.getPhone().compareToIgnoreCase(phone) == 0) {
                return client;
            }
        }
        return null;
    }

    @Override
    public Client selectById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectById'");
    }

    @Override
    public Client selectBySurname(String surname) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectBySurname'");
    }

    
}
