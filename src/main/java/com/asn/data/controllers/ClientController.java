package com.asn.data.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.asn.core.controllers.Controller;
import com.asn.core.controllers.UserConnect;
import com.asn.data.entities.Client;
import com.asn.data.entities.Detail;
import com.asn.data.entities.Dette;
import com.asn.data.enums.Etat;
import com.asn.data.services.ClientService;
import com.asn.data.services.DetailService;
import com.asn.data.services.DetteService;
import com.asn.data.services.impl.PayementService;
import com.asn.data.views.ClientView;
import com.asn.data.views.DetailView;
import com.asn.data.views.DetteView;
import com.asn.data.views.PayementView;

public class ClientController implements Controller{
    private Scanner scanner;
    private ClientService clientService;
    private ClientView clientView;
    private DetteService detteService;
    private DetteView detteView;
    private DetailService detailService;
    private DetailView detailView;
    private PayementService payementService;
    private PayementView payementView;

    public ClientController(Scanner scanner, ClientService clientService, ClientView clientView,
            DetteService detteService, DetteView detteView, DetailService detailService, DetailView detailView,
            PayementService payementService, PayementView payementView) {
        this.scanner = scanner;
        this.clientService = clientService;
        this.clientView = clientView;
        this.detteService = detteService;
        this.detteView = detteView;
        this.detailService = detailService;
        this.detailView = detailView;
        this.payementService = payementService;
        this.payementView = payementView;
    }

    @Override
    public int showMenu() {
        System.out.println("1-lister mes dettes non solde");
        System.out.println("2-Creer une dette");
        System.out.println("3-Lister les dettes par etat");
        System.out.println("4-Envoyer une relance pour une  demande de dette annuler");
        System.out.println("5-Deconnexion");
        System.out.println("Entrer votre choix");
        return scanner.nextInt();
    }

    @Override
    public void run(int choix) {
        Client client2 = clientService.getByUser(UserConnect.getUserConnect());
        switch (choix) {
            case 1:
                List<Dette> dettesNoSolde = detteService.showDettesNoSoldeClient(client2);
                detteView.affiche(dettesNoSolde);
                int choice = detteView.choiceFilter(Arrays.asList("Article", "Payement"));
                switch (choice) {
                    case 1:
                        scanner.nextLine();
                        Dette dette1 = detteService.getById(clientView.saisieInt("Entrer l'id de la dette"));
                        if (dette1 == null || dette1.getClient().getId() != client2.getId()) {
                            System.out.println("la dette n'existe pas");
                            break;
                        }
                        detailView.affiche(detailService.showDetailsInDette(dette1));
                        break;
                    case 2:
                        Dette dette2 = detteService.getById(clientView.saisieInt("Entrer l'id de la dette"));
                        if (dette2 == null || dette2.getClient().getId() != client2.getId()) {
                            System.out.println("la dette n'existe pas");
                            break;
                        }
                        payementView.affiche(payementService.showPayementsInDette(dette2));
                        break;
                    default:
                        break;
                }
                break;
               
            case 2:
                Dette dette = detteView.saisie();
                if (dette == null) {
                    break;
                }
                int id1 = detteService.save(dette);
                for (Detail detail : dette.getDetails()) {
                    detail.setDette(detteService.getById(id1));
                    detailService.save(detail);
                }
                break;
            case 3:
                List<Dette> dettes = detteService.getDettesByEtat(detteView.saisieEtat());
                detteView.affiche(detteService.getDettesByClient(dettes, client2));
                break;
            case 4:
                detteView.affiche(detteService.getDettesByEtat(Etat.REFUSER));
                Dette dette3 = detteService.getById(clientView.saisieInt("Entrer l'id de la dette a relancer"));
                if (dette3 == null || dette3.getClient().getId() != client2.getId() && !dette3.getEtat().equals(Etat.REFUSER)) {
                    System.out.println("la dette n'existe pas ou n'a pas ete refuser");
                    break;
                }
                detteService.updateEtatDette(dette3, Etat.ENCOURS);
                break;
            default:
                break;
        }
    }
    
}
