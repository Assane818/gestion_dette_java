package com.asn.controller;

import com.asn.App;
import com.asn.core.factory.RepositoryFactory;
import com.asn.core.factory.ServiceFactory;
import com.asn.core.factory.Impl.RepositoryFactoryImpl;
import com.asn.core.factory.Impl.ServiceFactoryImpl;
import com.asn.core.repository.Repository;
import com.asn.data.entities.User;
import com.asn.data.enums.Role;
import com.asn.data.services.UserService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class UserTableController {
    RepositoryFactory<User> userRepositoryFactory = new RepositoryFactoryImpl<>(User.class);
    Repository<User> userRepository = userRepositoryFactory.getRepository();
    ServiceFactory<User> userServiceFactory = new ServiceFactoryImpl<>(User.class, userRepository);
    UserService userService = (UserService)userServiceFactory.getService();
    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, String> nomColumn;
    @FXML
    private TableColumn<User, String> prenomColumn;
    @FXML
    private TableColumn<User, String> loginColumn;
    @FXML
    private TableColumn<User, String> roleColumn;
    @FXML
    private TableColumn<User, String> actionsColumn;
    @FXML
    private Button listeUserButton;
    @FXML
    private ComboBox<String> filtreRoleSelect;
    @FXML
    private Button gestionCompteButton;
    @FXML
    private CheckBox userActifCheckbox;
    @FXML
    private Button listeArticleButton;
    @FXML
    private Button deconnexionButton;

    @FXML
    public void chargeListeUser() {
        // Charger les données dans le TableView
        ObservableList<User> listUser = FXCollections.observableArrayList(userService.show());
        userTable.setItems(listUser);
    }

    @FXML
    public void initialize() {
        filtreRoleSelect.getItems().addAll("Tous", "ADMIN", "BOUTIQUIER", "CLIENT");
        filtreRoleSelect.setValue("Tous");//par defaut
        // Associer les colonnes aux propriétés de la classe User
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        loginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        actionsColumn.setCellFactory(param -> new TableCell<>() {
            private final Button actionButton = new Button("Changer État");
    
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
    
                if (empty) {
                    setGraphic(null);
                } else {
                    // Définir le texte et le style du bouton
                    actionButton.setStyle("-fx-background-color: #4A76A8; -fx-text-fill: WHITE;-fx-background-radius: 5px;-fx-font-size: 10px;-fx-padding: 2px 5px;");
                    // Associer un événement au bouton
                    actionButton.setOnAction(event -> {
                        User user = getTableView().getItems().get(getIndex());
                        userService.updateEtat(user, !user.isEtat());
                        loadListuser();
                    });
    
                    // Ajouter le bouton à la cellule
                    setGraphic(actionButton);
                }
            }
        });
    

        chargeListeUser();
        // Ajouter un événement au bouton de connexion
        filtreRoleSelect.setOnAction(event -> this.loadListeUserRole());
        // Ajouter un événement au bouton de connexion
        listeUserButton.setOnAction(event -> this.loadListuser());
        //affiche les users actif
        userActifCheckbox.setOnAction(event -> this.loadListeUserActicf());
        //affiche menu create user
        gestionCompteButton.setOnAction(event -> this.menuGestionCompte());
        //affiche menu article
        listeArticleButton.setOnAction(event -> this.loadListeArticle());
        //la deconnexion
        deconnexionButton.setOnAction(event -> ConnexionController.deconnexion());
    }

    @FXML
    public void loadListeUserRole() {
        String role = filtreRoleSelect.getValue();
        if ("Tous".equals(role)) {
            this.loadListuser();
            return;
        }
        ObservableList<User> filtreList = FXCollections.observableArrayList(userService.getUsersByRole(Role.getValue(role)));
        userTable.setItems(filtreList);

    }

    @FXML
    public void menuGestionCompte() {
        try {
            App.setRoot("menuGestionCompte");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void loadListeUserActicf() {
        if (!userActifCheckbox.isSelected()) {
            this.loadListuser();
            return;
        }
        ObservableList<User> filtreList = FXCollections.observableArrayList(userService.getUsersByEtat());
        userTable.setItems(filtreList);

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
    public void loadListuser() {
        try {
            App.setRoot("listeUserAdmin");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

