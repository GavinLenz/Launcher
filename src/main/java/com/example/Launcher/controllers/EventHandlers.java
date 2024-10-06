package com.example.Launcher.controllers;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import java.io.IOException;
import java.util.Objects;

import com.example.Launcher.UIInitializer;
import com.example.Launcher.models.Toon;
import com.example.Launcher.ToonManager;
import com.example.Launcher.utils.Login;


public class EventHandlers {

    private ToonManager toonManager;
    private ListView<Toon> toonsListView;
    private DisplayController displayController;

    public EventHandlers(ToonManager toonManager, ListView<Toon> toonsListView, DisplayController displayController, UIInitializer uiInitializer) {
        this.toonManager = toonManager;
        this.toonsListView = toonsListView;
        this.displayController = displayController;
    }

    protected void addToonHandler() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/Launcher/Form.fxml"));
            Parent root = loader.load();

            FormController formController = loader.getController();
            formController.setDisplayController(displayController);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(" ");

            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/Launcher/styles.css")).toExternalForm());

            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void playClickHandler() {
        ObservableList<Toon> selectedToons = UIInitializer.getSelectedToons();  // Access selected toons from UIInitializer

        if (selectedToons.isEmpty()) {
            showNoSelectionAlert();  // Handle case when no Toons are selected
            return;
        }

        for (Toon toon : selectedToons) {
            String username = toon.getUsername();
            String password = toon.getPassword();

            Login.startLogin(username, password);
        }
    }

    // REMOVE LATER - will be handled by alertHandler 
    private void showNoSelectionAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("No Toon Selected");
        alert.setHeaderText(null);
        alert.setContentText("Please select a toon to play.");
        alert.showAndWait();
    }
}