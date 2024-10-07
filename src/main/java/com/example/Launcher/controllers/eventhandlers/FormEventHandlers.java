package com.example.Launcher.controllers.eventhandlers;

import com.example.Launcher.controllers.DisplayController;
import com.example.Launcher.models.Toon;
import com.example.Launcher.patterns.command.AddToonCommand;
import com.example.Launcher.patterns.command.Command;
import com.example.Launcher.models.manager.ToonListManager;
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

    public void setDisplayController(DisplayController displayController) {
        // No need to do anything here
    }

    @FXML
    // Called when the user clicks the "Save" button
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

        // Use the Command Pattern to add the Toon
        Command addToonCommand = new AddToonCommand(ToonListManager.getInstance(), newToon);
        addToonCommand.execute();  // Add the Toon to the ToonListManager

        closeForm();  // Close the form window after adding
    }

    public void closeForm() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}