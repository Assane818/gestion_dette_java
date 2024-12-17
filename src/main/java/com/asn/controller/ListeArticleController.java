package com.asn.controller;

import com.asn.App;
import com.asn.core.factory.RepositoryFactory;
import com.asn.core.factory.ServiceFactory;
import com.asn.core.factory.Impl.RepositoryFactoryImpl;
import com.asn.core.factory.Impl.ServiceFactoryImpl;
import com.asn.core.repository.Repository;
import com.asn.data.entities.Article;
import com.asn.data.services.ArticleService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ListeArticleController {
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
    private Button listeUserButton;
    @FXML
    private Button listeArticleButton;
    @FXML
    private Button deconnexionButton;
    @FXML
    private CheckBox articleDispoCheckbox;
    @FXML
    private Button gestionArticleButton;
    

    @FXML
    public void initialize() {
        // Associer les colonnes aux propriétés de la classe User
        libelleColumn.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
        quantiteColumn.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        this.loadListArticle();
        listeArticleButton.setOnAction(event -> this.loadListArticle());
        listeUserButton.setOnAction(event -> this.loadAdminView());
        articleDispoCheckbox.setOnAction(event -> this.loadListeArticleDispo());
        gestionArticleButton.setOnAction(event -> this.menuGestionArticle());
        deconnexionButton.setOnAction(event -> ConnexionController.deconnexion());
    }

    @FXML
    public void loadListArticle() {
        // Charger les données dans le TableView
        ObservableList<Article> listArticles = FXCollections.observableArrayList(articleService.show());
        articleTable.setItems(listArticles);
    }

    @FXML
    public void loadListeArticleDispo() {
        if (!articleDispoCheckbox.isSelected()) {
            this.loadListArticle();
            return;
        }
        ObservableList<Article> filtreList = FXCollections.observableArrayList(articleService.getByDisponiblity());
        articleTable.setItems(filtreList);

    }

    @FXML
    public void menuGestionArticle() {
        try {
            App.setRoot("menuGestionArticle");
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
