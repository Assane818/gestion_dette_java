package com.asn.data.enums;

public enum Etat {
    VALIDER,
    ENCOURS,
    REFUSER,
    ARCHIVER;

    public static Etat getValue(int value) {
        for (Etat etat : Etat.values()) {
            if (etat.ordinal() == value) {
                return etat;
            }
        }
        return null;
    }
}
