package com.asn.data.repositories.jpa;

import java.util.List;

import com.asn.core.repository.impl.RepositoryJpaImpl;
import com.asn.data.entities.Article;
import com.asn.data.entities.Detail;
import com.asn.data.repositories.ArticleRepository;

public class ArticleRepositoryImplJpa extends RepositoryJpaImpl<Article> implements ArticleRepository {

    public ArticleRepositoryImplJpa() {
        super(Article.class);
    }

    

    @Override
    public List<Article> findAllByDisponiblity() {
        String sql = String.format("SELECT u FROM %s u WHERE u.quantite > 0", entityName);
        return super.em.createQuery(sql, Article.class).getResultList();
    }

    @Override
    public void update(Article article, double quantite) {
        em.getTransaction().begin();
        String sql = String.format("UPDATE %s SET quantite = :quantite WHERE id = :id", entityName);
        super.em.createQuery(sql)
                .setParameter("quantite", quantite)
                .setParameter("id", article.getId())
                .executeUpdate();
        em.getTransaction().commit();
    }

    @Override
    public Article selectByLibelle(String libelle) {
        try {
            String sql = String.format("Select u FROM %s u WHERE u.libelle = :libelle", entityName);
            return this.em.createQuery(sql, clazz)
                        .setParameter("libelle", libelle)
                        .getSingleResult();
            
        } catch (Exception e) {
           return null;
        }
    }

    @Override
    public Article selectArticleInDetail(Detail detail) {
        try {
            String sql = String.format("SELECT u FROM %s u WHERE u.id = :id", entityName);
            return super.em.createQuery(sql, clazz)
                    .setParameter("id", detail.getArticle().getId())
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
       
    }

    
    
}
