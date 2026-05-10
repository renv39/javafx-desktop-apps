
package com.rendellvelasco.autoloanapp.repositories;

import com.rendellvelasco.autoloanapp.models.User;

public interface IUserRepository {
    void addUser(User user);
    User findUser(String username);
}