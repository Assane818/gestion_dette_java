package com.asn.data.repositories;

import com.asn.core.repository.Repository;
import com.asn.data.entities.Client;

public interface ClientRepository extends Repository<Client> {
    Client selectByPhone(String phone);
    Client selectBySurname(String surname);
}
