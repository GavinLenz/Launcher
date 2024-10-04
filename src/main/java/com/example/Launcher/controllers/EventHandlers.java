package com.example.Launcher.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import java.io.IOException;
import java.util.Objects;

import com.example.Launcher.models.Toon;
import com.example.Launcher.ToonManager;
import com.example.Launcher.models.ToonWithCheckBox;
import com.example.Launcher.utils.ConfigManager;
import com.example.Launcher.utils.PathFinder;

public class EventHandlers {

    private ToonManager toonManager;
    private ListView<ToonWithCheckBox> toonsListView;
    private DisplayController displayController;

    public EventHandlers(ToonManager toonManager, ListView<ToonWithCheckBox> toonsListView, DisplayController displayController) {
        this.toonManager = toonManager;
        this.toonsListView = toonsListView;
        this.displayController = displayController;
    }

    @FXML
    void openAddToonForm() {
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

    @FXML
    void playSelectedToon() {
        ToonWithCheckBox selectedToonWithCheckBox = toonsListView.getSelectionModel().getSelectedItem();

        if (selectedToonWithCheckBox != null) {
            if (selectedToonWithCheckBox.isSelected()) {
                Toon selectedToon = selectedToonWithCheckBox.getToon();
                String username = selectedToon.getUsername();
                String password = selectedToon.getPassword();

                String ttrPath = ConfigManager.readConfig();

                if (PathFinder.isValidPath(ttrPath)) {
                    System.out.println("Launching toon with username: " + username);
                    PathFinder.launchGame(ttrPath);
                    showSuccessAlert(selectedToon.getName());
                } else {
                    System.out.println("Invalid path for TTR executable.");
                    showFailureAlert();
                }
            } else {
                System.out.println("Checkbox not selected.");
                showNoSelectionAlert();
            }
        } else {
            System.out.println("No toon selected.");
            showNoSelectionAlert();
        }
    }

    private void showSuccessAlert(String toonName) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Toon Launcher");
        alert.setHeaderText(null);
        alert.setContentText("Launching toon: " + toonName);
        alert.showAndWait();
    }

    private void showNoSelectionAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("No Toon Selected");
        alert.setHeaderText(null);
        alert.setContentText("Please select a toon to play.");
        alert.showAndWait();
    }

    private void showFailureAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Failed");
        alert.setHeaderText(null);
        alert.setContentText("The username or password is incorrect. Please try again.");
        alert.showAndWait();
    }
}