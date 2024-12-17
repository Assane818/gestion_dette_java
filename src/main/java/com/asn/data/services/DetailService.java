package com.asn.data.services;

import java.util.List;

import com.asn.data.entities.Detail;
import com.asn.data.entities.Dette;

public interface DetailService extends Service<Detail> {
    void updateDetteId(Detail object, int id);
    List<Detail> showDetailsInDette(Dette dette);
}
