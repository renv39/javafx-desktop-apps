module com.rendellvelasco.fleettracker {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.rendellvelasco.fleettracker.controllers to javafx.fxml;
    exports com.rendellvelasco.fleettracker.controllers;

    exports com.rendellvelasco.fleettracker.app;
    opens com.rendellvelasco.fleettracker.app to javafx.fxml;
}