package com.asn.data.entities;

import lombok.Data;

@Data
public class Article {
    private int id;
    private String libelle;
    private double prix;
    private double quantite;
    private static int nbreArticle;

    public Article() {
        id = ++nbreArticle;
    }

    public static int getNbreArticle() {
        return ++nbreArticle;
    }

    
}
