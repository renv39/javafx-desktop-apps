
package com.rendellvelasco.autoloanapp.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.rendellvelasco.autoloanapp.models.User;
import com.rendellvelasco.autoloanapp.repositories.IUserRepository;
import com.google.inject.Inject;

public class UserService {

    private final IUserRepository userRepo;

    @Inject
    public UserService(IUserRepository userRepo) {
        this.userRepo = userRepo;
    }

    // Called by SignupController - hashes password before saving
    public void register(String username, String plainTextPassword, String email) {
        String hashed = hashPassword(plainTextPassword);
        User user = new User(username, hashed, email);
        userRepo.addUser(user);
    }

    // Called by LoginController - fetches user and verifies hash
    public boolean authenticate(String username, String plainTextPassword) {
        User user = userRepo.findUser(username);
        if (user == null) return false;
        return BCrypt.verifyer()
                .verify(plainTextPassword.toCharArray(), user.getPasswordHash())
                .verified;
    }

    // Private - only used internally by register()
    private String hashPassword(String plainTextPassword) {
        return BCrypt.withDefaults()
                .hashToString(12, plainTextPassword.toCharArray());
    }
}