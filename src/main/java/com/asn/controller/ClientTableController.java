package com.asn.controller;

import com.asn.App;
import com.asn.core.factory.RepositoryFactory;
import com.asn.core.factory.ServiceFactory;
import com.asn.core.factory.Impl.RepositoryFactoryImpl;
import com.asn.core.factory.Impl.ServiceFactoryImpl;
import com.asn.core.repository.Repository;
import com.asn.data.entities.Client;
import com.asn.data.services.ClientService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ClientTableController {
    RepositoryFactory<Client> clientRepositoryFactory = new RepositoryFactoryImpl<>(Client.class);
    Repository<Client> clientRepository = clientRepositoryFactory.getRepository();
    ServiceFactory<Client> clientServiceFactory = new ServiceFactoryImpl<>(Client.class, clientRepository);
    ClientService clientService = (ClientService)clientServiceFactory.getService();

    @FXML
    private TableView<Client> clientTable;
    @FXML
    private TableColumn<Client, String> surnameColumn;
    @FXML
    private TableColumn<Client, String> phoneColumn;
    @FXML
    private TableColumn<Client, String> addressColumn;
    @FXML
    private TextField searchPhoneField;
    @FXML
    private Button searchPhoneButton;
    @FXML
    private Button listeClientButton;
    @FXML
    private Button listeDetteButton;
    @FXML
    private Button creerClientButton;
    @FXML
    private Button deconnexionButton;

    @FXML
    public void initialize() {
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        loadListeClient();
        listeClientButton.setOnAction(event -> loadListeClient());
        searchPhoneButton.setOnAction(event -> searchClientByPhone());
        creerClientButton.setOnAction(event -> loadFormCreateClient());
        listeDetteButton.setOnAction(event -> loadListeDette());
        //la deconnexion
        deconnexionButton.setOnAction(event -> ConnexionController.deconnexion());
    }

    @FXML
    public void loadListeClient() {
        // Charger les données dans le TableView
        ObservableList<Client> listUser = FXCollections.observableArrayList(clientService.show());
        clientTable.setItems(listUser);
    }

    @FXML
    public void searchClientByPhone() {
        String phone = searchPhoneField.getText();
        Client client = clientService.getByPhone(phone);
        ObservableList<Client> listUser = FXCollections.observableArrayList(client);
        clientTable.setItems(listUser);
    }

    @FXML
    private void loadFormCreateClient() {
        try {
            App.setRoot("formCreateClient");
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

}
