package com.example.Launcher;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;

public class LauncherController {

    @FXML
    private Button playButton;

    @FXML
    private Button addToon;

    @FXML
    private MenuButton groupButton;

    @FXML
    private MenuButton optionsButton;

    @FXML
    private Button endSelectedButton;

    @FXML
    private Button unselectAllButton;

    @FXML
    private Button endAllButton;


    @FXML
    protected void playButton(ActionEvent event) {
        System.out.println("Play Button");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("error.fxml"));
            Parent formRoot = loader.load();

            Stage formStage = new Stage();
            formStage.setTitle("Error");

            Scene scene = new Scene(formRoot);
            formStage.setScene(scene);

            formStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    protected void addToonButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("form.fxml"));
            Parent formRoot = loader.load();

            Stage formStage = new Stage();
            formStage.setTitle("Add Toon");

            Scene scene = new Scene(formRoot);
            formStage.setScene(scene);

            formStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void groupButton(ActionEvent event) {
        System.out.println("Group Button");
    }

    @FXML
    protected void optionsButton(ActionEvent event) {
        System.out.println("Options Button");
    }

    @FXML
    protected void endSelectedButton(ActionEvent event) {
        System.out.println("End Selected Button");
    }

    @FXML
    protected void unselectAllButton(ActionEvent event) {
        System.out.println("Unselect All Button");
    }

    @FXML
    protected void endAllButton(ActionEvent event) {
        System.out.println("End All Button");
    }
}