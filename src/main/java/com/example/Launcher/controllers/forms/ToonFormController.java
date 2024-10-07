package com.example.Launcher.controllers.forms;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.example.Launcher.models.Toon;
import com.example.Launcher.models.manager.ToonListManager;
import com.example.Launcher.patterns.command.AddToonCommand;
import com.example.Launcher.patterns.command.Command;
import com.example.Launcher.utils.ui.AlertManager;

public class ToonFormController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    private Stage formStage;

    // Reference to Singleton ToonListManager
    private final ToonListManager toonManager = ToonListManager.getInstance();

    // Method to set the form's stage, to close it later if needed
    public void setFormStage(Stage formStage) {
        this.formStage = formStage;
    }

    @FXML
    // Called when the user clicks the "Save" button
    private void handleSave() {
        String name = nameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (validateInput(name, username, password)) {
            Toon newToon = new Toon(name, username, password);

            // Use the command pattern to add the Toon
            Command addCommand = new AddToonCommand(toonManager, newToon);
            addCommand.execute();  // This adds the toon to the Singleton ToonListManager

            // No need to directly interact with DisplayController as the ObservableList will auto-update

            // Close the form
            formStage.close();
        } else {
            // Show error message using an alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Input Validation Error");
            alert.setContentText("Please enter valid Toon details.");
            alert.showAndWait();
        }
    }

    // Called when the user clicks the "Cancel" button
    @FXML
    private void handleCancel() {
        formStage.close();  // Simply close the form
    }

    // Validate the input fields
    private boolean validateInput(String name, String username, String password) {
        return name != null && !name.isEmpty() &&
                username != null && !username.isEmpty() &&
                password != null && !password.isEmpty();
    }
}