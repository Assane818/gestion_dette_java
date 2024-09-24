package com.asn.data.services;

import java.util.List;

import com.asn.data.entities.User;
import com.asn.data.enums.Etat;
import com.asn.data.enums.Role;
import com.asn.data.repositories.UserRepository;

public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean save(User user) {
        this.userRepository.insert(user);
        return true;
    }

    @Override
    public User getById(int id) {
        return userRepository.selectById(id);
    }

    @Override
    public boolean updateEtat(User user, boolean etat) {
        return userRepository.updateEtat(user, etat);
    }

    @Override
    public List<User> getUsersByEtat() {
        return userRepository.findAllUsersByEtat();
    }

    @Override
    public List<User> getUsersByRole(Role role) {
        return userRepository.findAllUsersByRole(role);
    }

    @Override
    public List<User> show() {
        return userRepository.selectAll();
    }
    
}
