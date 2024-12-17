package com.asn;

import java.util.Scanner;

import com.asn.core.controllers.UserConnect;
import com.asn.core.factory.RepositoryFactory;
import com.asn.core.factory.ServiceFactory;
import com.asn.core.factory.ViewFactory;
import com.asn.core.factory.Impl.RepositoryFactoryImpl;
import com.asn.core.factory.Impl.ServiceFactoryImpl;
import com.asn.core.factory.Impl.ViewFactoryImpl;
import com.asn.data.controllers.AdminController;
import com.asn.data.controllers.BoutiquierController;
import com.asn.data.controllers.ClientController;
import com.asn.data.entities.Article;
import com.asn.data.entities.Client;
import com.asn.data.entities.Detail;
import com.asn.data.entities.Dette;
import com.asn.data.entities.Payement;
import com.asn.data.entities.User;
import com.asn.data.services.ArticleService;
import com.asn.data.services.ClientService;
import com.asn.data.services.DetailService;
import com.asn.data.services.DetteService;
import com.asn.data.services.PayementService;
import com.asn.data.services.UserService;
import com.asn.data.views.ArticleView;
import com.asn.data.views.ClientView;
import com.asn.data.views.DetailView;
import com.asn.data.views.DetteView;
import com.asn.data.views.PayementView;
import com.asn.data.views.UserView;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        
        //repository
        RepositoryFactory<User> userRepositoryFactory = new RepositoryFactoryImpl<>(User.class);
        RepositoryFactory<Client> clientRepositoryFactory = new RepositoryFactoryImpl<>(Client.class);
        RepositoryFactory<Article> articleRepositoryFactory = new RepositoryFactoryImpl<>(Article.class);
        RepositoryFactory<Dette> detteRepositoryFactory = new RepositoryFactoryImpl<>(Dette.class);
        RepositoryFactory<Detail> detailRepositoryFactory = new RepositoryFactoryImpl<>(Detail.class);
        RepositoryFactory<Payement> payementRepositoryFactory = new RepositoryFactoryImpl<>(Payement.class);
        var userRepository = userRepositoryFactory.getRepository();
        var detailRepository = detailRepositoryFactory.getRepository();
        var clientRepository = clientRepositoryFactory.getRepository();
        var articleRepository = articleRepositoryFactory.getRepository();
        var detteRepository = detteRepositoryFactory.getRepository();
        var payementRepository = payementRepositoryFactory.getRepository();

        //service
        ServiceFactory<User> userServiceFactory = new ServiceFactoryImpl<>(User.class, userRepository);
        ServiceFactory<Client> clientServiceFactory = new ServiceFactoryImpl<>(Client.class, clientRepository);
        ServiceFactory<Article> articleServiceFactory = new ServiceFactoryImpl<>(Article.class, articleRepository);
        ServiceFactory<Dette> detteServiceFactory = new ServiceFactoryImpl<>(Dette.class, detteRepository);
        ServiceFactory<Detail> detailServiceFactory = new ServiceFactoryImpl<>(Detail.class, detailRepository);
        ServiceFactory<Payement> payementServiceFactory = new ServiceFactoryImpl<>(Payement.class, payementRepository);

        var userService = (UserService)userServiceFactory.getService();
        var clientService = (ClientService)clientServiceFactory.getService();
        var articleService = (ArticleService)articleServiceFactory.getService();
        var detteService = (DetteService)detteServiceFactory.getService();
        var detailService = (DetailService)detailServiceFactory.getService();
        var payementService = (PayementService)payementServiceFactory.getService();

        //view
        ViewFactory<User> userViewFactory = new ViewFactoryImpl<>(User.class, userService);
        ViewFactory<Client> clientViewFactory = new ViewFactoryImpl<>(Client.class, clientService);
        ViewFactory<Article> articleViewFactory = new ViewFactoryImpl<>(Article.class, articleService);
        ViewFactory<Dette> detteViewFactory = new ViewFactoryImpl(Dette.class, clientService, articleService, detailService);
        ViewFactory<Detail> detailViewFactory = new ViewFactoryImpl<>(Detail.class, null);
        ViewFactory<Payement> payementViewFactory = new ViewFactoryImpl<>(Payement.class, null);

        var userView = (UserView)userViewFactory.getView();
        var clientView = (ClientView)clientViewFactory.getView();
        var articleView = (ArticleView)articleViewFactory.getView();
        var detteView = (DetteView)detteViewFactory.getView();
        var detailView = (DetailView)detailViewFactory.getView();
        var payementView = (PayementView)payementViewFactory.getView();

        do {
            User userConnect = userView.saisieConnexion();
            if (userConnect == null) {
                System.out.println("Login ou mot de passe invalide");
                continue;
            }
            UserConnect.setUserConnect(userConnect);
            int choix = 0;
            switch (userConnect.getRole()) {
                case ADMIN:
                    AdminController adminController = new AdminController(scanner, clientService, clientView, userService, userView, articleService, articleView, detteService, detteView);
                    System.out.println("-------------------------");
                    System.out.println("BIENVENUE CHER ADMIN");
                    System.out.println("-------------------------");
                    do {
                        choix = adminController.showMenu();
                        adminController.run(choix);
                    } while (choix != 10);
                    break;
                case CLIENT:
                    ClientController clientController = new ClientController(scanner, clientService, clientView, detteService, detteView, detailService, detailView, payementService, payementView);
                    System.out.println("-------------------------");
                    System.out.println("BIENVENUE CHER CLIENT");
                    System.out.println("-------------------------");
                    do {
                        choix = clientController.showMenu();
                        clientController.run(choix);
                    } while (choix != 5);
                    break;
                case BOUTIQUIER:
                    BoutiquierController boutiquierController = new BoutiquierController(scanner, clientView, userView, userService, clientService,  detailService, detteView, detteService, payementView, payementService, detailView, articleService);
                    System.out.println("-------------------------");
                    System.out.println("BIENVENUE CHER BOUTIQUIER");
                    System.out.println("-------------------------");
                    do {
                        choix = boutiquierController.showMenu();
                        boutiquierController.run(choix);
                    } while (choix != 9);
                    break;
                default:
                    break;
     
            }
        } while (true);
    }

}
