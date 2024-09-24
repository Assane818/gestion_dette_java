package com.asn.data.views;

import java.util.List;

public interface View<T> {
    T saisie();
    void affiche(List<T> list);
}
