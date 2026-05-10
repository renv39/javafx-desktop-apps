
package com.rendellvelasco.projectstaffingtool;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    // The injector is created ONCE and shared across the whole app
    private static Injector injector;

    @Override
    public void init() {
        // Bootstrap Guice with our wiring module
        injector = Guice.createInjector(new AppModule());
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/rendellvelasco/projectstaffingtool/login.fxml"));

        // KEY LINE: instead of FXMLLoader calling "new LoginController()",
        // it asks Guice to build it — so @Inject constructors are honoured
        loader.setControllerFactory(injector::getInstance);

        stage.setScene(new Scene(loader.load()));
        stage.setTitle("Login");
        stage.show();
    }

    /**
     * Exposes the injector so controllers can open new windows
     * and pass the injector along to the next FXMLLoader.
     */
    public static Injector getInjector() {
        return injector;
    }

    public static void main(String[] args) {
        Application.launch(App.class, args);
    }
}
