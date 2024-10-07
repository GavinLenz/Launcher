package com.example.Launcher;

import com.example.Launcher.controllers.eventhandlers.LauncherController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Objects;
import java.io.IOException;
import com.example.Launcher.controllers.DisplayController;

public class Main extends Application {

    private LauncherController launcherController;

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the FXML and create the scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/Launcher/Display.fxml"));
            Scene scene = new Scene(loader.load(), 500, 320);

            // Set the scene and window title
            primaryStage.setScene(scene);
            primaryStage.setTitle("Toontown Launcher");
            primaryStage.setResizable(false);

            // Apply stylesheets to the scene
            String stylesheetPath = getClass().getResource("/com/example/Launcher/styles.css").toExternalForm();
            if (stylesheetPath != null) {
                primaryStage.getScene().getStylesheets().add(stylesheetPath);
            } else {
                System.err.println("Warning: Stylesheet could not be loaded.");
            }

            // Get the controller and set the primary stage
            DisplayController controller = loader.getController();
            if (controller == null) {
                throw new IllegalStateException("DisplayController is null.");
            }
            controller.setPrimaryStage(primaryStage);

            // Show the primary stage
            primaryStage.show();
        } catch (IOException e) {
            System.err.println("Error loading the FXML file for the display: " + e.getMessage());
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // Handle potential issues such as a null controller
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
            // Capture any exceptions that occur during application launch
            System.err.println("Error launching the application: " + e.getMessage());
            e.printStackTrace();
        }
    }
}