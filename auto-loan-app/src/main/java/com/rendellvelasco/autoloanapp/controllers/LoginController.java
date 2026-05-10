
package com.rendellvelasco.autoloanapp.controllers;

import com.rendellvelasco.autoloanapp.App;
import com.rendellvelasco.autoloanapp.models.User;
import com.rendellvelasco.autoloanapp.services.UserService;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController {

    private final App app;
    private final UserService userService;
    private final User user = new User();

    @Inject
    public LoginController(App app, UserService userService) {
        this.app = app;
        this.userService = userService;
    }

    @FXML private TextField usernameTextField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Button backButton;

    @FXML
    public void initialize() {
        usernameTextField.textProperty()
                .bindBidirectional(user.usernameProperty());
        passwordField.textProperty()
                .bindBidirectional(user.passwordHashProperty());

        loginButton.disableProperty().bind(
                user.usernameProperty().isEmpty()
                        .or(user.passwordHashProperty().isEmpty()));
    }

    @FXML
    void onActionLoginButton() {
        // UserService handles BCrypt verification
        boolean success = userService.authenticate(
                user.getUsername(),
                user.getPasswordHash()   // plain text here - service does the hash check
        );

        if (success) {
            app.showLoanApplication();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText("Invalid Credentials");
            alert.setContentText("Username or password is incorrect.");
            alert.showAndWait();
        }
    }

    @FXML
    void onActionBackButton() {
        app.showHomepage();
    }
}