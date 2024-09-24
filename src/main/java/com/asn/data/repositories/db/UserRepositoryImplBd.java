package com.asn.data.repositories.db;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.asn.core.repository.impl.RepositoryBdImpl;
import com.asn.data.entities.Client;
import com.asn.data.entities.User;
import com.asn.data.enums.Etat;
import com.asn.data.enums.Role;
import com.asn.data.repositories.ClientRepository;
import com.asn.data.repositories.UserRepository;
import com.mysql.cj.protocol.Resultset;

public class UserRepositoryImplBd extends RepositoryBdImpl<User>  implements UserRepository{

    public UserRepositoryImplBd() {
        this.tableName = "user";
    }

    @Override
    public void insert(User user) {
        String sql = String.format("INSERT INTO \"%s\" (\"nom\", \"prenom\", \"login\", \"password\", \"role\", \"etat\") VALUES (?,?,?,?,?,?)", this.tableName);
        try {
            this.getConnexion();
            this.iniPreparedStatement(sql);
            this.ps.setString(1, user.getNom());
            this.ps.setString(2, user.getPrenom());
            this.ps.setString(3, user.getLogin());
            this.ps.setString(4, user.getPassword());
            this.ps.setString(5, user.getRole().name());
            this.ps.setBoolean(6, user.getEtat());
            this.executeUpdate();
            ResultSet rs = this.ps.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getInt(1));
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
    public User selectById(int id) {
        try {
            String sql = String.format("SELECT * FROM \"%s\" WHERE \"id\" = ?", this.tableName);
            this.getConnexion();
            this.iniPreparedStatement(sql);
            this.ps.setInt(1, id);
            ResultSet rs = this.executeQuery();
            if (rs.next()) {
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
    public User convertToObject(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setNom(rs.getString("nom"));
        user.setPrenom(rs.getString("prenom"));
        user.setLogin(rs.getString("login"));
        user.setRole(Role.getValue(rs.getString("role")));
        user.setEtat(rs.getBoolean("etat"));
        return user;
    }

    @Override
    public User selectByLogin(String login) {
        try {
            String sql = String.format("SELECT * FROM \"%s\" WHERE \"login\" like ?", this.tableName);
            this.getConnexion();
            this.iniPreparedStatement(sql);
            this.ps.setString(1, login);
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
    public List<User> findAllUsersByEtat() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllUsersByEtat'");
    }

    @Override
    public List<User> findAllUsersByRole(Role role) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllUsersByRole'");
    }

    @Override
    public boolean updateEtat(User object, boolean etat) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateEtat'");
    }

    
}
