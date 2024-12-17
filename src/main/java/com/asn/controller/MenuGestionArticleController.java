package com.asn.controller;

import com.asn.App;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MenuGestionArticleController {
    @FXML
    private Button listeUserButton;
    @FXML
    private Button listeArticleButton;
    @FXML
    private Button deconnexionButton;
    @FXML
    private Button formUpdateArticleButton;
    @FXML
    private Button formCreateArticleButton;

    @FXML
    public void initialize() {
        // Ajouter un événement au bouton de connexion
        listeUserButton.setOnAction(event -> this.loadAdminView());
        //affiche menu article
        listeArticleButton.setOnAction(event -> this.loadListeArticle());
        formUpdateArticleButton.setOnAction(event -> this.loadFormUpdateArticle());
        formCreateArticleButton.setOnAction(event -> this.loadFormCreateArticle());
        deconnexionButton.setOnAction(event -> ConnexionController.deconnexion());
    }

    @FXML
    public void loadListeArticle() {
        try {
            App.setRoot("listeArticle");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void loadFormCreateArticle() {
        try {
            App.setRoot("formCreateArticle");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void loadFormUpdateArticle() {
        try {
            App.setRoot("formUpdateArticle");
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
    
