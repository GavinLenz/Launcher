package com.example.Launcher;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private Button endSelected;
    @FXML
    private Button unselectedAll;
    @FXML
    private Button endAll;
    @FXML
    private ListView list; // Needs fixing


    @FXML
    protected void playButton() {
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
    protected void groupButton() {
    }

    @FXML
    protected void optionsButton() {
    }

    @FXML
    protected void endSelectedButton() {
    }

    @FXML
    protected void unselectAllButton() {
    }

    @FXML
    protected void endAllButton() {
    }

    @FXML
    protected void toonListView() {
    }
}