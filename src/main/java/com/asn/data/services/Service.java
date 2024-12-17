package com.asn.data.services;

import java.util.List;



public interface Service<T> {
    int save(T object);
    List<T> show();
    T getById(int id);
}
