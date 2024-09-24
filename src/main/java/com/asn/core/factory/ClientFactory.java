package com.asn.core.factory;

import java.util.Scanner;

import com.asn.data.repositories.ClientRepository;
import com.asn.data.repositories.UserRepository;
import com.asn.data.repositories.db.ClientRepositoryImplBd;
import com.asn.data.services.ClientService;
import com.asn.data.services.ClientServiceImpl;
import com.asn.data.views.ClientView;

public class ClientFactory {
    private  ClientRepository clientRepository;
    private ClientService clientService;
    private ClientView clientView;

    public ClientRepository getClientRepository(UserRepository userRepository) {
        if (clientRepository == null) {
            clientRepository = new ClientRepositoryImplBd(userRepository);
        }
        return clientRepository;
    }

    public ClientService getClientService() {
        if (clientService == null) {
            clientService = new ClientServiceImpl(this.clientRepository);
        }
        return clientService;
    }
    
    public ClientView getClientView(Scanner scanner) {
        if (clientView == null) {
            clientView = new ClientView(scanner, this.clientService);
        }
        return clientView;
    }
}
