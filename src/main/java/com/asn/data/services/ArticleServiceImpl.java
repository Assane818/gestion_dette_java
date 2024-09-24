package com.asn.data.services;

import java.util.List;

import com.asn.data.entities.Article;
import com.asn.data.repositories.ArticleRepository;

public class ArticleServiceImpl {
    private ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }
    public boolean save(Article article) {
        this.articleRepository.insert(article);
        return true;
    }

    public List<Article> show() {
        return articleRepository.selectAll();
    }

    public List<Article> getByDisponiblity() {
        return articleRepository.findAllByDisponiblity();
    }

    public Article getById(int id) {
        return articleRepository.selectById(id);
    }

    public boolean update(Article article, double quantite) {
        return articleRepository.update(article, quantite);
    }
}
