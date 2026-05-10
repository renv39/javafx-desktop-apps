
package com.rendellvelasco.autoloanapp.repositories;

import com.rendellvelasco.autoloanapp.models.User;
import com.rendellvelasco.autoloanapp.utilities.DatabaseManager;

import java.sql.*;

public class SqlUserRepository implements IUserRepository {

    @Override
    public void addUser(User user) {
        String sql = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPasswordHash());
            stmt.setString(3, user.getEmail());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("addUser failed: " + e.getMessage());
        }
    }

    @Override
    public User findUser(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setPasswordHash(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                return user;
            }
        } catch (SQLException e) {
            System.err.println("findUser failed: " + e.getMessage());
        }
        return null;
    }
}