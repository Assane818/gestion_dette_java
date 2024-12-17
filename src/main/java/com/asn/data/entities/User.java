package com.asn.data.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.asn.data.enums.Role;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)

@Entity
@Table(name = "users")
public class User extends AbstractEntity {
    @Column(length = 25, nullable = false)
    private String nom;
    @Column(length = 25, nullable = false)
    private String prenom;
    @Column(length = 150, unique = true, nullable = false)
    private String login;
    @Column(length = 15, nullable = false)
    private String password;
    @Column(nullable = false)
    private boolean etat;
    @Enumerated
    private Role role;
    @Transient
    private static int nbreUser;

    public User() {
        this.etat = true;
        createAt = LocalDateTime.now();
        updateAt = LocalDateTime.now();
    }
    public static int getNbreUser() {
        return ++nbreUser;
    }

    
    
}
