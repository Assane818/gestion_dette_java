package com.asn.data.views;


import com.asn.data.entities.Dette;
import com.asn.data.entities.Payement;

public interface PayementView extends View<Payement> {

    Payement saisiePayement(Dette dette);
    
}
