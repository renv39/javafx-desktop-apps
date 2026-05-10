package com.rendellvelasco.utils;

public enum CarCategory {
    PASSENGERVEHICLES, COMMERCIALVEHICLES, SPECIALIZEDVEHICLES;

    public static boolean isValid(String value) {
        if (value == null) return false;
        for (CarCategory c : CarCategory.values()) {
            if (c.name().equalsIgnoreCase(value.trim())) {
                return true;
            }
        }
        return false;
    }
}
