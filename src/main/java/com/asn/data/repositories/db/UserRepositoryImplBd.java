package com.asn.data.repositories.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.asn.core.repository.impl.RepositoryBdImpl;
import com.asn.data.entities.User;
import com.asn.data.enums.Role;
import com.asn.data.repositories.UserRepository;

public class UserRepositoryImplBd extends RepositoryBdImpl<User>  implements UserRepository{

    public UserRepositoryImplBd() {
        super.tableName = "users";
        super.className = User.class;
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
    public List<User> selectAllUsersByEtat() {
        List<User> users = new ArrayList<>();
        try {
            String sql = String.format("SELECT * FROM \"%s\" WHERE \"etat\" = ?", tableName);
            this.getConnexion();
            this.iniPreparedStatement(sql);
            this.ps.setBoolean(1, true);
            ResultSet rs = this.ps.executeQuery();
            while (rs.next()) {
                users.add(this.convertToObject(rs, className));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeConnexion();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return users;
    }

    @Override
    public List<User> selectAllUsersByRole(Role role) {
        List<User> users = new ArrayList<>();
        try {
            String sql = String.format("SELECT * FROM \"%s\" WHERE \"role\" = ?", tableName);
            this.getConnexion();
            this.iniPreparedStatement(sql);
            this.ps.setInt(1, role.ordinal());
            ResultSet rs = this.ps.executeQuery();
            while (rs.next()) {
                users.add(this.convertToObject(rs, className));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeConnexion();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return users;
    }

    @Override
    public boolean updateEtat(User object, boolean etat) {
        try {
            String sql = "UPDATE users SET etat = ? WHERE id = ?";
            this.getConnexion();
            this.iniPreparedStatement(sql);
            this.ps.setBoolean(1, etat);
            this.ps.setInt(2, object.getId());
            this.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


    @Override
    public User selectUserConnect(String login, String password) {
        try {
            String sql = String.format("SELECT * FROM \"%s\" WHERE \"login\" like ? AND \"password\" like ? AND \"etat\" = ?", tableName);
            this.getConnexion();
            this.iniPreparedStatement(sql);
            this.ps.setString(1, login);
            this.ps.setString(2, password);
            this.ps.setBoolean(3, true);
            ResultSet rs = this.executeQuery();
            while (rs.next()) {
                return this.convertToObject(rs, className);
            }
            rs.close();
        } catch (Exception e) {
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
