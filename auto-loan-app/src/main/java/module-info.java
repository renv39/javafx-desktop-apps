module com.rendellvelasco.autoloanapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.sql;
    requires com.google.guice;
    requires bcrypt;
    requires org.xerial.sqlitejdbc;
    requires jakarta.inject;

    // Core app
    opens com.rendellvelasco.autoloanapp to javafx.fxml, com.google.guice;

    // Controllers - needs both JavaFX (FXML) and Guice (injection)
    opens com.rendellvelasco.autoloanapp.controllers to javafx.fxml, com.google.guice;

    // Models - needs javafx.base for properties binding
    opens com.rendellvelasco.autoloanapp.models to javafx.base, javafx.fxml, com.google.guice;

    // Services, repositories, config, util - Guice needs reflective access
    opens com.rendellvelasco.autoloanapp.services to com.google.guice;
    opens com.rendellvelasco.autoloanapp.repositories to com.google.guice;
    opens com.rendellvelasco.autoloanapp.config to com.google.guice;
    opens com.rendellvelasco.autoloanapp.utilities to com.google.guice;

    opens com.rendellvelasco.autoloanapp.state to com.google.guice;

    // Exports
    exports com.rendellvelasco.autoloanapp;
    exports com.rendellvelasco.autoloanapp.config;
}