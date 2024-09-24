package com.asn.data.services;

import java.util.List;



public interface Service<T> {
    boolean save(T object);
    List<T> show();
    T getById(int id);
}
