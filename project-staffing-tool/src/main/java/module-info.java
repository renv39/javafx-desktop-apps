module com.rendellvelsaco.projectstaffingtool {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.guice;
    //requires com.google.guice.extensions.assistedinject; // only needed if you use AssistedInject later
    //requires javax.inject;                               // @Inject, @Singleton annotations

    opens com.rendellvelasco.projectstaffingtool to javafx.fxml, com.google.guice;
    opens com.rendellvelasco.projectstaffingtool.controllers to javafx.fxml, com.google.guice;
    opens com.rendellvelasco.projectstaffingtool.models to javafx.base, javafx.fxml, com.google.guice;
    opens com.rendellvelasco.projectstaffingtool.services to javafx.fxml, com.google.guice;
    opens com.rendellvelasco.projectstaffingtool.data to javafx.fxml, com.google.guice;
    opens com.rendellvelasco.projectstaffingtool.exceptions to com.google.guice, javafx.fxml;

    exports com.rendellvelasco.projectstaffingtool;
    exports com.rendellvelasco.projectstaffingtool.controllers;
    exports com.rendellvelasco.projectstaffingtool.models;
    exports com.rendellvelasco.projectstaffingtool.services;
    exports com.rendellvelasco.projectstaffingtool.data;
    exports com.rendellvelasco.projectstaffingtool.exceptions;

}