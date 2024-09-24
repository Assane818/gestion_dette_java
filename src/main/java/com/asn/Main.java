package com.asn;

import java.util.Scanner;

import com.asn.core.factory.ArticleFactory;
import com.asn.core.factory.ClientFactory;
import com.asn.core.factory.UserFactory;
import com.asn.data.entities.Article;
import com.asn.data.entities.Client;
import com.asn.data.entities.User;
import com.asn.data.repositories.ArticleRepository;
import com.asn.data.repositories.ClientRepository;
import com.asn.data.repositories.UserRepository;
import com.asn.data.repositories.list.ArticleRepositoryImpl;
import com.asn.data.services.ArticleServiceImpl;
import com.asn.data.services.ClientService;
import com.asn.data.services.UserService;
import com.asn.data.views.ArticleView;
import com.asn.data.views.ClientView;
import com.asn.data.views.UserView;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
        public static void main(String[] args) {
            ClientFactory clientFactory = new ClientFactory();
            UserFactory userFactory = new UserFactory();
            ArticleFactory articleFactory = new ArticleFactory();
            UserRepository userRepository = userFactory.getUserRepository();
            UserService userService = userFactory.getUserService();
            ClientRepository clientRepository = clientFactory.getClientRepository(userRepository);
            ClientService clientService = clientFactory.getClientService();
            UserView userView = userFactory.getUserView(scanner);
            ClientView clientView = clientFactory.getClientView(scanner);
            ArticleRepository articleRepository = articleFactory.getArticleRepository();
            ArticleServiceImpl articleService = articleFactory.getArticleService();
            ArticleView articleView = articleFactory.getArticleView(scanner);
            int choix;
            do {
                choix = showMenu();
                switch (choix) {
                    case 1:
                        Client client1 = clientView.saisie();
                        System.out.println("Voulez-vous ajouter un compte utilisateur O/N");
                        char rs = scanner.next().charAt(0);
                        if (rs == 'O') {
                            User user = userView.saisie();
                            client1.setUser(user);
                        }
                        clientService.save(client1);
                        break;
                    case 2:
                        Client client = clientService.getByPhone(clientView.saisieString("Entrer le numero de telephone"));
                        if (client == null) {
                            System.out.println("Le client n'existe pas");
                            break;
                        }
                        User user1 = userView.saisie();
                        client.setUser(user1);
                        userService.save(user1);
                        break;
                    case 3:
                        userService.save(userView.saisieBoutiquierOrAdmin(userView.saisieRole()));
                        break;
                    case 4:
                        User user = userService.getById(userView.saisieInt("Enter l'id de l'utilisateur"));
                        if (user == null) {
                            System.out.println("l'utilisateur n'existe pas");
                            break;
                        }
                        userService.updateEtat(user, userView.saisieEtat());
                        break;
                    case 5:
                        int filter;
                        do {
                            filter = userView.choiceFilter();
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
                    case 6:
                        articleService.save(articleView.saisie());
                        break;
                    case 7:
                        articleView.affiche(articleService.show());
                        break;
                    case 8:
                        articleView.affiche(articleService.getByDisponiblity());
                        break;
                    case 9:
                        Article article = articleService.getById(articleView.saisieInt("Entrer l'id de l'article"));
                        if (article == null) {
                            System.out.println("l'article n'existe pas");
                            break;
                        }
                        articleService.update(article, articleView.saisieDouble("Entrer la nouvelle quantité"));
                        break;

                    case 10:
                        clientView.affiche(clientService.show());
                        break;
                    case 11:
                        System.out.println(clientService.getByPhone(clientView.saisieString("Entrer le numero de telephone a rechercher")));
                        break;

                }
            } while (choix != 0);
            
            
        }
        public static int showMenu() {
            System.out.println("1-Creer un client");
            System.out.println("2-Creer un compte utilisateur a un client");
            System.out.println("3-Creer un compte utilisateur avec un role Boutiquier ou  Admin");
            System.out.println("4-Désactiver/Activer  un compte utilisateur");
            System.out.println("5-Afficher les comptes utilisateurs  actifs ou par role");
            System.out.println("6-Créer un article");
            System.out.println("7-lister les articles");
            System.out.println("8-filtrer les articles par disponibilité");
            System.out.println("9-Mettre à jour la qté en stock d’un article");
            System.out.println("10-lister client");
            System.out.println("11-chercher un client par numero de telephone");
            System.out.println("Entrer votre choix:");
            return scanner.nextInt();
        }
}