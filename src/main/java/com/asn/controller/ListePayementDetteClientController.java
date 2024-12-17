package com.asn.controller;

import java.util.List;

import com.asn.App;
import com.asn.core.controllers.UserConnect;
import com.asn.core.factory.RepositoryFactory;
import com.asn.core.factory.ServiceFactory;
import com.asn.core.factory.Impl.RepositoryFactoryImpl;
import com.asn.core.factory.Impl.ServiceFactoryImpl;
import com.asn.core.repository.Repository;
import com.asn.data.entities.Client;
import com.asn.data.entities.Dette;
import com.asn.data.entities.Payement;
import com.asn.data.services.ClientService;
import com.asn.data.services.DetteService;
import com.asn.data.services.impl.PayementService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ListePayementDetteClientController {
    RepositoryFactory<Dette> detteRepositoryFactory = new RepositoryFactoryImpl<>(Dette.class);
    Repository<Dette> detteRepository = detteRepositoryFactory.getRepository();
    ServiceFactory<Dette> detteServiceFactory = new ServiceFactoryImpl<>(Dette.class, detteRepository);
    DetteService detteService = (DetteService)detteServiceFactory.getService();
    RepositoryFactory<Payement> payementRepositoryFactory = new RepositoryFactoryImpl<>(Payement.class);
    Repository<Payement> payementRepository = payementRepositoryFactory.getRepository();
    ServiceFactory<Payement> payementServiceFactory = new ServiceFactoryImpl<>(Payement.class, payementRepository);
    PayementService payementService = (PayementService)payementServiceFactory.getService();
    RepositoryFactory<Client> clientRepositoryFactory = new RepositoryFactoryImpl<>(Client.class);
    Repository<Client> clientRepository = clientRepositoryFactory.getRepository();
    ServiceFactory<Client> clientServiceFactory = new ServiceFactoryImpl<>(Client.class, clientRepository);
    ClientService clientService = (ClientService)clientServiceFactory.getService();

    @FXML
    private TableView<Payement> payementTable;
    @FXML
    private TableColumn<Payement, String> dateColumn;
    @FXML
    private TableColumn<Payement, String> montantColumn;
    @FXML
    private Button listeDetteButton;
    @FXML
    private Button deconnexionButton;
    @FXML
    private Button idDetteButton;
    @FXML
    private TextField idDetteField;

    @FXML
    public void initialize() {
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        montantColumn.setCellValueFactory(new PropertyValueFactory<>("montantPayer"));
        idDetteButton.setOnAction(event -> this.loadListPayemnentDette());
        listeDetteButton.setOnAction(event -> this.loadListeDette());
        deconnexionButton.setOnAction(event -> ConnexionController.deconnexion());
    }


    @FXML
    private void loadListeDette() {
        try {
            App.setRoot("listeDetteClient");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void loadListPayemnentDette() {
        String id = idDetteField.getText();
        if (id.isEmpty() || !id.matches("\\d+")) {
            showAlert("Erreur", "L'id de la dette saisie n'est pas valide");
            return;
        }
        Dette dette = detteService.getById(Integer.parseInt(id));
        if (dette == null || dette.getClient().getId() != clientService.getByUser(UserConnect.getUserConnect()).getId()) {
            showAlert("Erreur", "La dette n'existe pas");
            return; 
        }
        List<Payement> payements = payementService.showPayementsInDette(dette);
        ObservableList<Payement> listPayements = FXCollections.observableArrayList(payements);
        payementTable.setItems(listPayements);
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
