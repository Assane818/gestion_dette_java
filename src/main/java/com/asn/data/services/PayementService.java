package com.asn.data.services;

import java.util.List;

import com.asn.data.entities.Dette;
import com.asn.data.entities.Payement;

public interface PayementService extends Service<Payement> {
    List<Payement> showPayementsInDette(Dette dette);
}
