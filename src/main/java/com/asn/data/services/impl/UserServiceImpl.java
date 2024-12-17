package com.asn.data.services.impl;

import java.util.List;

import com.asn.data.entities.User;
import com.asn.data.enums.Role;
import com.asn.data.repositories.UserRepository;
import com.asn.data.services.UserService;

public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public int save(User user) {
        return this.userRepository.insert(user);
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
        return userRepository.selectAllUsersByEtat();
    }

    @Override
    public List<User> getUsersByRole(Role role) {
        return userRepository.selectAllUsersByRole(role);
    }

    @Override
    public List<User> show() {
        return userRepository.selectAll(User.class);
    }

    @Override
    public User getByLogin(String login) {
        return userRepository.selectByLogin(login);
    }

    @Override
    public User selectUserConnect(String login, String password) {
        return userRepository.selectUserConnect(login, password);
    }
    
}
