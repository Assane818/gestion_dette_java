package com.asn.controller;

import com.asn.App;
import com.asn.core.controllers.UserConnect;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ListeDetteClientController {
    RepositoryFactory<Dette> detteRepositoryFactory = new RepositoryFactoryImpl<>(Dette.class);
    Repository<Dette> detteRepository = detteRepositoryFactory.getRepository();
    ServiceFactory<Dette> detteServiceFactory = new ServiceFactoryImpl<>(Dette.class, detteRepository);
    DetteService detteService = (DetteService)detteServiceFactory.getService();
    RepositoryFactory<Client> clientRepositoryFactory = new RepositoryFactoryImpl<>(Client.class);
    Repository<Client> clientRepository = clientRepositoryFactory.getRepository();
    ServiceFactory<Client> clientServiceFactory = new ServiceFactoryImpl<>(Client.class, clientRepository);
    ClientService clientService = (ClientService)clientServiceFactory.getService();

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
    private TableColumn<Dette, String> actionsColumn;
    @FXML
    private Button listeDetteButton;
    @FXML
    private Button creerDetteButton;
    @FXML
    private Button deconnexionButton;
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
        actionsColumn.setCellFactory(param -> new TableCell<>() {
            private final Button actionButton = new Button("Relancer");
    
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || (getTableView().getItems().get(getIndex()).getEtat() != Etat.REFUSER)) {
                    setGraphic(null);
                } else {
                    // Définir le texte et le style du bouton
                    actionButton.setStyle("-fx-background-color: #4A76A8; -fx-text-fill: WHITE;-fx-background-radius: 5px;-fx-font-size: 10px;-fx-padding: 2px 5px;");
                    // Associer un événement au bouton
                    actionButton.setOnAction(event -> {
                        Dette dette = getTableView().getItems().get(getIndex());
                        detteService.updateEtatDette(dette, Etat.ENCOURS);
                        loadListeDette();
                    });
    
                    // Ajouter le bouton à la cellule
                    setGraphic(actionButton);
                }
            }
        });
        chargeListeDette();
        listeDetteButton.setOnAction(event -> loadListeDette());
        filtreDetteSelect.setOnAction(event -> loadListeDetteEtat());
        creerDetteButton.setOnAction(event -> loadGestionDette());
        voirArticleDetteButton.setOnAction(event -> loadarticleDette());
        voirPayementDetteButton.setOnAction(event -> loadPayementDette());
        //la deconnexion
        deconnexionButton.setOnAction(event -> ConnexionController.deconnexion());
    }

    @FXML
    public void chargeListeDette() {
        // Charger les données dans le TableView
        ObservableList<Dette> listDette = FXCollections.observableArrayList(detteService.getDettesByClient(detteService.show(), clientService.getByUser(UserConnect.getUserConnect())));
        detteTable.setItems(listDette);
    }

    @FXML
    public void loadListeDetteEtat() {
        String etat = filtreDetteSelect.getValue();
        if ("Tous".equals(etat)) {
            this.chargeListeDette();
            return;
        }
        ObservableList<Dette> filtreList = FXCollections.observableArrayList(detteService.getDettesByEtat(Etat.getValue(etat)));
        detteTable.setItems(filtreList);

    }

    @FXML
    public void loadListeDette() {
        try {
            App.setRoot("listeDetteClient");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void loadGestionDette() {
        try {
            App.setRoot("formCreateDetteClient");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void loadarticleDette() {
        try {
            App.setRoot("formArticleDetteClient");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void loadPayementDette() {
        try {
            App.setRoot("formPayementDetteClient");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
