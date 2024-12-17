package com.asn.core.controllers;

import com.asn.data.entities.User;

public class UserConnect {
    private static User connectUser;
    
    public static User getUserConnect() {
        return connectUser;
    }

    public static void setUserConnect(User connectUser) {
        UserConnect.connectUser = connectUser;
    }
}
