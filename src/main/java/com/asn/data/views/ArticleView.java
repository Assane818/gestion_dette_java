package com.asn.data.views;

import java.util.Scanner;

import com.asn.data.entities.Article;

public class ArticleView extends ViewImpl<Article> {

    public ArticleView(Scanner scanner) {
        super(scanner);
    }

    @Override
    public Article saisie() {
        Article article = new Article();
        scanner.nextLine();
        System.out.println("Saisir le libelle de l'article");
        article.setLibelle(scanner.nextLine());
        System.out.println("Saisir le prix de l'article");
        article.setPrix(scanner.nextDouble());
        System.out.println("Saisir la quantite de l'article");
        article.setQuantite(scanner.nextDouble());
        scanner.nextLine();
        return article;
    }
    
}
