package com.asn.controller;

import com.asn.App;
import com.asn.core.factory.RepositoryFactory;
import com.asn.core.factory.ServiceFactory;
import com.asn.core.factory.Impl.RepositoryFactoryImpl;
import com.asn.core.factory.Impl.ServiceFactoryImpl;
import com.asn.core.repository.Repository;
import com.asn.data.entities.Client;
import com.asn.data.entities.Dette;
import com.asn.data.enums.Etat;
import com.asn.data.services.ClientService;
import com.asn.data.services.DetteService;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ListeDetteController {
    RepositoryFactory<Client> clientRepositoryFactory = new RepositoryFactoryImpl<>(Client.class);
    Repository<Client> clientRepository = clientRepositoryFactory.getRepository();
    ServiceFactory<Client> clientServiceFactory = new ServiceFactoryImpl<>(Client.class, clientRepository);
    ClientService clientService = (ClientService)clientServiceFactory.getService();
    RepositoryFactory<Dette> detteRepositoryFactory = new RepositoryFactoryImpl<>(Dette.class);
    Repository<Dette> detteRepository = detteRepositoryFactory.getRepository();
    ServiceFactory<Dette> detteServiceFactory = new ServiceFactoryImpl<>(Dette.class, detteRepository);
    DetteService detteService = (DetteService)detteServiceFactory.getService();

    @FXML
    private TableView<Dette> detteTable;
    @FXML
    private TableColumn<Dette, String> idColumn;
    @FXML
    private TableColumn<Dette, String> dateColumn;
    @FXML
    private TableColumn<Dette, String> montantColumn;
    @FXML
    private TableColumn<Dette, String> montantVerserColumn;
    @FXML
    private TableColumn<Dette, String> montantRestantColumn;
    @FXML
    private Button listeClientButton;
    @FXML
    private Button listeDetteButton;
    @FXML
    private Button gestionDetteButton;
    @FXML
    private Button deconnexionButton;
    @FXML
    private TextField searchPhoneField;
    @FXML
    private Button searchPhoneButton;
    @FXML
    private ComboBox<String> filtreDetteSelect;
    @FXML
    private Button voirArticleDetteButton;
    @FXML
    private Button voirPayementDetteButton;


    @FXML
    public void initialize() {
        filtreDetteSelect.getItems().addAll("Tous", "VALIDER", "ENCOURS", "REFUSER", "ARCHIVER");
        filtreDetteSelect.setValue("Tout");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        montantColumn.setCellValueFactory(new PropertyValueFactory<>("montant"));
        montantVerserColumn.setCellValueFactory(new PropertyValueFactory<>("montantVerser"));
        montantRestantColumn.setCellValueFactory(cellData -> {
            Dette dette = cellData.getValue();
            double montantRestant = dette.getMontant() - dette.getMontantVerser();
            return new SimpleStringProperty(Double.toString(montantRestant));
        });


        loadListeDette();
        listeClientButton.setOnAction(event -> loadListeClient());
        listeDetteButton.setOnAction(event -> loadListeDette());
        searchPhoneButton.setOnAction(event -> loadListdetteNoSoldeClient());
        filtreDetteSelect.setOnAction(event -> loadListeDetteEtat());
        gestionDetteButton.setOnAction(event -> loadGestionDette());
        voirArticleDetteButton.setOnAction(event -> loadarticleDette());
        voirPayementDetteButton.setOnAction(event -> loadPayementDette());
        //la deconnexion
        deconnexionButton.setOnAction(event -> ConnexionController.deconnexion());
    }

    @FXML
    public void loadListeDette() {
        // Charger les données dans le TableView
        ObservableList<Dette> listDette = FXCollections.observableArrayList(detteService.show());
        detteTable.setItems(listDette);
    }

    @FXML
    public void loadListdetteNoSoldeClient() {
        String phone = searchPhoneField.getText();
        Client client = clientService.getByPhone(phone);
        ObservableList<Dette> listDette = FXCollections.observableArrayList(detteService.showDettesNoSoldeClient(client));
        detteTable.setItems(listDette);
    }

    @FXML
    public void loadListeDetteEtat() {
        String etat = filtreDetteSelect.getValue();
        if ("Tous".equals(etat)) {
            this.loadListeDette();
            return;
        }
        ObservableList<Dette> filtreList = FXCollections.observableArrayList(detteService.getDettesByEtat(Etat.getValue(etat)));
        detteTable.setItems(filtreList);

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
    public void loadarticleDette() {
        try {
            App.setRoot("formArticleDette");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void loadPayementDette() {
        try {
            App.setRoot("formPayementDette");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void loadGestionDette() {
        try {
            App.setRoot("menuGestionDette");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
