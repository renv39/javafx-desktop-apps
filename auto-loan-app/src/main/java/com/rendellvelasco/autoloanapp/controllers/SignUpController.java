
package com.rendellvelasco.autoloanapp.controllers;

import com.rendellvelasco.autoloanapp.App;
import com.rendellvelasco.autoloanapp.models.User;
import com.rendellvelasco.autoloanapp.services.UserService;
import com.google.inject.Inject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class SignUpController {

    private final App app;
    private final UserService userService;
    private final User user = new User();
    private final StringProperty confirmPassword = new SimpleStringProperty();

    @Inject
    public SignUpController(App app, UserService userService) {
        this.app = app;
        this.userService = userService;
    }

    @FXML private TextField usernameTextField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private TextField emailTextField;
    @FXML private Button signUpButton;

    @FXML
    public void initialize() {
        usernameTextField.textProperty()
                .bindBidirectional(user.usernameProperty());
        passwordField.textProperty()
                .bindBidirectional(user.passwordHashProperty());
        confirmPasswordField.textProperty()
                .bindBidirectional(confirmPassword);
        emailTextField.textProperty()
                .bindBidirectional(user.emailProperty());

        signUpButton.disableProperty().bind(
                user.usernameProperty().isEmpty()
                        .or(user.passwordHashProperty().isEmpty())
                        .or(confirmPassword.isEmpty())
                        .or(user.passwordHashProperty().isNotEqualTo(confirmPassword))
                        .or(user.emailProperty().isEmpty())
        );
    }

    public boolean validateSignUp() {
        if (user.getUsername().isEmpty() || user.getPasswordHash().isEmpty()
                || user.getEmail().isEmpty()) {
            showAlert("Missing Information", "All fields must be filled.");
            return false;
        }
        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            showAlert("Password Mismatch", "Passwords do not match.");
            return false;
        }
        if (!user.getEmail().matches("[^@]+@[^@]+\\.[^@]+")) {
            showAlert("Invalid Email", "Please enter a valid email address.");
            return false;
        }
        if (user.getPasswordHash().length() < 6) {
            showAlert("Weak Password", "Password must be at least 6 characters.");
            return false;
        }
        return true;
    }

    @FXML
    public void onActionSignUpButton() {
        if (validateSignUp()) {
            // UserService handles BCrypt hashing before saving
            userService.register(
                    user.getUsername(),
                    user.getPasswordHash(),  // plain text - service hashes it
                    user.getEmail()
            );

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Account Created");
            alert.setContentText("Welcome, " + user.getUsername() + "!");
            alert.showAndWait();

            resetForm();
            app.showHomepage();
        }
    }

    @FXML
    public void onActionBackButton() {
        app.showHomepage();
    }

    private void resetForm() {
        user.setUsername("");
        user.setPasswordHash("");
        user.setEmail("");
        confirmPasswordField.clear();
        usernameTextField.requestFocus();
    }

    private void showAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validation Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}