package com.asn.data.views;

import com.asn.data.entities.Dette;
import com.asn.data.enums.Etat;

public interface DetteView extends View<Dette> {
    Etat saisieEtat(); 
}
