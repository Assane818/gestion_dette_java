package com.asn.data.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.asn.core.controllers.UserConnect;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString(exclude = "dettes", callSuper = true)
@Entity
@Table(name = "clients")
@EqualsAndHashCode(callSuper = false)
public class Client extends AbstractEntity {
    @Column(length = 25, unique = true, nullable = false)
    private String surname;
    @Column(length = 12, unique = true, nullable = false)
    private String phone;
    @Column(length = 50, nullable = false)
    private String address;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = true)
    private User user;
    @Transient
    private static int nbreClient;
    @OneToMany(mappedBy = "client")
    private List<Dette> dettes = new ArrayList<>();



    public Client() {
        createAt = LocalDateTime.now();
        updateAt = LocalDateTime.now();
    }
    
    public static int getNbreClient() {
        return ++nbreClient;
    }
    

}
