package com.example.Launcher.controllers.eventhandlers;

import com.example.Launcher.controllers.DisplayController;
import com.example.Launcher.models.Toon;
import com.example.Launcher.patterns.command.Command;
import com.example.Launcher.patterns.command.RemoveToonCommand;
import com.example.Launcher.patterns.command.UpdateToonCommand;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditToonController {

    @FXML
    private TextField lockedNameField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;

    private DisplayController displayController;
    private Toon originalToon;

    public void setDisplayController(DisplayController displayController, Toon toon) {
        this.displayController = displayController;
        this.originalToon = toon;

        // Populate the fields with the toon's existing data
        lockedNameField.setText(toon.getName());
        usernameField.setText(toon.getUsername());
        passwordField.setText(toon.getPassword());

        // Disable the name field to prevent editing
        lockedNameField.setDisable(true);
    }

    @FXML
    public void updateToon() {
        String name = lockedNameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Ensure no field is empty before proceeding
        if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
            System.out.println("All fields must be filled.");
            return;
        }

        Toon newToon = new Toon(name, username, password);

        // Use the Command Pattern to add the Toon (No need to pass ToonListManager now)
        Command updateToonCommand = new UpdateToonCommand(newToon);
        updateToonCommand.execute();

        closeForm();
    }

    @FXML
    public void removeToon() {

        // Use the Command Pattern to delete the Toon (No need to pass ToonListManager now)
        Command removeToonCommand = new RemoveToonCommand(originalToon);
        removeToonCommand.execute();

        closeForm();
    }

    @FXML
    public void closeForm() {
        Stage stage = (Stage) lockedNameField.getScene().getWindow();
        stage.close();
    }
}