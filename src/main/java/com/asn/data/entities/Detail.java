package com.asn.data.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "details")
public class Detail extends AbstractEntity {
    @Column(nullable = false)
    private double quantite;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private Article article;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private Dette dette;
    @Transient
    private static int nbreDetail;
    
    public Detail() {
        createAt = LocalDateTime.now();
        updateAt = LocalDateTime.now();
    }

    public static int getNbreDetail() {
        return ++nbreDetail;
    }
}
