package com.asn.data.repositories;

import java.util.List;

import com.asn.core.repository.Repository;
import com.asn.data.entities.Client;
import com.asn.data.entities.Dette;
import com.asn.data.enums.Etat;

public interface DetteRepository extends Repository<Dette> {
    List<Dette> selectDettesNoSoldeClient(Client client);
    List<Dette> selectDettesByEtat(Etat etat);
    void updateEtatDette(Dette dette, Etat etat);
    void updateDette(Dette dette);
    
}
