package com.example.Launcher.controllers.eventhandlers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.example.Launcher.controllers.DisplayController;
import com.example.Launcher.models.Toon;
import com.example.Launcher.models.manager.ToonListManager;
import com.example.Launcher.utils.ui.ToonListInitializer;

import java.io.IOException;
import java.util.Objects;

public class ToonEventHandlers {

    private final ListView<Toon> toonsListView;
    private ToonListManager toonManager;
    private DisplayController displayController;
    private ToonListInitializer uiInitializer;
    private Stage primaryStage;  // Store the reference to primaryStage

    public ToonEventHandlers(ToonListManager toonManager, ListView<Toon> toonsListView,
                             DisplayController displayController, ToonListInitializer uiInitializer) {
        this.toonManager = toonManager;
        this.toonsListView = toonsListView;
        this.displayController = displayController;
        this.uiInitializer = uiInitializer;
    }

    // Set the primary stage
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    // Handle opening the Add Toon form
    public void openAddToonForm() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/Launcher/Form.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add Toon");

            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/Launcher/styles.css")).toExternalForm());

            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Handle adding a Toon
    public void addToonHandler() {
        openAddToonForm();
    }
}