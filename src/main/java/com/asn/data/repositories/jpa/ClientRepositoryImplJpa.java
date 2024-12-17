package com.asn.data.repositories.jpa;

import java.util.List;

import com.asn.core.repository.impl.RepositoryJpaImpl;
import com.asn.data.entities.Client;
import com.asn.data.entities.User;
import com.asn.data.repositories.ClientRepository;
import com.asn.data.repositories.UserRepository;

public class ClientRepositoryImplJpa extends RepositoryJpaImpl<Client>  implements ClientRepository{
    
    

    public ClientRepositoryImplJpa(UserRepository userRepository) {
        super(Client.class);
    }

    

    @Override
    public Client selectByPhone(String phone) {
        try {
            String sql = String.format("Select u FROM %s u WHERE u.phone = :phone", entityName);
            return this.em.createQuery(sql, clazz)
                        .setParameter("phone", phone)
                        .getSingleResult();
        } catch (Exception e) {
           return null;
        }
    }

    @Override
    public Client selectBySurname(String surname) {
        try {
            String sql = String.format("Select u FROM %s u WHERE u.surname = :surname", entityName);
            return this.em.createQuery(sql, clazz)
                        .setParameter("surname", surname)
                        .getSingleResult();
        } catch (Exception e) {
           return null;
        }
    }

    @Override
    public void updateUserId(Client object, int id) {
        super.em.getTransaction().begin();
        String sql = String.format("UPDATE %s SET user_id = :userId WHERE id = :id", entityName);
        super.em.createQuery(sql)
                .setParameter("userId", id)
                .setParameter("id", object.getId())
                .executeUpdate();
        super.em.getTransaction().commit();
    }

    @Override
    public List<Client> selectClientAccount() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectClientAccount'");
    }

    @Override
    public List<Client> selectClientNoAccount() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectClientNoAccount'");
    }

    @Override
    public Client selectByUser(User user) {
        try {
            String sql = String.format("SELECT u FROM %s u where u.user = :user" , entityName);
            return this.em.createQuery(sql, clazz)
                .setParameter("user", user)
                .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    

}
