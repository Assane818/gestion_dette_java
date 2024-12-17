package com.asn.controller;

import com.asn.App;
import com.asn.core.controllers.UserConnect;
import com.asn.core.factory.RepositoryFactory;
import com.asn.core.factory.ServiceFactory;
import com.asn.core.factory.Impl.RepositoryFactoryImpl;
import com.asn.core.factory.Impl.ServiceFactoryImpl;
import com.asn.data.entities.User;
import com.asn.data.repositories.UserRepository;
import com.asn.data.services.UserService;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ConnexionController {
    RepositoryFactory<User> userRepositoryFactory = new RepositoryFactoryImpl<>(User.class);
    UserRepository userRepository = (UserRepository)userRepositoryFactory.getRepository();
    ServiceFactory<User> userServiceFactory = new ServiceFactoryImpl<>(User.class, userRepository);
    UserService userService = (UserService)userServiceFactory.getService();
    @FXML
    private TextField login;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button connexionButton;
    @FXML
    public void initialize() {
        // Ajouter un événement au bouton de connexion
        connexionButton.setOnAction(event -> handleConnexion());
    }

    private void handleConnexion() {
        String username = login.getText();
        String password = passwordField.getText();
        User user = userService.selectUserConnect(username, password);
        if (user != null) {
            UserConnect.setUserConnect(user);
            switch (user.getRole()) {
                case ADMIN:
                    loadAdminView();
                    break;
                case CLIENT:
                    loadClientView();
                    break;
                case BOUTIQUIER:
                    loadBoutiquierView();
                    break;
            }
            
        } else {
            showAlert("Erreur de connexion", "Nom d'utilisateur ou mot de passe incorrect.");
        }
    }


    private void showAlert(String title, String message) {
        // Créer une nouvelle alerte de type INFORMATION
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        // Supprimer le texte d'en-tête de l'alerte
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void loadAdminView() {
        try {
            App.setRoot("ListeUserAdmin");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private  void loadBoutiquierView() {
        try {
            App.setRoot("listeClientBoutiquier");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadClientView() {
        try {
            App.setRoot("listeDetteClient");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deconnexion() {
        try {
            App.setRoot("Connexion");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
