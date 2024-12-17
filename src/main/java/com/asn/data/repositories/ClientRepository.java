package com.asn.data.repositories;

import java.util.List;

import com.asn.core.repository.Repository;
import com.asn.data.entities.Client;
import com.asn.data.entities.User;

public interface ClientRepository extends Repository<Client> {
    Client selectByPhone(String phone);
    Client selectBySurname(String surname);
    void updateUserId(Client object, int id);
    List<Client> selectClientAccount();
    List<Client> selectClientNoAccount();
    Client selectByUser(User user);
}
