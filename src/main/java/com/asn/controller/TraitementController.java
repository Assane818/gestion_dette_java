package com.asn.controller;

import com.asn.App;
import com.asn.core.factory.RepositoryFactory;
import com.asn.core.factory.ServiceFactory;
import com.asn.core.factory.Impl.RepositoryFactoryImpl;
import com.asn.core.factory.Impl.ServiceFactoryImpl;
import com.asn.core.repository.Repository;
import com.asn.data.entities.Dette;
import com.asn.data.enums.Etat;
import com.asn.data.services.DetteService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class TraitementController {
    RepositoryFactory<Dette> detteRepositoryFactory = new RepositoryFactoryImpl<>(Dette.class);
    Repository<Dette> detteRepository = detteRepositoryFactory.getRepository();
    ServiceFactory<Dette> detteServiceFactory = new ServiceFactoryImpl<>(Dette.class, detteRepository);
    DetteService detteService = (DetteService)detteServiceFactory.getService();

    @FXML
    private Button listeDetteButton;
    @FXML
    private Button listeClientButton;
    @FXML
    private Button deconnexionButton;
    @FXML
    private Button annulerButton;
    @FXML
    private Button saveButton;
    @FXML
    private ComboBox<String> etatDetteSelect;
    @FXML
    private TextField idDetteField;

    @FXML
    public void initialize() {
        etatDetteSelect.getItems().addAll("ENCOURS", "VALIDER", "REFUSER");
        etatDetteSelect.setValue("ENCOURS");
        listeClientButton.setOnAction(event -> this.loadListeClient());
        listeDetteButton.setOnAction(event -> this.loadListeDette());
        annulerButton.setOnAction(event -> this.loadListeDette());
        saveButton.setOnAction(event -> this.taitementDette());
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
    public void taitementDette() {
        String id = idDetteField.getText();
        if (id.isEmpty() || !id.matches("\\d+") ) {
            showAlert("Erreur", "L'id de la dette saisie n'est pas valide");
            return;
        } 
        Dette dette = detteService.getById(Integer.parseInt(id));
        if (dette == null || !dette.getEtat().equals(Etat.ENCOURS)) {
            showAlert("Erreur", "La dette n'existe pas ou a deja ete traitée");
            return;
        }
        String etat = etatDetteSelect.getValue();
        detteService.updateEtatDette(dette, Etat.getValue(etat));
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
