package com.asn.data.views;

import java.util.Scanner;

import com.asn.data.entities.User;
import com.asn.data.enums.Etat;
import com.asn.data.enums.Role;
import com.asn.data.services.ClientService;

public class UserView extends ViewImpl<User> {
    public UserView(Scanner scanner) {
        super(scanner);
    }
 
    @Override
    public User saisie() {
        User user = new User();
        scanner.nextLine();
        System.out.println("Saisir le nom de l'utilisateur");
        user.setNom(scanner.nextLine());
        System.out.println("Saisir le prenom de l'utilisateur");
        user.setPrenom(scanner.nextLine());
        System.out.println("Saisir le login de l'utilisateur");
        user.setLogin(scanner.nextLine());
        System.out.println("Saisir le mot de passe de l'utilisateur");
        user.setPassword(scanner.nextLine());
        user.setRole(Role.CLIENT);
        return user;

    }

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

    public boolean saisieEtat() {
        System.out.println("1-Actif");
        System.out.println("2-Inactif");
        System.out.println("Entrer votre choix:");
        int etatChoice = scanner.nextInt();
        return etatChoice == 1 ? true : false;
    }

    public User saisieBoutiquierOrAdmin(Role role) {
        User user = new User();
        scanner.nextLine();
        System.out.println("Saisir le login de l'utilisateur");
        user.setLogin(scanner.nextLine());
        System.out.println("Saisir le mot de passe de l'utilisateur");
        user.setPassword(scanner.nextLine());
        user.setRole(role);
        return user;

    }

    public int choiceFilter() {
        System.out.println("1-Actif");
        System.out.println("2-Role");
        System.out.println("Entrer votre choix:");
        return scanner.nextInt();
    }


    
}
