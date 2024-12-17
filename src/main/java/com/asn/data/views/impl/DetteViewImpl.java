package com.asn.data.views.impl;

import java.util.Scanner;

import com.asn.core.controllers.UserConnect;
import com.asn.data.entities.Article;
import com.asn.data.entities.Client;
import com.asn.data.entities.Detail;
import com.asn.data.entities.Dette;
import com.asn.data.enums.Etat;
import com.asn.data.enums.Role;
import com.asn.data.services.ArticleService;
import com.asn.data.services.ClientService;
import com.asn.data.services.DetailService;
import com.asn.data.views.DetteView;

public class DetteViewImpl extends ViewImpl<Dette> implements DetteView {
    private ClientService clientService;
    private ArticleService articleService;
    private DetailService detailService;

    public DetteViewImpl(Scanner scanner, ClientService clientService, ArticleService articleService, DetailService detailService) {
        super(scanner);
        this.clientService = clientService;
        this.articleService = articleService;
        this.detailService = detailService;
    }

    @Override
    public Dette saisie() {
        Dette dette = new Dette();
        if (!UserConnect.getUserConnect().getRole().equals(Role.CLIENT)) {
            Client client = clientService.getById(super.saisieInt("Entrer l'id du client"));
            dette.setClient(client);
            if (client == null) {
                return null;
            }
            client.getDettes().add(dette);
            scanner.nextLine();
        } else {
            dette.setClient(clientService.getByUser(UserConnect.getUserConnect()));
            clientService.getByUser(UserConnect.getUserConnect()).getDettes().add(dette);
        }

        
        char reponse;
        do {
            Article article;
            do {
                article = articleService.getByLibelle(super.saisieString("Entrer le libelle de l'article"));
            } while (article == null);
            
            Detail detail = new Detail();
            do {
                detail.setQuantite(super.saisieDouble("Saisir la quantite de l'article"));
            } while (detail.getQuantite() > article.getQuantite());
            dette.setMontant(dette.getMontant() + (article.getPrix() * detail.getQuantite()));
            if (UserConnect.getUserConnect().getRole().equals(Role.BOUTIQUIER)) {
                articleService.update(article, article.getQuantite() - detail.getQuantite());
                dette.setEtat(Etat.VALIDER);
            }
            detail.setArticle(article);
            dette.getDetails().add(detail);
            detail.setDette(dette);
            System.out.println("Voulez-vous ajouter un autre article O/N?");
            reponse = scanner.next().charAt(0);
        } while (reponse == 'O' || reponse == 'o');
        return dette;
    }
    @Override
    public Etat saisieEtat() {
        int etatChoice;
        do {
            for (Etat etat : Etat.values()) {
                System.out.println((etat.ordinal()+1) + "-" + etat.name());
            }
            System.out.println("Entrer votre choix:");
            etatChoice = scanner.nextInt();

        } while (etatChoice <= 0 || etatChoice > Etat.values().length);
        return Etat.values()[etatChoice - 1];
    }
        
    
}
