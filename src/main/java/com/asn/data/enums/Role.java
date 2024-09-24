package com.asn.data.enums;

public enum Role {
    ADMIN,
    BOUTIQUIER,
    CLIENT;

    public static Role getValue(String value) {
        for (Role role : Role.values()) {
            if (role.name().compareTo(value) == 0) {
                return role;
            }
        }
        return null;
    }
}
