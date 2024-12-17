package com.asn.core.repository.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.Map;

import com.asn.core.repository.Repository;
import com.asn.core.services.YamlService;
import com.asn.core.services.impl.YamlServiceImpl;

public class RepositoryJpaImpl<T> implements Repository<T> {
    protected Class<T> clazz;
    protected String entityName;
    protected EntityManager em;
    EntityManagerFactory emf;
    

    public RepositoryJpaImpl(Class<T> clazz) {
        this.clazz = clazz;
        this.entityName = clazz.getSimpleName();
        YamlService yamlService = new YamlServiceImpl();
        Map<String, Object> mapYaml = yamlService.loadYaml();
        emf =Persistence.createEntityManagerFactory(mapYaml.get("persistence").toString());
        em = emf.createEntityManager();

    }

    @Override
    public int insert(T object) {
        int id = 0;
        try {
            em.getTransaction().begin();
            object = em.merge(object);
            em.persist(object);
            em.getTransaction().commit();
            id = (int) em.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(object);
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
        return id;

    }

    @Override
    public T selectById(int id) {
        try {
            String sql = String.format("SELECT u FROM %s u WHERE u.id = :id", entityName);
            return em.createQuery(sql, clazz)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<T> selectAll(Class<T> clazz) {
        String sql = String.format("SELECT u FROM %s u",entityName);
        return em.createQuery(sql,clazz).getResultList();
    }

    @Override
    public T convertToObject(ResultSet rs, Class<T> clazz) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToObject'");
    }

    @Override
    public void convertToSql(PreparedStatement ps, T entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToSql'");
    }

    @Override
    public String generateRequestInsert(T object) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateRequestInsert'");
    }
    
}
