package com.asn.data.repositories.db;



import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.asn.core.repository.impl.RepositoryBdImpl;
import com.asn.data.entities.Client;
import com.asn.data.entities.User;
import com.asn.data.repositories.ClientRepository;
import com.asn.data.repositories.UserRepository;

public class ClientRepositoryImplBd extends RepositoryBdImpl<Client>  implements ClientRepository{
    private UserRepository userRepositoryImpl;

    public ClientRepositoryImplBd(UserRepository userRepository) {
        this.tableName = "client";
        this.userRepositoryImpl = userRepository;
    }


    @Override
    public void insert(Client client) {
        try {
            User user = client.getUser();
            if (user != null) {
                userRepositoryImpl.insert(user);
            }
            String sql = String.format("INSERT INTO \"%s\" (\"surname\", \"phone\", \"address\", \"userId\") VALUES (?,?,?,?)", this.tableName);
            this.getConnexion();
            this.iniPreparedStatement(sql);
            this.ps.setString(1, client.getSurname());
            this.ps.setString(2, client.getPhone());
            this.ps.setString(3, client.getAddress());
            if (user != null) {
                this.ps.setInt(4, user.getId());
            } else {
                this.ps.setNull(4, Types.INTEGER);
            }
            this.executeUpdate();
            ResultSet rs = this.ps.getGeneratedKeys();
            if (rs.next()) {
                client.setId(rs.getInt(1));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeConnexion();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Client selectById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectById'");
    }

    


    @Override
    public Client selectByPhone(String phone) {
        try {
            String sql = String.format(" SELECT * FROM \"%s\" WHERE \"phone\" like ?", this.tableName);
            this.getConnexion();
            this.iniPreparedStatement(sql);
            this.ps.setString(1, phone);
            ResultSet rs = this.executeQuery();
            while (rs.next()) {
                return convertToObject(rs);
            }
            rs.close();
        } catch (SQLException e) {
           System.out.println("Erreur: " + e.getMessage());
        } finally {
            try {
                this.closeConnexion();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Client convertToObject(ResultSet rs) throws SQLException {
        Client client = new Client();
        client.setId(rs.getInt("id"));
        client.setSurname(rs.getString("surname"));
        client.setPhone(rs.getString("phone"));
        client.setAddress(rs.getString("address"));
        int userId = rs.getInt("userId");
        client.setUser(userRepositoryImpl.selectById(userId));
        return client;
    }

    @Override
    public Client selectBySurname(String surname) {
        try {
            String sql = String.format(" SELECT * FROM \"%s\" WHERE \"surname\" like ?", this.tableName);
            this.getConnexion();
            this.iniPreparedStatement(sql);
            this.ps.setString(1, surname);
            ResultSet rs = this.executeQuery();
            while (rs.next()) {
                return convertToObject(rs);
            }
            rs.close();
        } catch (SQLException e) {
           System.out.println("Erreur: " + e.getMessage());
        } finally {
            try {
                this.closeConnexion();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    
}
