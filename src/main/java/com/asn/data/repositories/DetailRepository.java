package com.asn.data.repositories;

import java.util.List;

import com.asn.core.repository.Repository;
import com.asn.data.entities.Detail;
import com.asn.data.entities.Dette;

public interface DetailRepository extends Repository<Detail> {
    void updateDetteId(Detail object, int id);
    List<Detail>selectDetailsInDette(Dette dette);
}
