package com.asn.data.services;

import java.util.List;

import com.asn.data.entities.Client;
import com.asn.data.entities.Dette;
import com.asn.data.enums.Etat;

public interface DetteService extends Service<Dette> {
    List<Dette> showDettesNoSoldeClient(Client client);
    List<Dette> getDettesByEtat(Etat etat);
    void updateEtatDette(Dette dette, Etat etat);
    void updateDette(Dette dette);
    List<Dette> getDettesByClient(List<Dette> dettes, Client client);
    void archiverDettesSolde(List<Dette> dettes);
}
