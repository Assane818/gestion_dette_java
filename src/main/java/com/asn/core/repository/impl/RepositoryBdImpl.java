package com.asn.core.repository.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.asn.core.dataBase.impl.DataBaseImpl;
import com.asn.core.repository.Repository;
import com.asn.data.entities.Article;
import com.asn.data.entities.Client;
import com.asn.data.entities.Dette;
import com.asn.data.entities.User;
import com.asn.data.enums.Etat;
import com.asn.data.enums.Role;
import com.asn.data.repositories.ArticleRepository;
import com.asn.data.repositories.ClientRepository;
import com.asn.data.repositories.DetteRepository;
import com.asn.data.repositories.UserRepository;

public abstract class RepositoryBdImpl<T> extends DataBaseImpl implements Repository<T> {
    protected String tableName;
    protected Class<T> className;
    protected UserRepository userRepository;
    protected ClientRepository clientRepository;
    protected ArticleRepository articleRepository;
    protected DetteRepository detteRepository;
    @Override
    public List<T> selectAll(Class<T> clazz) {
        List<T> datas = new ArrayList<>();
        try {
            String sql = String.format("SELECT * FROM \"%s\"", tableName);
            this.getConnexion();
            this.iniPreparedStatement(sql);
            ResultSet rs = this.ps.executeQuery();
            while (rs.next()) {
                datas.add(this.convertToObject(rs, clazz));
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
        return datas;
    }

    @Override
    public int insert(T data) {
        int id = 0;
        try {
            String sql = this.generateRequestInsert(data);
            this.getConnexion();
            this.iniPreparedStatement(sql);
            this.convertToSql(this.ps, data);
            this.executeUpdate();
            ResultSet rs = this.ps.getGeneratedKeys();
            if (rs.next()) {
                List<Field> fields = getAllFields(data.getClass());
                for (Field field : fields) {
                    if (field.getName().equals("id")) {
                        field.setAccessible(true);
                        Field idField = field;
                        idField.set(data, rs.getInt(1));
                        id = rs.getInt(1);
                    }
                }
                
            }
            rs.close();
    
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeConnexion();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return id;
        
    }

    @Override
    public T selectById(int id) {
        try {
            String sql = String.format("SELECT * FROM \"%s\" WHERE \"id\" = ?", this.tableName);
            this.getConnexion();
            this.iniPreparedStatement(sql);
            this.ps.setInt(1, id);
            ResultSet rs = this.executeQuery();
            if (rs.next()) {
                return this.convertToObject(rs, className);
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
    public T convertToObject(ResultSet rs, Class<T> clazz) {
        try {
            T entity = clazz.getDeclaredConstructor().newInstance();
            List<Field> fields = getAllFields(clazz);
            for (Field field : fields) {
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                // Rendre les champs privés accessibles
                field.setAccessible(true);
                String fieldName = field.getName();
                Class<?> fieldType = field.getType();
                if (fieldType.equals(int.class) || fieldType.equals(Integer.class)) {
                    field.set(entity, rs.getInt(fieldName));
                } else if (fieldType.equals(String.class)) {
                    field.set(entity, rs.getString(fieldName));
                } else if (fieldType.equals(boolean.class)) {
                    field.set(entity, rs.getBoolean(fieldName));
                } else if (fieldType.equals(double.class)) {
                    field.set(entity, rs.getDouble(fieldName));
                } else if (fieldType.equals(LocalDateTime.class)) {
                    Timestamp timestamp = rs.getTimestamp(fieldName);
                    if (timestamp != null) {
                        field.set(entity, timestamp.toLocalDateTime());
                    } else {
                        field.set(entity, null);
                    }
                } else if (fieldType.equals(Role.class)) {
                    field.set(entity, Role.getValue(rs.getInt(fieldName)));
                } else if (fieldType.equals(User.class)) {
                    if (fieldName.compareTo("user_id") == 0) {
                        String columnName = fieldName + "_id";
                        field.set(entity, userRepository.selectById(rs.getInt(columnName)));
                    }
                    field.set(entity,null);
                } else if (fieldType.equals(Client.class)) {
                    String columnName = fieldName + "_id";
                    field.set(entity, clientRepository.selectById(rs.getInt(columnName)));
                } else if (fieldType.equals(Article.class)) {
                    String columnName = fieldName + "_id";
                    field.set(entity, articleRepository.selectById(rs.getInt(columnName)));
                } else if (fieldType.equals(Dette.class)) {
                    String columnName = fieldName + "_id";
                    field.set(entity, detteRepository.selectById(rs.getInt(columnName)));
                } else if (fieldType.equals(Etat.class)) {
                    field.set(entity, Etat.getValue(rs.getInt(fieldName)));
                }
            }
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void convertToSql(PreparedStatement ps, T entity) {
        try {
            List<Field> fields = getAllFields(entity.getClass());
            int i = 1;
            for (Field field : fields) {
                if (Modifier.isStatic(field.getModifiers()) || field.getName().equals("id") || List.class.isAssignableFrom(field.getType())) {
                    continue;
                }
                field.setAccessible(true);
                Class<?> fieldType = field.getType();
                if (fieldType.equals(int.class) || fieldType.equals(Integer.class)) {
                    ps.setInt(i++, field.getInt(entity));
                } else if (fieldType.equals(double.class)) {
                    ps.setDouble(i++, field.getDouble(entity));
                }else if (fieldType.equals(String.class)) {
                    ps.setString(i++, (String)field.get(entity));
                } else if (fieldType.equals(boolean.class)) {
                    ps.setBoolean(i++, field.getBoolean(entity));
                } else if (fieldType.equals(LocalDateTime.class)) {
                    ps.setTimestamp(i++, Timestamp.valueOf((LocalDateTime)field.get(entity)));
                } else if (fieldType.equals(Role.class)) {
                    ps.setInt(i++, ((Enum<?>)field.get(entity)).ordinal());
                } else if (fieldType.equals(Etat.class)) {
                    ps.setInt(i++, ((Enum<?>)field.get(entity)).ordinal());
                } else if (fieldType.equals(User.class)) {
                    User user = (User) field.get(entity);
                    if (user != null) {
                        ps.setInt(i++, user.getId());
                    } else {
                        ps.setNull(i++, Types.INTEGER);
                    }
                } else if (fieldType.equals(Client.class)) {
                    Client client = (Client) field.get(entity);
                    if (client != null) {
                        ps.setInt(i++, client.getId());
                    } else {
                        ps.setNull(i++, Types.INTEGER);
                    }
                } else if (fieldType.equals(Article.class)) {
                    Article article = (Article) field.get(entity);
                    if (article != null) {
                        ps.setInt(i++, article.getId());
                    } else {
                        ps.setNull(i++, Types.INTEGER);
                    }
                } else if (fieldType.equals(Dette.class)) {
                    Dette dette = (Dette) field.get(entity);
                    if (dette != null) {
                        ps.setInt(i++, dette.getId());
                    } else {
                        ps.setNull(i++, Types.INTEGER);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String generateRequestInsert(T object) {
        // Field[] fields = object.getClass().getDeclaredFields();
        List<Field> fields = getAllFields(object.getClass());
            // Construction de la requête SQL
            StringBuilder sql = new StringBuilder("INSERT INTO \"" + tableName + "\" (");
            StringBuilder values = new StringBuilder("VALUES (");
            boolean firstField = true;
            for (Field field : fields) {
                if (!Modifier.isStatic(field.getModifiers()) && !field.getName().equals("id" ) && !List.class.isAssignableFrom(field.getType())) {
                    if (!firstField) {
                        sql.append(", ");
                        values.append(", ");
                    }
                    String fieldName = field.getName();
                    if (field.getType().equals(User.class) || field.getType().equals(Client.class) || field.getType().equals(Article.class) || field.getType().equals(Dette.class)) {
                        sql.append("\"").append(fieldName).append("_id\"");
                    } else {
                        sql.append("\"").append(fieldName).append("\"");
                    }
                    values.append("?");
                    firstField = false;
                }  
            }
            sql.append(") ").append(values).append(")");
            return sql.toString();
    }

    private List<Field> getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>(); 
        while (clazz != null) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }
}
