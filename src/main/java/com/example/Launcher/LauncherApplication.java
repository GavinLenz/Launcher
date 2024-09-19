package com.example.Launcher;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LauncherApplication extends Application {
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LauncherApplication.class.getResource("/com/example/Launcher/Launcher.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 500, 320);
            stage.setTitle("Toontown Launcher");
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading FXML file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch();
    }
}


/*

Comments to self...


1. Reformat FXML code for readability

2. Need to figure out how to display toons

 */