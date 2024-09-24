package com.asn.core.factory;

import java.util.Scanner;

import com.asn.data.repositories.ArticleRepository;
import com.asn.data.repositories.list.ArticleRepositoryImpl;
import com.asn.data.services.ArticleServiceImpl;
import com.asn.data.views.ArticleView;

public class ArticleFactory {
    private ArticleRepository articleRepository;
    private ArticleServiceImpl articleService;
    private ArticleView articleView;

    public ArticleRepository getArticleRepository() {
        if (articleRepository == null) {
            articleRepository = new ArticleRepositoryImpl();
        }
        return articleRepository;
    }

    public ArticleServiceImpl getArticleService() {
        if (articleService == null) {
            articleService = new ArticleServiceImpl(this.articleRepository);
        }
        return articleService;
    }
    
    public ArticleView getArticleView(Scanner scanner) {
        if ( articleView == null) {
            articleView = new ArticleView(scanner);
        }
        return articleView;
    }
}
