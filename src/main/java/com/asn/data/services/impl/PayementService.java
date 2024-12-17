package com.asn.data.services.impl;

import java.util.List;

import com.asn.data.entities.Dette;
import com.asn.data.entities.Payement;
import com.asn.data.services.Service;

public interface PayementService extends Service<Payement> {
    List<Payement> showPayementsInDette(Dette dette);
}
