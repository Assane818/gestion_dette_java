package com.asn.data.repositories.list;

import com.asn.core.repository.impl.RepositoryImpl;
import com.asn.data.entities.User;
import com.asn.data.enums.Role;
import com.asn.data.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
public class UserRepositoryImpl extends RepositoryImpl<User> implements UserRepository {

    @Override
    public User selectById(int id) {
        for (User user: list) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    @Override
    public boolean updateEtat(User user , boolean etat) {
        user.setEtat(etat);
        return true;
    }

    @Override
    public List<User> findAllUsersByEtat() {
        List<User> usersActif = new ArrayList<>();
        for (User user: list) {
            if (user.getEtat() == true) {
                usersActif.add(user);
            }
        }
        return usersActif;
    }

    @Override
    public List<User> findAllUsersByRole(Role role) {
        List<User> usersRole = new ArrayList<>();
        for (User user: list) {
            if (user.getRole() == role) {
                usersRole.add(user);
            }
        }
        return usersRole;
    }

    @Override
    public User selectByLogin(String login) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectByLogin'");
    }
}
