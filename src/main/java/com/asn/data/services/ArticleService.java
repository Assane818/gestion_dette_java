package com.asn.data.services;

import java.util.List;

import com.asn.data.entities.Article;
import com.asn.data.entities.Detail;

public interface ArticleService extends Service<Article> {
    List<Article> getByDisponiblity();
    void update(Article article, double quantite);
    String generateReference(int nbr,String format);
    Article getByLibelle(String libelle);
    Article getArticleInDetail(Detail detail);

}
