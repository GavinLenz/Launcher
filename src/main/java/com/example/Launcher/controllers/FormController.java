package com.example.Launcher.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import com.example.Launcher.models.Toon;
import javafx.stage.Stage;

public class FormController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private DisplayController displayController;  // Reference to the main controller to pass data back

    public void setDisplayController(DisplayController displayController) {
        this.displayController = displayController;
    }

    @FXML
    private void saveToon() {
        String name = nameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Create a Toon object and send it back to DisplayController
        if (!name.isEmpty() && !username.isEmpty() && !password.isEmpty()) {
            Toon toon = new Toon(name, username, password);
            displayController.addToon(toon);  // Sending the toon back to the display controller
            closeForm();
        }
    }

    @FXML
    private void closeForm() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}