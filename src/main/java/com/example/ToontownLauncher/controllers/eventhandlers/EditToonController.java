package com.example.ToontownLauncher.controllers.eventhandlers;

import com.example.ToontownLauncher.controllers.DisplayController;
import com.example.ToontownLauncher.models.Toon;
import com.example.ToontownLauncher.patterns.command.Command;
import com.example.ToontownLauncher.patterns.command.RemoveToonCommand;
import com.example.ToontownLauncher.patterns.command.UpdateToonCommand;
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

        lockedNameField.setText(toon.getName());
        usernameField.setText(toon.getUsername());
        passwordField.setText(toon.getPassword());

        lockedNameField.setDisable(true);
    }

    @FXML
    public void updateToon() {
        String name = lockedNameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
            System.out.println("All fields must be filled.");
            return;
        }

        Toon newToon = new Toon(name, username, password);
        Command updateToonCommand = new UpdateToonCommand(newToon);
        updateToonCommand.execute();

        closeForm();
    }

    @FXML
    public void removeToon() {
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