package com.asn.data.repositories.jpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.asn.core.repository.impl.RepositoryBdImpl;
import com.asn.core.repository.impl.RepositoryJpaImpl;
import com.asn.data.entities.Article;
import com.asn.data.entities.Client;
import com.asn.data.entities.Detail;
import com.asn.data.entities.Dette;
import com.asn.data.repositories.ArticleRepository;
import com.asn.data.repositories.DetailRepository;
import com.asn.data.repositories.DetteRepository;
import com.asn.data.repositories.UserRepository;

public class DetailRepositoryImplJpa extends RepositoryJpaImpl<Detail> implements DetailRepository {

    // public DetailRepositoryImplJpa(ArticleRepository articleRepository, DetteRepository detteRepository) {
    //     super.className = Detail.class;
    // }

    public DetailRepositoryImplJpa(ArticleRepository articleRepository, DetteRepository detteRepository) {
        super(Detail.class);
    }

    

    @Override
    public void updateDetteId(Detail object, int id) {
        super.em.getTransaction().begin();
        String sql = String.format("UPDATE %s SET dette_id = :detteId WHERE id = :id", entityName);
        super.em.createQuery(sql)
                .setParameter("detteId", id)
                .setParameter("id", object.getId())
                .executeUpdate();
        super.em.getTransaction().commit();
    }

    @Override
    public List<Detail> selectDetailsInDette(Dette dette) {
        String sql = String.format("SELECT u FROM %s u WHERE u.dette = :dette", entityName);
        return super.em.createQuery(sql, clazz)
                .setParameter("dette", dette)
                .getResultList();
    }
    
    

}
