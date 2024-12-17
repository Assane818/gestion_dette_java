package com.asn.data.controllers;

import java.util.Arrays;
import java.util.Scanner;

import com.asn.core.controllers.Controller;
import com.asn.data.entities.Article;
import com.asn.data.entities.Client;
import com.asn.data.entities.User;
import com.asn.data.services.ArticleService;
import com.asn.data.services.ClientService;
import com.asn.data.services.DetteService;
import com.asn.data.services.UserService;
import com.asn.data.views.ArticleView;
import com.asn.data.views.ClientView;
import com.asn.data.views.DetteView;
import com.asn.data.views.UserView;

public class AdminController implements Controller {
    private Scanner scanner;
    private ClientService clientService;
    private ClientView clientView;
    private UserService userService;
    private UserView userView;
    private ArticleService articleService;
    private ArticleView articleView;
    private DetteService detteService;
    private DetteView detteView;

    public AdminController(Scanner scanner, ClientService clientService, ClientView clientView, UserService userService,
            UserView userView, ArticleService articleService, ArticleView articleView, DetteService detteService,
            DetteView detteView) {
        this.scanner = scanner;
        this.clientService = clientService;
        this.clientView = clientView;
        this.userService = userService;
        this.userView = userView;
        this.articleService = articleService;
        this.articleView = articleView;
        this.detteService = detteService;
        this.detteView = detteView;
    }

    @Override
    public int showMenu() {
        System.out.println("1-Creer un compte utilisateur a un client");
        System.out.println("2-Creer un compte utilisateur avec un role Boutiquier ou  Admin");
        System.out.println("3-Désactiver/Activer  un compte utilisateur");
        System.out.println("4-Afficher les comptes utilisateurs  actifs ou par role");
        System.out.println("5-Créer un article");
        System.out.println("6-lister les articles");
        System.out.println("7-filtrer les articles par disponibilité");
        System.out.println("8-Mettre à jour la qté en stock d’un article");
        System.out.println("9-Archiver les dette solde");
        System.out.println("10-Deconnexion");
        System.out.println("Entrer votre choix");
        return scanner.nextInt();
    }

    @Override
    public void run(int choix) {
        switch (choix) {
            case 1:
                Client client = clientService.getByPhone(clientView.saisieString("Entrer le numero de telephone"));
                if (client == null || client.getUser() != null) {
                    System.out.println("Le client n'existe pas ou a deja un compte utilisateur");
                    break;
                }
                User user1 = userView.saisie();
                int id = userService.save(user1);
                clientService.updateUserId(client, id);
                break;
            case 2:
                userService.save(userView.saisieBoutiquierOrAdmin(userView.saisieRole()));
                break;
            case 3:
                userView.affiche(userService.show());
                User user = userService.getById(userView.saisieInt("Enter l'id de l'utilisateur"));
                if (user == null) {
                    System.out.println("l'utilisateur n'existe pas");
                    break;
                }
                userService.updateEtat(user, userView.saisieEtat());
                break;
            case 4:
                int filter;
                do {
                    filter = userView.choiceFilter(Arrays.asList("Actif", "Role"));
                    switch (filter) {
                        case 1:
                            userView.affiche(userService.getUsersByEtat());
                            break;
                        case 2:
                            userView.affiche(userService.getUsersByRole(userView.saisieAllRole()));
                            break;
                    }                    
                } while (filter != 1 && filter != 2);
                break;
            case 5:
                articleService.save(articleView.saisie());
                break;
            case 6:
                articleView.affiche(articleService.show());
                break;
            case 7:
                articleView.affiche(articleService.getByDisponiblity());
                break;
            case 8:
                articleView.affiche(articleService.show());
                Article article = articleService.getByLibelle(articleView.saisieString("Entrer le lebelle de l'article"));
                if (article == null) {
                    System.out.println("l'article n'existe pas");
                    break;
                }
                articleService.update(article, articleView.saisieDouble("Entrer la nouvelle quantité"));
                break;
            case 9:
                detteService.archiverDettesSolde(detteService.show());
                System.out.println("Dette archivee avec succes");
                break;
            default:
                break;
        }
    }
    
}
