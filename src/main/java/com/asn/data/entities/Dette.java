package com.asn.data.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.asn.data.enums.Etat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = {"details", "client", "payements"}, callSuper = true)
@EqualsAndHashCode(callSuper = false)

@Entity
@Table(name = "dettes")
public class Dette extends AbstractEntity {
    @Column(nullable = false)
    private LocalDateTime date;
    @Column(nullable = false)
    private double montant;
    @Column(nullable = false, name = "montantVerser")
    private double montantVerser;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = false)
    private Client client;
    @Transient
    private static int nbreDette;
    @Enumerated(EnumType.ORDINAL)
    private Etat etat;
    @OneToMany(mappedBy = "dette")
    private List<Detail> details = new ArrayList<>();
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "dette")
    private List<Payement> payements = new ArrayList<>();

    public Dette() {
        date = LocalDateTime.now();
        createAt = LocalDateTime.now();
        updateAt = LocalDateTime.now();
        etat = Etat.ENCOURS;
    }
    
}
