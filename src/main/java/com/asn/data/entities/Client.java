package com.asn.data.entities;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


import lombok.Data;

@Data
public class Client {
    private int id;
    private String surname;
    private String phone;
    private String address;
    private User user;
    private static int nbreClient;


    public Client() {
        id = ++nbreClient;
    }
    
    public static int getNbreClient() {
        return ++nbreClient;
    }
    
    public static String getInsertSql() {
        return "INSERT INTO \"client\" (\"name\", \"surname\", \"phone\", \"address\") VALUES (?,?,?,?);";
    }

    public List<Object> getParams() {
        List<Object> params = new ArrayList<>();
        params.add(surname);
        params.add(phone);
        params.add(address);
        return params;
    }

    

    public static List<Client> getResultSet(ResultSet rs) {
        List<Client> clients = new ArrayList<>();
        try {
            while (rs.next()) {
                Client client = new Client();
                client.setId(rs.getInt("id"));
                client.setSurname(rs.getString("surname"));
                client.setPhone(rs.getString("phone"));
                client.setAddress(rs.getString("address"));
                clients.add(client);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clients;
        
    }
}
