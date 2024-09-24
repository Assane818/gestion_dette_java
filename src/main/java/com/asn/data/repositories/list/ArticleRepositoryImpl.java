package com.asn.data.repositories.list;

import java.util.List;
import java.util.ArrayList;

import com.asn.core.repository.impl.RepositoryImpl;
import com.asn.data.entities.Article;
import com.asn.data.repositories.ArticleRepository;

public class ArticleRepositoryImpl extends RepositoryImpl<Article> implements ArticleRepository {

    

    public List<Article> findAllByDisponiblity() {
        List<Article> articlesDispo = new ArrayList<>();
        for (Article article: list) {
            if (article.getQuantite() > 0) {
                articlesDispo.add(article);
            }
        }
        return articlesDispo;
    }

    public boolean update(Article article, double quantite) {
        article.setQuantite(quantite);
        return true;
    }

    public Article selectById(int id) {
        for (Article article: list) {
            if (article.getId() == id) {
                return article;
            }
        }
        return null;
    }
}
