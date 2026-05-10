package com.rendellvelasco.fleettracker.app;
import com.rendellvelasco.fleettracker.controllers.MainViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/rendellvelasco/fleettracker/views/main-view.fxml")
        );
        Scene scene = new Scene(loader.load());
        MainViewController controller = loader.getController();
        controller.initData();
        stage.setTitle("Vehicle Management System");
        stage.setScene(scene);
        stage.show();
    }
}
