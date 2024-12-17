package com.asn.data.services.impl;

import java.util.List;

import com.asn.data.entities.Dette;
import com.asn.data.entities.Payement;
import com.asn.data.repositories.PayementRepository;
import com.asn.data.services.PayementService;

public class PayementServiceImpl implements PayementService {
    protected PayementRepository payementRepository;

    public PayementServiceImpl(PayementRepository payementRepository) {
        this.payementRepository = payementRepository;
    }

    @Override
    public int save(Payement object) {
       return payementRepository.insert(object);
    }

    @Override
    public List<Payement> show() {
        return payementRepository.selectAll(Payement.class);
    }

    @Override
    public Payement getById(int id) {
        return payementRepository.selectById(id);
    }

    @Override
    public List<Payement> showPayementsInDette(Dette dette) {
        return payementRepository.selectPayementsInDette(dette);
    }
     
}
