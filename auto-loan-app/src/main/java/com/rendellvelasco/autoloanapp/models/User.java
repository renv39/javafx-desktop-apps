

package com.rendellvelasco.autoloanapp.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private transient StringProperty username = new SimpleStringProperty();
    private transient StringProperty passwordHash = new SimpleStringProperty(); // renamed from password
    private transient StringProperty email = new SimpleStringProperty();

    public User(String username, String passwordHash, String email) {
        this.username.set(username);
        this.passwordHash.set(passwordHash);
        this.email.set(email);
    }

    public User() {}

    // Custom serialization - write raw values
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(username.get());
        out.writeObject(passwordHash.get());
        out.writeObject(email.get());
    }

    // Custom deserialization - reconstruct properties
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        username = new SimpleStringProperty((String) in.readObject());
        passwordHash = new SimpleStringProperty((String) in.readObject());
        email = new SimpleStringProperty((String) in.readObject());
    }

    public String getUsername() { return username.get(); }
    public StringProperty usernameProperty() { return username; }
    public void setUsername(String username) { this.username.set(username); }

    public String getPasswordHash() { return passwordHash.get(); }
    public StringProperty passwordHashProperty() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash.set(passwordHash); }

    public String getEmail() { return email.get(); }
    public StringProperty emailProperty() { return email; }
    public void setEmail(String email) { this.email.set(email); }

    @Override
    public String toString() {
        return "User{username=" + username.get() + ", email=" + email.get() + '}';
    }
}