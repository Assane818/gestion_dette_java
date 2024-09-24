package com.asn.data.views;

import java.util.Scanner;

import com.asn.data.entities.Client;
import com.asn.data.services.ClientService;

public class ClientView extends ViewImpl<Client> {
    private ClientService clientService;
    public ClientView(Scanner scanner, ClientService clientService) {
        super(scanner);
        this.clientService = clientService;
    }

    @Override
    public Client saisie() {
        Client client = new Client();
        scanner.nextLine();
        System.out.println("Saisir le surname de l'utilisateur");
        client.setSurname(scanner.nextLine());
        if (clientService.getBySurname(client.getSurname()) != null) {
            System.out.println("le client existe deja");
            return null;
        }
        System.out.println("Saisir le numero de telephone de l'utilisateur");
        client.setPhone(scanner.nextLine());
        System.out.println("Saisir l'adresse de l'utilisateur");
        client.setAddress(scanner.nextLine());
        return client;
    }
    
}
