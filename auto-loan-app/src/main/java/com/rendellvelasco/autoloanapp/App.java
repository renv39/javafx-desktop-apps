
package com.rendellvelasco.autoloanapp;

import com.rendellvelasco.autoloanapp.config.AppModule;
import com.rendellvelasco.autoloanapp.controllers.LoanAmortizationController;
import com.rendellvelasco.autoloanapp.controllers.LoanSavedListController;
import com.rendellvelasco.autoloanapp.utilities.DatabaseManager;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

@Singleton
public class App extends Application {

    private Injector injector;
    private Stage primaryStage;

    private Scene homepageScene;
    private Scene loginScene;
    private Scene signupScene;
    private Scene loanScene;
    private Scene amortizationScene;
    private Scene savedLoanListScene;

    // Controllers we need direct access to
    private LoanAmortizationController loanAmortizationController;
    private LoanSavedListController savedLoanListController;

    @Override
    public void start(Stage primaryStage) {
        try {
            this.primaryStage = primaryStage;
            this.primaryStage.setWidth(1400);
            this.primaryStage.setHeight(900);

            DatabaseManager.initialize();
            initGuice();
            initScenes();

            primaryStage.setTitle("Auto Loan Application");
            primaryStage.setScene(homepageScene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initGuice() {
        injector = Guice.createInjector(new AppModule(this));
    }

    private void initScenes() throws IOException {
        homepageScene = loadScene("/com/rendellvelasco/autoloanapp/homepage.fxml");
        loginScene    = loadScene("/com/rendellvelasco/autoloanapp/login.fxml");
        signupScene   = loadScene("/com/rendellvelasco/autoloanapp/sign-up.fxml");
        loanScene = loadScene("/com/rendellvelasco/autoloanapp/loan-application.fxml");

        // Loan - grab controller for loadLoan() callback from saved list
        FXMLLoader loanLoader = new FXMLLoader(
                getClass().getResource("/com/rendellvelasco/autoloanapp/loan-application.fxml"));
        loanLoader.setControllerFactory(injector::getInstance);

        // Amortization - grab controller to call displayAmortization()
        FXMLLoader amortLoader = new FXMLLoader(
                getClass().getResource("/com/rendellvelasco/autoloanapp/loan-amortization.fxml"));
        amortLoader.setControllerFactory(injector::getInstance);
        amortizationScene = new Scene(amortLoader.load());
        loanAmortizationController = amortLoader.getController();

        // Saved list - grab controller to call loadFromDB/File()
        //              and wire loadLoan() callback to LoanController
        FXMLLoader savedLoader = new FXMLLoader(
                getClass().getResource("/com/rendellvelasco/autoloanapp/loan-saved-list.fxml"));
        savedLoader.setControllerFactory(injector::getInstance);
        savedLoanListScene = new Scene(savedLoader.load());
        savedLoanListController = savedLoader.getController();
    }

    private Scene loadScene(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        loader.setControllerFactory(injector::getInstance);
        return new Scene(loader.load());
    }

    // Navigation
    public void showHomepage() {
        primaryStage.setTitle("Home");
        primaryStage.setScene(homepageScene);
    }

    public void showLogin() {
        primaryStage.setTitle("Login");
        primaryStage.setScene(loginScene);
    }

    public void showSignup() {
        primaryStage.setTitle("Sign Up");
        primaryStage.setScene(signupScene);
    }

    public void showLoanApplication() {
        primaryStage.setTitle("Loan Application");
        primaryStage.setScene(loanScene);
    }

    public void showAmortizationSchedule() {
        loanAmortizationController.displayAmortization();
        Stage stage = new Stage();
        stage.setTitle("Amortization Schedule");
        stage.setScene(amortizationScene);
        stage.show();
    }

    // Opens saved list window — loans already loaded, just show
    public void showSavedLoanList() {
        Stage stage = new Stage();
        stage.setTitle("Saved Loans");
        stage.setScene(savedLoanListScene);
        stage.show();
    }

    // Read from DB then show
    public void showSavedLoansFromDB() {
        savedLoanListController.loadFromDB();
        showSavedLoanList();
    }

    // Read from File then show
    public void showSavedLoansFromFile() {
        savedLoanListController.loadFromFile();
        showSavedLoanList();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

