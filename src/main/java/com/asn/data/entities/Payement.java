package com.asn.data.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.asn.core.controllers.UserConnect;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
 @Setter
 @ToString(callSuper = true)
 @EqualsAndHashCode(callSuper = false)
 @Entity
 @Table(name = "payements")
public class Payement extends AbstractEntity {
    @Column(nullable = false)
    private LocalDateTime date;
    @Column(nullable = false, name = "montantPayer")
    private double montantPayer;
    @ManyToOne(fetch = FetchType.EAGER)
    private Dette dette;
    private static int nbrePayement;
    
    public Payement() {
        date = LocalDateTime.now();
        createAt = LocalDateTime.now();
        updateAt = LocalDateTime.now();
    }

    public static int getNbrePayement() {
        return ++nbrePayement;
    }
}
