
package com.rendellvelasco.projectstaffingtool.services;

import java.util.Map;

/**
 * Validates manager credentials.
 * In a real system this would hash passwords and query a user store.
 * For the workshop, credentials are hard-coded.
 */
public class AuthenticationService {

    // username -> password (plain text for demo purposes only)
    private static final Map<String, String> CREDENTIALS = Map.of(
            "admin",   "admin123",
            "manager", "manager123"
    );

    /**
     * Returns {@code true} if the username/password pair is valid.
     */
    public boolean authenticate(String user, String pass) {
        if (user == null || pass == null) return false;
        String expected = CREDENTIALS.get(user.trim().toLowerCase());
        return expected != null && expected.equals(pass);
    }
}
