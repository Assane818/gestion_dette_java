package com.asn.data.services.impl;

import java.util.List;

import com.asn.data.entities.Article;
import com.asn.data.entities.Detail;
import com.asn.data.entities.Dette;
import com.asn.data.repositories.ArticleRepository;
import com.asn.data.services.ArticleService;

public class ArticleServiceImpl implements ArticleService {
    private ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }
    public int save(Article article) {
        return this.articleRepository.insert(article);
    }

    public List<Article> show() {
        return articleRepository.selectAll(Article.class);
    }

    public List<Article> getByDisponiblity() {
        return articleRepository.findAllByDisponiblity();
    }

    public Article getById(int id) {
        return articleRepository.selectById(id);
    }

    public void update(Article article, double quantite) {
        articleRepository.update(article, quantite);
    }

    @Override
    public String generateReference(int nbr,String format) {
        int size = String.valueOf(nbr).length();
        return format+"0".repeat((4-size)<0?0:4-size)+nbr;
    }
    @Override
    public Article getByLibelle(String libelle) {
        return articleRepository.selectByLibelle(libelle);
    }
    @Override
    public Article getArticleInDetail(Detail detail) {
        return articleRepository.selectArticleInDetail(detail);
    }

}
