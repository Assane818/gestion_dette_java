package com.asn.data.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.asn.core.controllers.Controller;
import com.asn.data.entities.Article;
import com.asn.data.entities.Client;
import com.asn.data.entities.Detail;
import com.asn.data.entities.Dette;
import com.asn.data.entities.Payement;
import com.asn.data.entities.User;
import com.asn.data.enums.Etat;
import com.asn.data.services.ArticleService;
import com.asn.data.services.ClientService;
import com.asn.data.services.DetailService;
import com.asn.data.services.DetteService;
import com.asn.data.services.UserService;
import com.asn.data.services.impl.PayementService;
import com.asn.data.views.ClientView;
import com.asn.data.views.DetailView;
import com.asn.data.views.DetteView;
import com.asn.data.views.PayementView;
import com.asn.data.views.UserView;

public class BoutiquierController implements Controller {
    private Scanner scanner;
    private ClientView clientView;
    private UserView userView;
    private UserService userService;
    private ClientService clientService;
    private DetailService detailService;
    private DetteView detteView;
    private DetteService detteService;
    private PayementView payementView;
    private PayementService payementService;
    private DetailView detailView;
    private ArticleService articleService;
    

    public BoutiquierController(Scanner scanner, ClientView clientView, UserView userView, UserService userService,
            ClientService clientService, DetailService detailService, DetteView detteView, DetteService detteService,
            PayementView payementView , PayementService payementService, DetailView detailView, ArticleService articleService) {
        this.scanner = scanner;
        this.clientView = clientView;
        this.userView = userView;
        this.userService = userService;
        this.clientService = clientService;
        this.detailService = detailService;
        this.detteView = detteView;
        this.detteService = detteService;
        this.payementView = payementView;
        this.payementService = payementService;
        this.detailView = detailView;
        this.articleService = articleService;
    }

    @Override
    public int showMenu() {
        System.out.println("1-Creer un client");
        System.out.println("2-lister client");
        System.out.println("3-chercher un client par numero de telephone");
        System.out.println("4-Creer une dette");
        System.out.println("5-Enregistrer un payement");
        System.out.println("6-lister dette non solde d'un client");
        System.out.println("7-Lister les dettes par etat");
        System.out.println("8-Traiter une demande de dette");
        System.out.println("9-Deconnexion");
        System.out.println("Entrer votre choix");
        return scanner.nextInt();
    }

    @Override
    public void run(int choix) {
        switch (choix) {
            case 1:
                Client client1 = clientView.saisie();
                System.out.println("Voulez-vous ajouter un compte utilisateur O/N");
                char rp = scanner.next().charAt(0);
                if (rp == 'O') {
                    User user = userView.saisie();
                    int userId = userService.save(user);
                    client1.setUser(userService.getById(userId));
                }
                System.out.println(client1);
                clientService.save(client1);
                break;
            case 2:
                clientView.affiche(clientService.show());
                break;
            case 3:
                System.out.println(clientService.getByPhone(clientView.saisieString("Entrer le numero de telephone a rechercher")));
                break;
            case 4:
                clientView.affiche(clientService.show());
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
            case 5:
                detteView.affiche(detteService.getDettesByEtat(Etat.VALIDER));
                Dette dette2 = detteService.getById(detteView.saisieInt("Entrer l'id de la dette a payer"));
                if (dette2 == null || dette2.getEtat() != Etat.VALIDER || dette2.getMontant() == dette2.getMontantVerser()) {
                    System.out.println("la dette n'existe pas ou n'est pas valider");
                    break;
                }
                System.out.println(dette2);
                Payement payement = payementView.saisiePayement(dette2);
                payementService.save(payement);
                detteService.updateDette(dette2);
                detteService.archiverDettesSolde(detteService.show());
                break;
            case 6:
                Client client2 = clientService.getByPhone(clientView.saisieString("Entrer le numero de telephone"));
                if (client2 == null) {
                    System.out.println("Le client n'existe pas");
                    break;
                }
                List<Dette> dettesNoSolde = detteService.showDettesNoSoldeClient(client2);
                detteView.affiche(dettesNoSolde);
                int choice = detteView.choiceFilter(Arrays.asList("Article", "Payement"));
                switch (choice) {
                    case 1:
                        Dette dette1 = detteService.getById(clientView.saisieInt("Entrer l'id de la dette"));
                        if (dette1 == null || dette1.getClient().getId() != client2.getId()) {
                            System.out.println("la dette n'existe pas");
                            break;
                        }
                        detailView.affiche(detailService.showDetailsInDette(dette1));
                        break;
                    case 2:
                        Dette dette3 = detteService.getById(clientView.saisieInt("Entrer l'id de la dette"));
                        if (dette3 == null || dette3.getClient().getId() != client2.getId()) {
                            System.out.println("la dette n'existe pas");
                            break;
                        }
                        payementView.affiche(payementService.showPayementsInDette(dette3));
                        break;
                }
                break;
            case 7:
                detteView.affiche(detteService.getDettesByEtat(detteView.saisieEtat()));
                break;
            case 8:
                detteView.affiche(detteService.getDettesByEtat(Etat.ENCOURS));
                Dette dette1 = detteService.getById(detteView.saisieInt("Entrer l'id de la dette"));
                if (dette1 == null || dette1.getEtat() != Etat.ENCOURS) {
                    System.out.println("la dette n'existe pas");
                    break;
                }
                List<Detail> details = detailService.showDetailsInDette(dette1);
                detailView.affiche(details);
                int choice1 = detteView.choiceFilter(Arrays.asList("Valider", "Refuser"));
                switch (choice1) {
                    case 1:
                        detteService.updateEtatDette(dette1, Etat.VALIDER);
                        for (Detail detail : details) {
                            Article article = articleService.getArticleInDetail(detail);
                            articleService.update(article, article.getQuantite() - detail.getQuantite());
                        }
                        break;
                    case 2:
                        detteService.updateEtatDette(dette1, Etat.REFUSER);
                        break;
                }
                break;
            default:
                break;
        }
    }

}
