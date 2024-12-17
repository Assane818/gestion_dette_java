package com.asn.data.repositories.jpa;


import java.util.List;

import com.asn.core.repository.impl.RepositoryJpaImpl;
import com.asn.data.entities.User;
import com.asn.data.enums.Role;
import com.asn.data.repositories.UserRepository;

public class UserRepositoryImplJpa extends RepositoryJpaImpl<User>  implements UserRepository{

    public UserRepositoryImplJpa() {
        super(User.class);
    }

    @Override
    public User selectByLogin(String login) {
        try {
            String sql = String.format("SELECT u FROM %s u WHERE u.login = :login",entityName);
            return super.em.createQuery(sql, clazz)
                        .setParameter("login", login)
                        .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean updateEtat(User object, boolean etat) {
        super.em.getTransaction().begin();
        String sql = String.format("UPDATE %s SET etat = :etat WHERE id = :id", entityName);
        super.em.createQuery(sql)
                .setParameter("etat", etat)
                .setParameter("id", object.getId())
                .executeUpdate();
        super.em.getTransaction().commit();
        return true;
    }

    @Override
    public List<User> selectAllUsersByEtat() {
        String sql = String.format("SELECT u FROM %s u WHERE u.etat = true", entityName);
        return super.em.createQuery(sql, User.class).getResultList();

    }

    @Override
    public List<User> selectAllUsersByRole(Role role) {
        String sql = String.format("SELECT u FROM %s u WHERE u.role = :role", entityName);
        return super.em.createQuery(sql, User.class)
                .setParameter("role", role)
                .getResultList();
    }

    @Override
    public User selectUserConnect(String login, String password) {
        try {
            String sql =String.format("SELECT u FROM %s u WHERE u.login = :login AND u.password = :password AND u.etat = true", entityName);
            return this.em.createQuery(sql, User.class)
                        .setParameter("login", login)
                        .setParameter("password", password)
                        .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }



}
