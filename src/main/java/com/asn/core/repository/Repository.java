package com.asn.core.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public interface Repository<T> {
    int insert(T object);
    T selectById(int id);
    List<T> selectAll(Class<T> clazz); 
    T convertToObject(ResultSet rs,Class<T> clazz);
    void convertToSql(PreparedStatement ps, T entity);
    String generateRequestInsert(T object);
    
}
