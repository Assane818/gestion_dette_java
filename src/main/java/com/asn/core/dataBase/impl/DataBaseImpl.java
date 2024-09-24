package com.asn.core.dataBase.impl;

import com.asn.core.dataBase.DataBase;

import java.sql.*;




public class DataBaseImpl implements DataBase {
    private final String url = "jdbc:postgresql://localhost:5432/gestion_dette";
    private final String user = "postgres";
    private final String password = "Assane123";
    protected PreparedStatement ps;
    protected Connection conn = null;

    @Override
    public void getConnexion() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeConnexion() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    @Override
    public ResultSet executeQuery() throws SQLException {
        return ps.executeQuery();
    }

    @Override
    public int executeUpdate() throws SQLException {
        return ps.executeUpdate();
    }

    @Override
    public void iniPreparedStatement(String sql) throws SQLException {
        if (sql.toUpperCase().trim().startsWith("INSERT")) {
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        } else {
            ps = conn.prepareStatement(sql);
        }
    }    
    
}
