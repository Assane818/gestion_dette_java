package com.asn.data.entities;

import com.asn.data.enums.Role;

import lombok.Data;

@Data
public class User {
    private int id;
    private String nom;
    private String prenom;
    private String login;
    private String password;
    private boolean etat;
    private Role role;
    private static int nbreUser;

    public User() {
        id = ++nbreUser;
        this.etat = true;
    }
    public static int getNbreUser() {
        return ++nbreUser;
    }

    public boolean getEtat() {
        return etat;
    }
    
}
