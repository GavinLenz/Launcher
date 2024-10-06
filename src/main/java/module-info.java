module com.example.Launcher {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    requires java.net.http;
    requires org.json;

    // Open packages to javafx.fxml for reflection
    opens com.example.Launcher to javafx.fxml;
    opens com.example.Launcher.controllers.eventhandlers to javafx.fxml;
    opens com.example.Launcher.controllers to javafx.fxml;
    opens com.example.Launcher.utils.ui to javafx.fxml;

    // Export public packages
    exports com.example.Launcher;
    exports com.example.Launcher.utils;
}