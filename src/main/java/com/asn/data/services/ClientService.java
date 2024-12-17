package com.asn.data.services;

import java.util.List;

import com.asn.data.entities.Client;
import com.asn.data.entities.User;

public interface ClientService extends Service<Client> {
    Client getByPhone(String phone);
    Client getBySurname(String surname);
    List<Client> getClientAccount();
    List<Client> getClientNoAccount();
    void updateUserId(Client object, int id);
    Client getByUser(User user);
}
