package com.asn.core.repository;

import java.util.List;

public interface Repository<T> {
    void insert(T object);
    T selectById(int id);
    List<T> selectAll();
    
}
