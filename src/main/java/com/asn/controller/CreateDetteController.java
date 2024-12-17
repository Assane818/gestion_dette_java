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
import com.asn.data.entities.Client;
import com.asn.data.entities.Detail;
import com.asn.data.entities.Dette;
import com.asn.data.enums.Etat;
import com.asn.data.repositories.DetailRepository;
import com.asn.data.services.ArticleService;
import com.asn.data.services.ClientService;
import com.asn.data.services.DetailService;
import com.asn.data.services.DetteService;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CreateDetteController {
    RepositoryFactory<Article> articleRepositoryFactory = new RepositoryFactoryImpl<>(Article.class);
    Repository<Article> articleRepository = articleRepositoryFactory.getRepository();
    ServiceFactory<Article> articleServiceFactory = new ServiceFactoryImpl<>(Article.class, articleRepository);
    ArticleService articleService = (ArticleService) articleServiceFactory.getService();
    RepositoryFactory<Dette> detteRepositoryFactory = new RepositoryFactoryImpl<>(Dette.class);
    Repository<Dette> detteRepository = detteRepositoryFactory.getRepository();
    ServiceFactory<Dette> detteServiceFactory = new ServiceFactoryImpl<>(Dette.class, detteRepository);
    DetteService detteService = (DetteService)detteServiceFactory.getService();
    RepositoryFactory<Client> clientRepositoryFactory = new RepositoryFactoryImpl<>(Client.class);
    Repository<Client> clientRepository = clientRepositoryFactory.getRepository();
    ServiceFactory<Client> clientServiceFactory = new ServiceFactoryImpl<>(Client.class, clientRepository);
    ClientService clientService = (ClientService)clientServiceFactory.getService();
    RepositoryFactory<Detail> detailRepositoryFactory = new RepositoryFactoryImpl<>(Detail.class);
    Repository<Detail> detailRepository = detailRepositoryFactory.getRepository();
    ServiceFactory<Detail> detailServiceFactory = new ServiceFactoryImpl(Detail.class, detailRepository);
    DetailService detailService = (DetailService)detailServiceFactory.getService();


    @FXML
    private TextField surnameField;
    @FXML
    private TextField libelleField;
    @FXML
    private TextField quantiteField;
    @FXML
    private Button listeDetteButton;
    @FXML
    private Button listeClientButton;
    @FXML
    private Button deconnexionButton;
    @FXML
    private Button annulerButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button addButton;
    private List<Detail> listDetail = new ArrayList<>();

    @FXML
    public void initialize() {
        listeClientButton.setOnAction(event -> this.loadListeClient());
        listeDetteButton.setOnAction(event -> this.loadListeDette());
        addButton.setOnAction(event -> this.createDetailDette());
        saveButton.setOnAction(event -> this.saveDette());
        annulerButton.setOnAction(event -> this.loadListeDette());
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
    private void createDetailDette() {
        String libelle = libelleField.getText();
        String quantite = quantiteField.getText();
        Article article = articleService.getByLibelle(libelle);
        if (libelle.isEmpty() || quantite.isEmpty() || !quantite.matches("\\d+") || article == null || article.getQuantite() < Double.parseDouble(quantite)) {
            showAlert("Erreur", "Saisie des champs incorrecte ou l'article ou le client n'existe pas");
            return;
        }
        if (article.getQuantite() < Double.parseDouble(quantite)) {
            showAlert("Erreur", "Quantite insufficiente");
            return;
        }
        Detail detail = new Detail();
        detail.setArticle(article);
        detail.setQuantite(Double.parseDouble(quantite));
        this.listDetail.add(detail);
        libelleField.clear();
        quantiteField.clear();
    }
    @FXML
    private void saveDette() {
        String surname = surnameField.getText();
        Client client = clientService.getBySurname(surname);
        if (client == null) {
            showAlert("Erreur", "Le client n'existe pas");
            return;
        }
        Dette dette = new Dette();
        dette.setClient(client);
        dette.setEtat(Etat.VALIDER);
        if (listDetail.isEmpty()) {
            showAlert("Erreur", "Aucun article dans la dette");
            return;
        }
        for (Detail detail : listDetail) {
            dette.setMontant(dette.getMontant() + (detail.getQuantite() * detail.getArticle().getPrix()));
        }
        int id = detteService.save(dette);
        for (Detail detail : listDetail) {
            detail.setDette(detteService.getById(id));
            detailService.save(detail);
            Article article = articleService.getById(detail.getArticle().getId());
            articleService.update(article, article.getQuantite() - detail.getQuantite());
        }
        listDetail.clear();
        loadListeDette();
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
