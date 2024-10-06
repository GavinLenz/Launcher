package com.example.Launcher;

import com.example.Launcher.controllers.eventhandlers.LauncherController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

import com.example.Launcher.controllers.DisplayController;

public class Main extends Application {

    private LauncherController launcherController;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/Launcher/Display.fxml"));
        Scene scene = new Scene(loader.load(), 500, 320);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Toontown Launcher");
        primaryStage.setResizable(false);
        primaryStage.getScene().getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/Launcher/styles.css")).toExternalForm());

        DisplayController controller = loader.getController();
        controller.setPrimaryStage(primaryStage);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}