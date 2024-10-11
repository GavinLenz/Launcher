package com.example.ToontownLauncher.controllers.eventhandlers;

import com.example.ToontownLauncher.controllers.DisplayController;
import com.example.ToontownLauncher.models.Toon;
import com.example.ToontownLauncher.patterns.command.AddToonCommand;
import com.example.ToontownLauncher.patterns.command.Command;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddToonController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;

    public void setDisplayController(DisplayController displayController) {
        // Link the DisplayController to this controller
    }

    @FXML
    public void saveToon() {
        String name = nameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Ensure no field is empty before proceeding
        if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
            System.out.println("All fields must be filled.");
            return;
        }

        Toon newToon = new Toon(name, username, password);

        // Use the Command Pattern to add the Toon (No need to pass ToonListManager now)
        Command addToonCommand = new AddToonCommand(newToon);
        addToonCommand.execute();

        closeForm();
    }

    @FXML
    public void closeForm() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}