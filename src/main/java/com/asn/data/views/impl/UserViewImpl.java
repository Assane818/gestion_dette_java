package com.asn.data.views.impl;

import java.util.Scanner;

import com.asn.data.entities.User;
import com.asn.data.enums.Role;
import com.asn.data.services.UserService;
import com.asn.data.views.UserView;

public class UserViewImpl extends ViewImpl<User> implements UserView {
    private UserService userService;
    public UserViewImpl(Scanner scanner, UserService userService) {
        super(scanner);
        this.userService = userService;
    }
 
    @Override
    public User saisie() {
        User user = new User();
        System.out.println("Saisir le nom de l'utilisateur");
        user.setNom(scanner.nextLine());
        System.out.println("Saisir le prenom de l'utilisateur");
        user.setPrenom(scanner.nextLine());
        do {
            System.out.println("Saisir le login de l'utilisateur");
            user.setLogin(scanner.nextLine());
        } while (userService.getByLogin(user.getLogin()) != null);
        System.out.println("Saisir le mot de passe de l'utilisateur");
        user.setPassword(scanner.nextLine());
        user.setRole(Role.CLIENT);
        return user;

    }
    @Override
    public Role saisieRole() {
        int RoleChoice;
        do {
            for (int i = 0; i < Role.values().length - 1; i++) {
                Role role = Role.values()[i];
                System.out.println((role.ordinal()+1) + "-" + role.name());
            }
            System.out.println("Vous voulez creer quelle role:");
            RoleChoice = scanner.nextInt();

        } while (RoleChoice <= 0 || RoleChoice > Role.values().length - 1);
        return Role.values()[RoleChoice - 1];
    }
    @Override
    public Role saisieAllRole() {
        int RoleChoice;
        do {
            for (Role role : Role.values()) {
                System.out.println((role.ordinal()+1) + "-" + role.name());
            }
            System.out.println("Vous rechercher quelle role:");
            RoleChoice = scanner.nextInt();

        } while (RoleChoice <= 0 || RoleChoice > Role.values().length);
        return Role.values()[RoleChoice - 1];
    }
    @Override
    public boolean saisieEtat() {
        int etatChoice;
        do {
            System.out.println("1-Actif");
            System.out.println("2-Inactif");
            System.out.println("Entrer votre choix:");
            etatChoice = scanner.nextInt();
        } while (etatChoice <= 0 || etatChoice > 2);
        
        return etatChoice == 1 ? true : false;
    }
    @Override
    public User saisieBoutiquierOrAdmin(Role role) {
        User user = new User();
        scanner.nextLine();
        System.out.println("Saisir le nom de l'utilisateur");
        user.setNom(scanner.nextLine());
        System.out.println("Saisir le prenom de l'utilisateur");
        user.setPrenom(scanner.nextLine());
        do {
            System.out.println("Saisir le login de l'utilisateur");
            user.setLogin(scanner.nextLine());
        } while (userService.getByLogin(user.getLogin()) != null);
        System.out.println("Saisir le mot de passe de l'utilisateur");
        user.setPassword(scanner.nextLine());
        user.setRole(role);
        return user;

    }
    @Override
    public User saisieConnexion() {
        System.out.println("Saisir le login de l'utilisateur");
        String login = scanner.nextLine();
        System.out.println("Saisir le mot de passe de l'utilisateur");
        String password = scanner.nextLine();
        return userService.selectUserConnect(login, password);
    }


    
}
