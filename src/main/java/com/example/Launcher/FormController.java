package com.example.Launcher;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FormController {

    @FXML
    private Label nameLabel;

    @FXML
    private TextField nameField;

    @FXML
    private Label usernameLabel;

    @FXML
    private TextField usernameField;

    @FXML
    private Label passwordLabel;

    @FXML
    private PasswordField passwordField;



    @FXML
    private void submitForm(ActionEvent event) {
        // Get user input
        String name = nameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Create a User object
        Toon user = new Toon(name, username, password);

        // Save user data to a file
        String filename = "user_data.txt";
        user.saveToFile(filename);


        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}
