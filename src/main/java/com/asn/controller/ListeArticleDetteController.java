package com.asn.controller;

import java.util.ArrayList;
import java.util.List;

import com.asn.App;
import com.asn.core.factory.RepositoryFactory;
import com.asn.core.factory.ServiceFactory;
import com.asn.core.factory.Impl.RepositoryFactoryImpl;
import com.asn.core.factory.Impl.ServiceFactoryImpl;
import com.asn.core.repository.Repository;
import com.asn.data.entities.Article;
import com.asn.data.entities.Detail;
import com.asn.data.entities.Dette;
import com.asn.data.services.ArticleService;
import com.asn.data.services.DetailService;
import com.asn.data.services.DetteService;

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

public class ListeArticleDetteController {
    RepositoryFactory<Dette> detteRepositoryFactory = new RepositoryFactoryImpl<>(Dette.class);
    Repository<Dette> detteRepository = detteRepositoryFactory.getRepository();
    ServiceFactory<Dette> detteServiceFactory = new ServiceFactoryImpl<>(Dette.class, detteRepository);
    DetteService detteService = (DetteService)detteServiceFactory.getService();
    RepositoryFactory<Detail> detailRepositoryFactory = new RepositoryFactoryImpl<>(Detail.class);
    Repository<Detail> detailRepository = detailRepositoryFactory.getRepository();
    ServiceFactory<Detail> detailServiceFactory = new ServiceFactoryImpl<>(Detail.class, detailRepository);
    DetailService detailService = (DetailService)detailServiceFactory.getService();
    RepositoryFactory<Article> articleRepositoryFactory = new RepositoryFactoryImpl<>(Article.class);
    Repository<Article> articleRepository = articleRepositoryFactory.getRepository();
    ServiceFactory<Article> articleServiceFactory = new ServiceFactoryImpl<>(Article.class, articleRepository);
    ArticleService articleService = (ArticleService) articleServiceFactory.getService();

    @FXML
    private TableView<Article> articleTable;
    @FXML
    private TableColumn<Article, String> libelleColumn;
    @FXML
    private TableColumn<Article, String> prixColumn;
    @FXML
    private TableColumn<Article, String> quantiteColumn;
    @FXML
    private Button listeDetteButton;
    @FXML
    private Button listeClientButton;
    @FXML
    private Button deconnexionButton;
    @FXML
    private Button idDetteButton;
    @FXML
    private TextField idDetteField;

    @FXML
    public void initialize() {
        libelleColumn.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
        quantiteColumn.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        idDetteButton.setOnAction(event -> this.loadListArticleDette());
        listeClientButton.setOnAction(event -> this.loadListeClient());
        listeDetteButton.setOnAction(event -> this.loadListeDette());
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
    private void loadListeDette() {
        try {
            App.setRoot("listeDette");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void loadListArticleDette() {
        String id = idDetteField.getText();
        if (id.isEmpty() || !id.matches("\\d+")) {
            showAlert("Erreur", "L'id de la dette saisie n'est pas valide");
            return;
        }
        Dette dette = detteService.getById(Integer.parseInt(id));
        if (dette == null) {
            showAlert("Erreur", "La dette n'existe pas");
            return; 
        }
        List<Detail> details = detailService.showDetailsInDette(dette);
        List<Article> articles = new ArrayList<>();
        for (Detail detail : details) {
            articles.add(articleService.getArticleInDetail(detail));
        }
        // Charger les données dans le TableView
        ObservableList<Article> listArticles = FXCollections.observableArrayList(articles);
        articleTable.setItems(listArticles);
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
