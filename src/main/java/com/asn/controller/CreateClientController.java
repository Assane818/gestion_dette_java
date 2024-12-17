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
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class CreateClientController {
    RepositoryFactory<Client> clientRepositoryFactory = new RepositoryFactoryImpl<>(Client.class);
    Repository<Client> clientRepository = clientRepositoryFactory.getRepository();
    ServiceFactory<Client> clientServiceFactory = new ServiceFactoryImpl<>(Client.class, clientRepository);
    ClientService clientService = (ClientService)clientServiceFactory.getService();
    RepositoryFactory<User> userRepositoryFactory = new RepositoryFactoryImpl<>(User.class);
    Repository<User> userRepository = userRepositoryFactory.getRepository();
    ServiceFactory<User> userServiceFactory = new ServiceFactoryImpl<>(User.class, userRepository);
    UserService userService = (UserService)userServiceFactory.getService();

    @FXML
    private Button listeClientButton;
    @FXML
    private Button listeDetteButton;
    @FXML
    private Button deconnexionButton;
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
    private TextField addressField;
    @FXML
    private TextField surnameField;
    @FXML
    private Button saveButton;
    @FXML
    private Button annulerButton;
    @FXML
    private CheckBox createUserCheckbox;

    @FXML
    public void initialize() {
       nomField.setDisable(true);
       prenomField.setDisable(true);
       loginField.setDisable(true);
       passwordField.setDisable(true);
        loadListeClient();
        listeClientButton.setOnAction(event -> this.loadListeClient());
        createUserCheckbox.setOnAction(event -> this.activeField());
        saveButton.setOnAction(event -> this.saveClient());
        annulerButton.setOnAction(event -> this.loadListeClient());
        listeDetteButton.setOnAction(event -> this.loadListeDette());
        //la deconnexion
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
    public void activeField() {
        if (createUserCheckbox.isSelected()) {
            nomField.setDisable(false);
            prenomField.setDisable(false);
            loginField.setDisable(false);
            passwordField.setDisable(false);
        } else {
            nomField.setDisable(true);
            prenomField.setDisable(true);
            loginField.setDisable(true);
            passwordField.setDisable(true);
        }
    }

    @FXML
    private void saveClient() {
        String surname = surnameField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();
        if (surname.isEmpty() || phone.isEmpty() || address.isEmpty() || clientService.getByPhone(phone) != null || clientService.getBySurname(surname) != null) {
            this.showAlert("Erreur", "Saisie des champs incorrecte");
            return;
        } 
        Client client = new Client();
        client.setSurname(surname);
        client.setPhone(phone);
        client.setAddress(address);
        if (createUserCheckbox.isSelected()) {
            String nom = nomField.getText();
            String prenom = prenomField.getText();
            String login = loginField.getText();
            String password = passwordField.getText();
            if (nom.isEmpty() || prenom.isEmpty() || login.isEmpty() || password.isEmpty() || userService.getByLogin(login) != null) {
                this.showAlert("Erreur", "Saisie des champs incorrecte");
                return;
            }
            User user = new User();
            user.setNom(nom);
            user.setPrenom(prenom);
            user.setLogin(login);
            user.setPassword(password);
            user.setRole(Role.CLIENT);
            int userId = userService.save(user);
            client.setUser(userService.getById(userId));
        }
        clientService.save(client);
        this.loadListeClient();
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
    private void loadListeDette() {
        try {
            App.setRoot("listeDette");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        
}
