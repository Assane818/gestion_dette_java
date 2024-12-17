package com.asn.controller;

import com.asn.App;
import com.asn.core.factory.RepositoryFactory;
import com.asn.core.factory.ServiceFactory;
import com.asn.core.factory.Impl.RepositoryFactoryImpl;
import com.asn.core.factory.Impl.ServiceFactoryImpl;
import com.asn.core.repository.Repository;
import com.asn.data.entities.Dette;
import com.asn.data.entities.Payement;
import com.asn.data.enums.Etat;
import com.asn.data.services.DetteService;
import com.asn.data.services.impl.PayementService;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class PayementController {
    RepositoryFactory<Dette> detteRepositoryFactory = new RepositoryFactoryImpl<>(Dette.class);
    Repository<Dette> detteRepository = detteRepositoryFactory.getRepository();
    ServiceFactory<Dette> detteServiceFactory = new ServiceFactoryImpl<>(Dette.class, detteRepository);
    DetteService detteService = (DetteService)detteServiceFactory.getService();
    RepositoryFactory<Payement> payementRepositoryFactory = new RepositoryFactoryImpl<>(Payement.class);
    Repository<Payement> payementRepository = payementRepositoryFactory.getRepository();
    ServiceFactory<Payement> payementServiceFactory = new ServiceFactoryImpl<>(Payement.class, payementRepository);
    PayementService payementService = (PayementService)payementServiceFactory.getService();

    @FXML
    private Button listeDetteButton;
    @FXML
    private Button listeClientButton;
    @FXML
    private Button deconnexionButton;
    @FXML
    private Button annulerButton;
    @FXML
    private Button payerButton;
    @FXML
    private TextField montantField;
    @FXML
    private TextField idDetteField;

    @FXML
    public void initialize() {
        listeClientButton.setOnAction(event -> this.loadListeClient());
        listeDetteButton.setOnAction(event -> this.loadListeDette());
        annulerButton.setOnAction(event -> this.loadListeDette());
        payerButton.setOnAction(event -> this.saveArticle());
        deconnexionButton.setOnAction(event -> ConnexionController.deconnexion());
    }

    @FXML
    public void loadListeClient() {
        try {
            App.setRoot("listeClientBoutiquier");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void loadListeDette() {
        try {
            App.setRoot("listeDette");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void saveArticle() {
        String id = idDetteField.getText();
        String montant = montantField.getText();
        if (id.isEmpty() || !id.matches("\\d+") ) {
            showAlert("Erreur", "L'id de la dette saisie n'est pas valide");
            return;
        }
        Dette dette = detteService.getById(Integer.parseInt(id));
        if (montant.isEmpty() || !montant.matches("\\d+") || dette == null || (dette.getMontant() - dette.getMontantVerser()) < Double.parseDouble(montant) || !dette.getEtat().equals(Etat.VALIDER)) {
            showAlert("Erreur", "Saisie des champs incorrecte ou la dette n'existe pas");
            return;
        }
        Payement payement = new Payement();
        payement.setMontantPayer(Double.parseDouble(montant));
        dette.setMontantVerser(dette.getMontantVerser() + Double.parseDouble(montant));
        payement.setDette(dette);
        payementService.save(payement);
        detteService.updateDette(dette);
        detteService.archiverDettesSolde(detteService.show());
        this.loadListeDette();
    }

    private void showAlert(String title, String message) {
        // Créer une nouvelle alerte de type INFORMATION
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        // Supprimer le texte d'en-tête de l'alerte
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
