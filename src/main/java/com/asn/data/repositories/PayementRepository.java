package com.asn.data.repositories;

import java.util.List;

import com.asn.core.repository.Repository;
import com.asn.data.entities.Dette;
import com.asn.data.entities.Payement;

public interface PayementRepository extends Repository<Payement> {
    List<Payement> selectPayementsInDette(Dette dette);
}
