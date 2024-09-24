package com.asn.data.services;

import java.util.List;

import com.asn.data.entities.Article;

public interface ArticleService extends Service<Article> {
    List<Article> getByDisponiblity();
    boolean update(Article article, double quantite);

}
