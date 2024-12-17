package com.asn.data.views.impl;

import java.util.Scanner;

import com.asn.data.entities.Client;
import com.asn.data.services.ClientService;
import com.asn.data.views.ClientView;

public class ClientViewImpl extends ViewImpl<Client> implements ClientView {
    private ClientService clientService;
    public ClientViewImpl(Scanner scanner, ClientService clientService) {
        super(scanner);
        this.clientService = clientService;
    }

    @Override
    public Client saisie() {
        Client client = new Client();
        do {
            System.out.println("Saisir le surname de l'utilisateur");
            client.setSurname(scanner.nextLine());
        } while (clientService.getBySurname(client.getSurname()) != null);
        do {
            System.out.println("Saisir le numero de telephone de l'utilisateur");
            client.setPhone(scanner.nextLine());
        } while (clientService.getByPhone(client.getPhone()) != null);
        System.out.println("Saisir l'adresse de l'utilisateur");
        client.setAddress(scanner.nextLine());
        return client;
    }
    
}
