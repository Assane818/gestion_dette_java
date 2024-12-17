package com.asn.data.views.impl;

import java.util.Scanner;

import com.asn.data.entities.Article;
import com.asn.data.services.ArticleService;
import com.asn.data.views.ArticleView;

public class ArticleViewImpl extends ViewImpl<Article> implements ArticleView {

    private ArticleService articleService;

    public ArticleViewImpl(Scanner scanner, ArticleService articleService) {
        super(scanner);
        this.articleService = articleService;
    }

    @Override
    public Article saisie() {
        Article article = new Article();
        do {
            System.out.println("Saisir le libelle de l'article");
            article.setLibelle(scanner.nextLine());
        } while (articleService.getByLibelle(article.getLibelle()) != null);
        System.out.println("Saisir le prix de l'article");
        article.setPrix(scanner.nextDouble());
        System.out.println("Saisir la quantite de l'article");
        article.setQuantite(scanner.nextDouble());
        return article;
    }
    
}
