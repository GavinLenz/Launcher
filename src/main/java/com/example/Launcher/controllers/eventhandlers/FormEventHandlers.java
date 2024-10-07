package com.example.Launcher.controllers.eventhandlers;

import com.example.Launcher.controllers.DisplayController;
import com.example.Launcher.models.Toon;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FormEventHandlers {

    @FXML
    private TextField nameField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private DisplayController displayController;

    // Setter for DisplayController
    public void setDisplayController(DisplayController displayController) {
        this.displayController = displayController;
    }

    @FXML
    // Save Toon to DisplayController
    public void saveToon() {
        String name = nameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Ensure no field is empty before proceeding
        if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "All fields must be filled.");
            alert.show();
            return;
        }

        if (displayController != null) {
            Toon toon = new Toon(name, username, password);
            displayController.addToon(toon); // Pass new Toon to DisplayController
            closeForm();
        } else {
            System.out.println("Error: DisplayController is null.");
        }
    }

    @FXML
    public void closeForm() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}