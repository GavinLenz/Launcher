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

    opens com.example.Launcher to javafx.fxml;
    opens com.example.Launcher.controllers to javafx.fxml;
    exports com.example.Launcher;
    exports com.example.Launcher.utils;
    opens com.example.Launcher.utils to javafx.fxml;
    exports main;
    opens main to javafx.fxml;
}