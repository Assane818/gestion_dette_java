package com.asn.core.dataBase;

import java.sql.*;


public interface DataBase {
    void getConnexion() throws SQLException;
    void closeConnexion() throws SQLException;
    ResultSet executeQuery() throws SQLException;
    int executeUpdate() throws SQLException;
    void iniPreparedStatement(String sql) throws SQLException;
    
}
