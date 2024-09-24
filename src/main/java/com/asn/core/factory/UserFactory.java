package com.asn.core.factory;

import java.util.Scanner;

import com.asn.data.repositories.UserRepository;
import com.asn.data.repositories.db.UserRepositoryImplBd;
import com.asn.data.services.UserService;
import com.asn.data.services.UserServiceImpl;
import com.asn.data.views.UserView;

public class UserFactory {
    private UserRepository userRepository;
    private UserService userService;
    private UserView userView;

    public UserRepository getUserRepository() {
        if (userRepository == null) {
            userRepository = new UserRepositoryImplBd();
        }
        return userRepository;
    }

    public UserService getUserService() {
        if (userService == null) {
            userService = new UserServiceImpl(this.userRepository);
        }
        return userService;
    }
    
    public UserView getUserView(Scanner scanner) {
        if (userView == null) {
            userView = new UserView(scanner);
        }
        return userView;
    }
}
