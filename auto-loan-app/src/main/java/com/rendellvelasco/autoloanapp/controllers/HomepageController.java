
package com.rendellvelasco.autoloanapp.controllers;
import com.rendellvelasco.autoloanapp.App;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HomepageController {

    private final App app;

    @Inject
    public HomepageController(App app) {
        this.app = app;
    }

    @FXML
    private Button loginButton;

    @FXML
    private Button signUpButton;

    @FXML
    private void onClickLoginButton() {
        app.showLogin();
    }

    @FXML
    private void onClickSignUpButton() {
        app.showSignup();
    }
}