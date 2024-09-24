package com.asn.core.repository.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.asn.core.dataBase.impl.DataBaseImpl;
import com.asn.core.repository.Repository;

public abstract class RepositoryBdImpl<T> extends DataBaseImpl implements Repository<T> {
    protected String tableName;
    public abstract T convertToObject(ResultSet rs) throws SQLException;

    @Override
    public List<T> selectAll() {
        List<T> datas = new ArrayList<>();
        try {
            String sql = String.format("SELECT * FROM \"%s\"", tableName);
            this.getConnexion();
            this.iniPreparedStatement(sql);
            ResultSet rs = this.ps.executeQuery();
            while (rs.next()) {
                datas.add(convertToObject(rs));
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
}
