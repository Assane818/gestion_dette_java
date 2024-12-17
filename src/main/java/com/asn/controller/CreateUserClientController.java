package com.asn.controller;

import com.asn.App;
import com.asn.core.factory.RepositoryFactory;
import com.asn.core.factory.ServiceFactory;
import com.asn.core.factory.Impl.RepositoryFactoryImpl;
import com.asn.core.factory.Impl.ServiceFactoryImpl;
import com.asn.core.repository.Repository;
import com.asn.data.entities.Client;
import com.asn.data.entities.User;
import com.asn.data.enums.Role;
import com.asn.data.services.ClientService;
import com.asn.data.services.UserService;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CreateUserClientController {
    RepositoryFactory<User> userRepositoryFactory = new RepositoryFactoryImpl<>(User.class);
    Repository<User> userRepository = userRepositoryFactory.getRepository();
    ServiceFactory<User> userServiceFactory = new ServiceFactoryImpl<>(User.class, userRepository);
    UserService userService = (UserService)userServiceFactory.getService();
    RepositoryFactory<Client> clientRepositoryFactory = new RepositoryFactoryImpl<>(Client.class);
    Repository<Client> clientRepository = clientRepositoryFactory.getRepository();
    ServiceFactory<Client> clientServiceFactory = new ServiceFactoryImpl<>(Client.class, clientRepository);
    ClientService clientService = (ClientService)clientServiceFactory.getService(); 

    @FXML
    private Button listeUserButton;

    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private TextField loginField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField phoneField;
    @FXML
    private Button saveButton;
    @FXML
    private Button annulerButton;
    @FXML
    private Button listeArticleButton;
    @FXML
    private Button archiverDetteButton;
    @FXML
    private Button deconnexionButton;
    @FXML
    public void initialize() {
        // Ajouter un événement au bouton de connexion
        listeUserButton.setOnAction(event -> this.loadAdminView());
        saveButton.setOnAction(event -> this.saveUser());
        annulerButton.setOnAction(event -> this.loadAdminView());
        //affiche menu article
        listeArticleButton.setOnAction(event -> this.loadListeArticle());
        deconnexionButton.setOnAction(event -> ConnexionController.deconnexion());
    }

    private void saveUser() {
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String login = loginField.getText();
        String password = passwordField.getText();
        String phone = phoneField.getText();
        Client client = clientService.getByPhone(phone);
        if (nom.isEmpty() || prenom.isEmpty() || login.isEmpty() || password.isEmpty() || phone.isEmpty() || userService.getByLogin(login) != null || client == null || client.getUser() != null) {
            showAlert("Erreur", "Saisie des champs incorrecte ou client n'existe pas");
            return;
        }
        User user = new User();
        user.setNom(nom);
        user.setPrenom(prenom);
        user.setLogin(login);
        user.setPassword(password);
        user.setRole(Role.CLIENT);
        int id = userService.save(user);
        clientService.updateUserId(client, id);
        this.loadAdminView();
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
