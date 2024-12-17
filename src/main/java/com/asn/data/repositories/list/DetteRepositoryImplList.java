package com.asn.data.repositories.list;

import java.util.ArrayList;
import java.util.List;

import com.asn.core.repository.impl.RepositoryListImpl;
import com.asn.data.entities.Client;
import com.asn.data.entities.Dette;
import com.asn.data.enums.Etat;
import com.asn.data.repositories.ClientRepository;
import com.asn.data.repositories.DetteRepository;

public class DetteRepositoryImplList extends RepositoryListImpl<Dette> implements DetteRepository {
    private ClientRepository clientRepository;

    public DetteRepositoryImplList(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Dette> selectDettesNoSoldeClient(Client client) {
        List<Dette> dettes = new ArrayList<>();
        for (Dette dette : client.getDettes()) {
            if (dette.getMontant() > dette.getMontantVerser()) {
                dettes.add(dette);
            }
        }
        return null;
    }

    @Override
    public List<Dette> selectDettesByEtat(Etat etat) {
        List<Dette> dettes = new ArrayList<>();
        for (Dette dette : super.list) {
            if (dette.getEtat() == etat) {
                dettes.add(dette);
            }
        }
        return dettes;
    }

    @Override
    public void updateEtatDette(Dette dette, Etat etat) {
        dette.setEtat(etat);
    }

    @Override
    public void updateDette(Dette dette) {
        for (Dette det : super.list) {
            if (det.getId() == dette.getId()) {
                det.setEtat(dette.getEtat());
                det.setMontantVerser(dette.getMontantVerser());
            }
        }
    }
    
}
