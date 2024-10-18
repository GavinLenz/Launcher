package com.example.ToontownLauncher;

import com.example.ToontownLauncher.utils.ui.StyleManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;

import com.example.ToontownLauncher.controllers.DisplayController;

import java.io.IOException;

public class Main extends Application {

    private final String CSS_PATH = "/com/example/ToontownLauncher/styles.css";


    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ToontownLauncher/Display.fxml"));
            Scene scene = new Scene(loader.load(), 500, 320);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Toontown Launcher");
            primaryStage.setResizable(false);

            StyleManager styleManager = new StyleManager();
            URL stylesheetURL = getClass().getResource(CSS_PATH);
            if (stylesheetURL != null) {
                styleManager.applyStylesheet(primaryStage.getScene(), stylesheetURL.toExternalForm());
            } else {
                System.out.println("Stylesheet not found: " + CSS_PATH);
            }

            DisplayController controller = loader.getController();
            if (controller == null) {
                throw new IllegalStateException("DisplayController is null.");
            }
            controller.setPrimaryStage(primaryStage);

            primaryStage.show();
        } catch (IOException e) {
            System.err.println("Error loading the FXML file for the display: " + e.getMessage());
            e.printStackTrace();
        } catch (IllegalStateException e) {
            System.err.println("Error setting up the main window: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error occurred during application startup: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            launch(args);
        } catch (Exception e) {
            System.err.println("Error launching the application: " + e.getMessage());
            e.printStackTrace();
        }
    }
}