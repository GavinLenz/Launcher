module com.example.ToontownLauncher {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.swing;
    requires javafx.graphics;
    requires javafx.media;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    requires java.net.http;
    requires org.json;

    // These are the required modules for Apache HttpClient
    requires org.apache.httpcomponents.client5.httpclient5;
    requires org.apache.httpcomponents.core5.httpcore5;
    requires com.fasterxml.jackson.databind;

    // Open packages to javafx.fxml for reflection
    opens com.example.ToontownLauncher.controllers to javafx.fxml;
    opens com.example.ToontownLauncher.controllers.eventhandlers to javafx.fxml;
    opens com.example.ToontownLauncher.utils.ui to javafx.fxml;

    // Export public packages
    exports com.example.ToontownLauncher.controllers;
    exports com.example.ToontownLauncher to javafx.graphics;
}