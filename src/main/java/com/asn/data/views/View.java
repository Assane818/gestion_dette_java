package com.asn.data.views;

import java.util.List;

public interface View<T> {
    T saisie();
    void affiche(List<T> list);
    int saisieInt(String msg);
    Double saisieDouble(String msg);
    String saisieString(String msg);
    int choiceFilter(List<String> filtre);
}
