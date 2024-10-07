package com.example.Launcher.controllers.forms;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.example.Launcher.models.Toon;
import com.example.Launcher.controllers.DisplayController;
import com.example.Launcher.models.manager.ToonListManager;

public class ToonFormController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    private DisplayController displayController;
    private Stage formStage;

    // Reference to Singleton ToonListManager
    private ToonListManager toonManager = ToonListManager.getInstance();

    // Setter for DisplayController to communicate back
    public void setDisplayController(DisplayController displayController) {
        this.displayController = displayController;
    }

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

            // Add the Toon to the Singleton ToonListManager and update the list
            toonManager.addToon(newToon);

            // Ensure the displayController updates the UI list
            if (displayController != null) {
                displayController.addToon(newToon);
            }

            // Close the form
            formStage.close();
        } else {
            // Show error message (can add an alert here)
            System.out.println("Please enter valid Toon details.");
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