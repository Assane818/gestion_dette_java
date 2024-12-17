package com.asn.controller;

import com.asn.App;
import com.asn.core.factory.RepositoryFactory;
import com.asn.core.factory.ServiceFactory;
import com.asn.core.factory.Impl.RepositoryFactoryImpl;
import com.asn.core.factory.Impl.ServiceFactoryImpl;
import com.asn.core.repository.Repository;
import com.asn.data.entities.Article;
import com.asn.data.services.ArticleService;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CreateArticleController {
    RepositoryFactory<Article> articleRepositoryFactory = new RepositoryFactoryImpl<>(Article.class);
    Repository<Article> articleRepository = articleRepositoryFactory.getRepository();
    ServiceFactory<Article> articleServiceFactory = new ServiceFactoryImpl<>(Article.class, articleRepository);
    ArticleService articleService = (ArticleService) articleServiceFactory.getService();
    @FXML
    private Button listeUserButton;
    @FXML
    private TextField libelleField;
    @FXML
    private TextField prixField;
    @FXML
    private TextField quantiteField;
    @FXML
    private Button saveButton;
    @FXML
    private Button annulerButton;
    @FXML
    private Button listeArticleButton;
    @FXML
    private Button deconnexionButton;

    @FXML
    public void initialize() {
        // Ajouter un événement au bouton de connexion
        listeUserButton.setOnAction(event -> this.loadAdminView());
        annulerButton.setOnAction(event -> this.loadListeArticle());
        //affiche menu article
        listeArticleButton.setOnAction(event -> this.loadListeArticle());
        saveButton.setOnAction(event -> this.saveArticle());
        deconnexionButton.setOnAction(event -> ConnexionController.deconnexion());
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
    private void saveArticle() {
        String libelle = libelleField.getText();
        String prix = prixField.getText();
        String quantite = quantiteField.getText();
        Article article = articleService.getByLibelle(libelle);
        if (libelle.isEmpty() || prix.isEmpty() || !prix.matches("\\d+") || quantite.isEmpty() || !quantite.matches("\\d+") || article != null) {
            showAlert("Erreur", "Saisie des champs incorrecte ou l'article existe deja");
            return;
        }
        Article article1 = new Article();
        article1.setLibelle(libelle);
        article1.setPrix(Double.parseDouble(prix));
        article1.setQuantite(Double.parseDouble(quantite));
        articleService.save(article1);
        this.loadListeArticle();
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

    public void loadAdminView() {
        try {
            App.setRoot("ListeUserAdmin");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
