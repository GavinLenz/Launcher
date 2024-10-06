package com.example.Launcher.controllers.eventhandlers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class FormEventHandlers {

    private DisplayController displayController;

    public FormEventHandlers(DisplayController displayController) {
        this.displayController = displayController;
    }

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
}