package com.example.Launcher;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ErrorController {

    @FXML
    private Text errotText;

    @FXML
    private Button errorExitButton;


    @FXML
    protected void errorText(ActionEvent event) {
        System.out.println("Something went wrong. Please try again.");
    }

    @FXML
    protected void errorExitButton(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}
