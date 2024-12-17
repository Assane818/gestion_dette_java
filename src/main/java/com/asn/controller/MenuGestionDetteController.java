package com.asn.controller;

import com.asn.App;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MenuGestionDetteController {
    @FXML
    private Button listeDetteButton;
    @FXML
    private Button formCreateDetteButton;
    @FXML
    private Button formPayementButton;
    @FXML
    private Button formTraitementButton;
    @FXML
    private Button listeClientButton;
    @FXML
    private Button deconnexionButton;

    @FXML
    public void initialize() {
        listeClientButton.setOnAction(event -> loadListeClient());
        listeDetteButton.setOnAction(event -> loadListeDette());
        formPayementButton.setOnAction(event -> loadFormPayement());
        formCreateDetteButton.setOnAction(event -> formCreateDette());
        formTraitementButton.setOnAction(event -> loadFormTraitement());
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
    private void loadFormPayement() {
        try {
            App.setRoot("formPayement");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void loadFormTraitement() {
        try {
            App.setRoot("formTraitement");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void formCreateDette() {
        try {
            App.setRoot("formCreateDette");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
