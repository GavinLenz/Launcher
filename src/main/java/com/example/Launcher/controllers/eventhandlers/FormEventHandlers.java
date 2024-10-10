package com.example.Launcher.controllers.eventhandlers;

import com.example.Launcher.controllers.DisplayController;
import com.example.Launcher.models.Toon;
import com.example.Launcher.patterns.command.AddToonCommand;
import com.example.Launcher.patterns.command.Command;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FormEventHandlers {

    @FXML
    private TextField nameField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;

    // This method is no longer required, but kept for future needs
    public void setDisplayController(DisplayController displayController) {
        // Link the DisplayController to this controller
    }

    @FXML
    // Called when the  user clicks the "Save" button
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
        addToonCommand.execute();  // Add the Toon to the ToonListManager

        closeForm();  // Close the form window after adding
    }

    // Method to close the form
    public void closeForm() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}