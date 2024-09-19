package com.example.Launcher;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FormController {

    @FXML
    private Button saveButton;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label passwordLabel;
    @FXML
    private TextField usernameField;
    @FXML
    private Label usernameLabel;
    @FXML
    private TextField toonNameField;
    @FXML
    private Label toonNameLabel;


    @FXML
    protected void submitForm(ActionEvent event) throws IOException {
        try {
            String name = toonNameField.getText();
            String username = usernameField.getText();
            String password = passwordField.getText();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("user_data.txt", true))) {
                writer.write("Toon Name: " + name + "\n");
                writer.write("Username: " + username + "\n");
                writer.write("Password: " + password + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (RuntimeException e) {
            System.err.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        toonNameField.clear();
        usernameField.clear();
        passwordField.clear();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}
