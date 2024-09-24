package com.asn.data.services;

import java.util.List;

import com.asn.data.entities.User;
import com.asn.data.enums.Role;

public interface UserService extends Service<User> {
    boolean updateEtat(User object, boolean etat);
    List<User> getUsersByEtat();
    List<User> getUsersByRole(Role role);
}
