
package com.rendellvelasco.projectstaffingtool.controllers;

import com.rendellvelasco.projectstaffingtool.App;
import com.rendellvelasco.projectstaffingtool.services.AuthenticationService;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Login screen controller.
 * AuthenticationService is injected by Guice — no "new" keyword needed.
 */
public class LoginController {

    private final AuthenticationService authService;

    @FXML private TextField     usernameTextField;
    @FXML private PasswordField passwordField;
    @FXML private Button        loginButton;
    @FXML private Label         errorLabel;

    @Inject
    public LoginController(AuthenticationService authService) {
        this.authService = authService;
    }

    @FXML
    private void onActionLoginButton() {
        String username = usernameTextField.getText();
        String password = passwordField.getText();

        if (authService.authenticate(username, password)) {
            openDashboard();
        } else {
            showError("Invalid Credentials");
        }
    }

    private void showError(String message) {
        if (errorLabel != null) {
            errorLabel.setText(message);
            errorLabel.setTextFill(Color.RED);
            errorLabel.setVisible(true);
        }
    }

    private void openDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/rendellvelasco/projectstaffingtool/portfolio-dashboard.fxml"));

            // Guice builds DashboardController and injects its dependencies
            loader.setControllerFactory(App.getInjector()::getInstance);

            Parent root = loader.load();
            Stage dashStage = new Stage();
            dashStage.setTitle("Portfolio Dashboard");
            dashStage.setScene(new Scene(root));
            dashStage.show();

            Stage loginStage = (Stage) loginButton.getScene().getWindow();
            loginStage.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            showError("Failed to load dashboard.");
        }
    }
}