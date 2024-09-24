package com.asn.data.repositories;

import java.util.List;

import com.asn.core.repository.Repository;
import com.asn.data.entities.Article;

public interface ArticleRepository extends Repository<Article> {
    List<Article> findAllByDisponiblity();
    boolean update(Article article, double quantite);
    

}
