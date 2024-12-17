package com.asn.data.repositories.list;

import java.util.List;

import com.asn.core.repository.impl.RepositoryListImpl;
import com.asn.data.entities.Article;
import com.asn.data.entities.Detail;
import com.asn.data.repositories.ArticleRepository;

public class ArticleRepositoryImplList extends RepositoryListImpl<Article> implements ArticleRepository {

    public ArticleRepositoryImplList() {
    }
    @Override
    public List<Article> findAllByDisponiblity() {
        List<Article> listArticleDispo = this.selectAll(Article.class);
        for (Article article : list) {
            if (article.getQuantite() > 0) {
                listArticleDispo.add(article);
            }
        }
        return listArticleDispo;
    }

    @Override
    public void update(Article article, double quantite) {
        article.setQuantite(quantite);
    }

    @Override
    public Article selectByLibelle(String libelle) {
        for (Article article : list) {
            if (article.getLibelle().compareTo(libelle) == 0) {
                return article;
            }
        }
        return null;
    }

    @Override
    public Article selectArticleInDetail(Detail detail) {
        return detail.getArticle();
    }
    
}
