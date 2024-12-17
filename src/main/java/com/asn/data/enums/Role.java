package com.asn.data.enums;

public enum Role {
    ADMIN,
    BOUTIQUIER,
    CLIENT;

    public static Role getValue(int value) {
        for (Role role : Role.values()) {
            if (role.ordinal() == value) {
                return role;
            }
        }
        return null;
    }
}
    