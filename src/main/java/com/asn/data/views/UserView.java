package com.asn.data.views;


import com.asn.data.entities.User;
import com.asn.data.enums.Role;

public interface UserView extends View<User> {
    Role saisieRole();
    Role saisieAllRole();
    boolean saisieEtat();
    User saisieBoutiquierOrAdmin(Role role);
    User saisieConnexion();
}
