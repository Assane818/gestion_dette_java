package com.asn.data.repositories.list;

import java.util.ArrayList;
import java.util.List;

import com.asn.core.repository.impl.RepositoryListImpl;
import com.asn.data.entities.Client;
import com.asn.data.entities.User;
import com.asn.data.repositories.ClientRepository;
import com.asn.data.repositories.UserRepository;

public class ClientRepositoryImplList extends RepositoryListImpl<Client> implements ClientRepository{
    private UserRepository userRepository;
    public ClientRepositoryImplList(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Client selectByPhone(String phone) {
        for (Client client : list) {
            if (client.getPhone().compareTo(phone) == 0) {
                return client;
            }
        }
        return null;
    }

    @Override
    public Client selectBySurname(String surname) {
        for (Client client : list) {
            if (client.getSurname().compareTo(surname) == 0) {
                return client;
            }
        }
        return null;
    }

    @Override
    public void updateUserId(Client object, int id) {
        object.setUser(userRepository.selectById(id));
    }

    @Override
    public List<Client> selectClientAccount() {
        List<Client> listClient = new ArrayList<>();
        for (Client client : list) {
            if (client.getUser() != null) {
                listClient.add(client);
            }
        }
        return listClient;
    }

    @Override
    public List<Client> selectClientNoAccount() {
        List<Client> listClient = new ArrayList<>();
        for (Client client : list) {
            if (client.getUser() == null) {
                listClient.add(client);
            }
        }
        return listClient;
    }

    @Override
    public Client selectByUser(User user) {
        for (Client client : list) {
            if (client.getUser().getId() == user.getId()) {
                return client;
            }
        }
        return null;
    }
    
}
