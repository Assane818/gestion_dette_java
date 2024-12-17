package com.asn.data.repositories.list;

import java.util.List;

import com.asn.core.repository.impl.RepositoryListImpl;
import com.asn.data.entities.Dette;
import com.asn.data.entities.Payement;
import com.asn.data.repositories.PayementRepository;

public class PayementRepositoryImplList extends RepositoryListImpl<Payement> implements PayementRepository {

    @Override
    public List<Payement> selectPayementsInDette(Dette dette) {
        return dette.getPayements();
    }
    
}
