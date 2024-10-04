package com.example.Launcher;

import com.example.Launcher.utils.ConfigManager;
import com.example.Launcher.utils.PathFinder;
import com.example.Launcher.utils.PathPrompt;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        String path = ConfigManager.readConfig();

        if (!PathFinder.isValidPath(path)) {
            System.out.println("Invalid path, trying to auto-detect...");
            path = PathFinder.autoDetectPath();
            if (path == null) {
                System.out.println("Auto-detection failed, prompting user for path...");
                path = PathPrompt.promptUserForPath(primaryStage);
                if (path != null) {
                    ConfigManager.writeConfig(path);
                }
            }
        }

        if (PathFinder.isValidPath(path)) {
            System.out.println("Launching game from: " + path);
            PathFinder.launchGame(path);
        } else {
            System.out.println("Failed to find a valid path to the TTR executable.");
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/Launcher/Display.fxml"));
        primaryStage.setTitle("Toontown Launcher");
        primaryStage.setScene(new Scene(loader.load(), 500, 320));
        primaryStage.setResizable(false);

        // Debug statement to check if the CSS file is found
        String cssPath = "/com/example/Launcher/styles.css";
        if (getClass().getResource(cssPath) != null) {
            System.out.println("CSS file found: " + cssPath);
            primaryStage.getScene().getStylesheets().add(Objects.requireNonNull(getClass().getResource(cssPath)).toExternalForm());
        } else {
            System.out.println("CSS file not found: " + cssPath);
        }

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}