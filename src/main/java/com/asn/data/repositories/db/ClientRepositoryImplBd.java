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
    
    public ClientRepositoryImplBd(UserRepository userRepository) {
        super.tableName = "clients";
        super.className = Client.class;
        super.userRepository = userRepository;
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
                return convertToObject(rs, className);
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
        return null;
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
                return convertToObject(rs, className);
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
        return null;
    }

    @Override
    public void updateUserId(Client object, int id) {
        try {
            String sql = "UPDATE clients SET user_id = ? WHERE id = ?";
            this.getConnexion();
            this.iniPreparedStatement(sql);
            this.ps.setInt(1, id);
            this.ps.setInt(2, object.getId());
            this.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Client> selectClientAccount() {
        List<Client> allArticles = this.selectAll(className);
        List<Client> articlesAccount = new ArrayList<>();
        for (Client client : allArticles) {
            if (client.getUser() != null) {
                articlesAccount.add(client);
            }
        }
        return articlesAccount;
    }

    @Override
    public List<Client> selectClientNoAccount() {
        List<Client> allArticles = this.selectAll(className);
        List<Client> articlesNoAccount = new ArrayList<>();
        for (Client client : allArticles) {
            if (client.getUser() == null) {
                articlesNoAccount.add(client);
            }
        }
        return articlesNoAccount;
    }

    @Override
    public Client selectByUser(User user) {
        try {
            String sql = String.format(" SELECT * FROM \"%s\" WHERE \"user_id\" = ?", this.tableName);
            this.getConnexion();
            this.iniPreparedStatement(sql);
            this.ps.setInt(1, user.getId());
            ResultSet rs = this.executeQuery();
            while (rs.next()) {
                return convertToObject(rs, className);
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
        return null;
    }

}
