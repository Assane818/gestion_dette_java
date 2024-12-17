package com.asn.controller;

import com.asn.App;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MenuGestionCompteController {
    @FXML
    private Button listeUserButton;
    @FXML
    private Button formAddUserButton;
    @FXML
    private Button formAddUserClientButton;
    @FXML
    private Button listeArticleButton;
    @FXML
    private Button deconnexionButton;

    @FXML
    public void initialize() {
        // Ajouter un événement au bouton de connexion
        listeUserButton.setOnAction(event -> this.loadAdminView());
        formAddUserButton.setOnAction(event -> this.loadFormAddUser());
        formAddUserClientButton.setOnAction(event -> this.loadFormAddUserClient());
        //affiche menu article
        listeArticleButton.setOnAction(event -> this.loadListeArticle());
        deconnexionButton.setOnAction(event -> ConnexionController.deconnexion());
    }

    @FXML
    private void loadFormAddUser() {
        try {
            App.setRoot("formAddUser");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void loadFormAddUserClient() {
        try {
            App.setRoot("formAddUserClient");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void loadListeArticle() {
        try {
            App.setRoot("listeArticle");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadAdminView() {
        try {
            App.setRoot("ListeUserAdmin");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
