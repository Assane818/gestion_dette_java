package com.asn.data.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = {"details"}, callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "articles")
public class Article extends AbstractEntity {
    @Column(length = 25, unique = true, nullable = false)
    private String libelle;
    @Column(length = 100, unique = true, nullable = false)
    private double prix;
    @Column(nullable = false)
    private double quantite;
    @Transient  
    private static int nbreArticle;
    @OneToMany(mappedBy = "article")
    private List<Detail> details = new ArrayList<>();


    public Article() {
        createAt = LocalDateTime.now();
        updateAt = LocalDateTime.now();
    }

    public static int getNbreArticle() {
        return ++nbreArticle;
    }

    
}
