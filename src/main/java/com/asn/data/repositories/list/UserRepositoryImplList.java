package com.asn.data.repositories.list;

import java.util.ArrayList;
import java.util.List;

import com.asn.core.repository.impl.RepositoryListImpl;
import com.asn.data.entities.User;
import com.asn.data.enums.Role;
import com.asn.data.repositories.UserRepository;

public class UserRepositoryImplList extends RepositoryListImpl<User> implements UserRepository {
    public UserRepositoryImplList() {
        User user = new User();
        user.setNom("ADMIN");
        user.setPrenom("ADMIN");
        user.setLogin("ad@gmail.com");
        user.setPassword("passer");
        user.setEtat(true);
        user.setRole(Role.ADMIN);
        list.add(user);
    }

    @Override
    public User selectByLogin(String login) {
        for (User user : list) {
            if (user.getLogin().compareTo(login) == 0) {
                return user;
            }
        }
        return null;
    }

    @Override
    public boolean updateEtat(User object, boolean etat) {
        object.setEtat(etat);
        return true;
    }

    @Override
    public List<User> selectAllUsersByEtat() {
        List<User> users = new ArrayList<>();
        for (User user : list) {
            if (user.isEtat()) {
                users.add(user);
            }
        }
        return users;
    }

    @Override
    public List<User> selectAllUsersByRole(Role role) {
        List<User> users = new ArrayList<>();
        for (User user : list) {
            if (user.getRole() == role) {
                users.add(user);
            }
        }
        return users;
    }

    @Override
    public User selectUserConnect(String login, String password) {
        for (User user : list) {
            if (user.getLogin().compareTo(login) == 0 && user.getPassword().compareTo(password) == 0) {
                return user;
            }
        }
        return null;
    }
    
}
