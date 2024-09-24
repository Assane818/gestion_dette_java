package com.asn.core.repository.impl;

import java.util.ArrayList;
import java.util.List;

import com.asn.core.repository.Repository;


public abstract class RepositoryImpl<T> implements Repository<T> {
    protected List<T> list = new ArrayList<>();

    @Override
    public void insert(T data) {
        list.add(data);
    }

    public List<T> selectAll() {
        return list;
    }

}
