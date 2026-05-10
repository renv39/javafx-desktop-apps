

package com.rendellvelasco.autoloanapp.repositories;

import com.rendellvelasco.autoloanapp.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private final List<User> users = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
    }

    private User findUser(String userName) {
        return users.stream()
                .filter(user -> user.getUsername().equals(userName))
                .findFirst()
                .orElse(null);
    }

    public boolean validateLogin(String userName, String password) {
        try {
            User user = findUser(userName);
            return user.getUsername().equals(userName) && user.getPasswordHash().equals(password);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String toString() {
        return "UserRepository{" +
                "users=" + users +
                '}';
    }
}
