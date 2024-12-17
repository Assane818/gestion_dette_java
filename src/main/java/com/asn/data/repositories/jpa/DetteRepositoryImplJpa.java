package com.asn.data.repositories.jpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.asn.core.repository.impl.RepositoryBdImpl;
import com.asn.core.repository.impl.RepositoryJpaImpl;
import com.asn.data.entities.Client;
import com.asn.data.entities.Dette;
import com.asn.data.enums.Etat;
import com.asn.data.repositories.ClientRepository;
import com.asn.data.repositories.DetteRepository;
import com.asn.data.repositories.UserRepository;

public class DetteRepositoryImplJpa extends RepositoryJpaImpl<Dette> implements DetteRepository {

    public DetteRepositoryImplJpa(ClientRepository clientRepository) {
        super(Dette.class);
    }

    

    @Override
    public List<Dette> selectDettesNoSoldeClient(Client client) {
        String sql = String.format("SELECT u FROM %s u WHERE u.montant > u.montantVerser AND u.client = :client", entityName);
        return super.em.createQuery(sql, clazz)
                    .setParameter("client", client)
                    .getResultList();
    }

    @Override
    public List<Dette> selectDettesByEtat(Etat etat) {
        String sql = String.format("SELECT u FROM %s u WHERE u.etat = :etat", entityName);
        return super.em.createQuery(sql, clazz)
                    .setParameter("etat", etat)
                    .getResultList();
    }

    @Override
    public void updateEtatDette(Dette dette, Etat etat) {
        em.getTransaction().begin();
        String sql = String.format("UPDATE %s SET etat = :etat WHERE id = :id", entityName);
        super.em.createQuery(sql)
                .setParameter("etat", etat)
                .setParameter("id", dette.getId())
                .executeUpdate();
        em.getTransaction().commit();
    }

    @Override
    public void updateDette(Dette dette) {
        em.getTransaction().begin();
        String sql = String.format("UPDATE %s SET montantVerser = :montantVerser WHERE id = :id", entityName);
        super.em.createQuery(sql)
                .setParameter("montantVerser", dette.getMontantVerser())
                .setParameter("id", dette.getId())
                .executeUpdate();
        em.getTransaction().commit();
    }

    
}
