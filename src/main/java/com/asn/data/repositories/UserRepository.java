package com.asn.data.repositories;

import java.util.List;

import com.asn.core.repository.Repository;
import com.asn.data.entities.User;
import com.asn.data.enums.Role;

public interface UserRepository extends Repository<User> {
    User selectByLogin(String login);
    boolean updateEtat(User object, boolean etat);
    List<User> findAllUsersByEtat();
    List<User> findAllUsersByRole(Role role);
}
