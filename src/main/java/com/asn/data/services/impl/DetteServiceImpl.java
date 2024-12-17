package com.asn.data.services.impl;

import java.util.ArrayList;
import java.util.List;

import com.asn.data.entities.Client;
import com.asn.data.entities.Dette;
import com.asn.data.enums.Etat;
import com.asn.data.repositories.DetteRepository;
import com.asn.data.services.DetteService;

public class DetteServiceImpl implements DetteService {

    private DetteRepository detteRepository;

    public DetteServiceImpl(DetteRepository detteRepository) {
        this.detteRepository = detteRepository;
    }

    @Override
    public int save(Dette object) {
        return detteRepository.insert(object);
    }

    @Override
    public List<Dette> show() {
        return detteRepository.selectAll(Dette.class);
    }

    @Override
    public Dette getById(int id) {
        return detteRepository.selectById(id);
    }

    @Override
    public List<Dette> showDettesNoSoldeClient(Client client) {
        return detteRepository.selectDettesNoSoldeClient(client);
    }

    @Override
    public List<Dette> getDettesByEtat(Etat etat) {
        return detteRepository.selectDettesByEtat(etat);
    }

    @Override
    public void updateEtatDette(Dette dette, Etat etat) {
        detteRepository.updateEtatDette(dette, etat);
    }

    @Override
    public void updateDette(Dette dette) {
        detteRepository.updateDette(dette);
    }

    @Override
    public List<Dette> getDettesByClient(List<Dette> dettes, Client client) {
        List<Dette> dettesClient = new ArrayList<>();
        for (Dette dette : dettes) {
            if (dette.getClient().getId() == client.getId()) {
                dettesClient.add(dette);
            }
        }
        return dettesClient;
    }

    @Override
    public void archiverDettesSolde(List<Dette> dettes) {
        for (Dette dette : dettes) {
            if (dette.getMontant() - dette.getMontantVerser() == 0) {
                this.updateEtatDette(dette, Etat.ARCHIVER);
            }
        }
    }

    
}
