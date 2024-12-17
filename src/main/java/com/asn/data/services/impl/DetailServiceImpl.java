package com.asn.data.services.impl;

import java.util.List;

import com.asn.data.entities.Detail;
import com.asn.data.entities.Dette;
import com.asn.data.repositories.DetailRepository;
import com.asn.data.services.DetailService;

public class DetailServiceImpl implements DetailService{
    private DetailRepository detailRepository;

    public DetailServiceImpl(DetailRepository detailRepository) {
        this.detailRepository = detailRepository;
    }

    @Override
    public int save(Detail object) {
        return this.detailRepository.insert(object);
    }

    @Override
    public List<Detail> show() {
        return this.detailRepository.selectAll(Detail.class);
    }

    @Override
    public Detail getById(int id) {
        return this.detailRepository.selectById(id);
    }

    @Override
    public void updateDetteId(Detail object, int id) {
        this.detailRepository.updateDetteId(object, id);
    }

    @Override
    public List<Detail> showDetailsInDette(Dette dette) {
        return this.detailRepository.selectDetailsInDette(dette);
    }
    
}
