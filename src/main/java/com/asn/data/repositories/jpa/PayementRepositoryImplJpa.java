package com.asn.data.repositories.jpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.asn.core.repository.impl.RepositoryBdImpl;
import com.asn.core.repository.impl.RepositoryJpaImpl;
import com.asn.data.entities.Client;
import com.asn.data.entities.Dette;
import com.asn.data.entities.Payement;
import com.asn.data.repositories.DetteRepository;
import com.asn.data.repositories.PayementRepository;
import com.asn.data.repositories.UserRepository;

public class PayementRepositoryImplJpa extends RepositoryJpaImpl<Payement> implements PayementRepository{

    // public PayementRepositoryImplJpa(DetteRepository detteRepository) {
    //     super.className = Payement.class;
    // }
    public PayementRepositoryImplJpa(DetteRepository detteRepository) {
        super(Payement.class);
    }


    

    @Override
    public List<Payement> selectPayementsInDette(Dette dette) {
        String sql = String.format("SELECT u FROM %s u WHERE u.dette = :dette", entityName);
        return super.em.createQuery(sql, clazz)
                .setParameter("dette", dette)
                .getResultList();
    }

}
