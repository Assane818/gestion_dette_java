package com.asn.data.views.impl;

import java.util.Scanner;

import com.asn.data.entities.Dette;
import com.asn.data.entities.Payement;
import com.asn.data.views.PayementView;

public class PayementViewImpl extends ViewImpl<Payement> implements PayementView {

    public PayementViewImpl(Scanner scanner) {
        super(scanner);
    }

    @Override
    public Payement saisie() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saisie'");
    }
    @Override
    public Payement saisiePayement(Dette dette) {
        Payement payement = new Payement();
        do {
            System.out.println("Entrer le montant a verser");
            payement.setMontantPayer(scanner.nextDouble());
        } while (payement.getMontantPayer() <= 0 || payement.getMontantPayer() > (dette.getMontant() - dette.getMontantVerser()));
        dette.setMontantVerser(dette.getMontantVerser() + payement.getMontantPayer());
        payement.setDette(dette);
        dette.getPayements().add(payement);
        return payement;

    }
    
}
