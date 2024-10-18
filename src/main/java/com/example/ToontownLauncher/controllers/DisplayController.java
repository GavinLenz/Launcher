package com.example.ToontownLauncher.controllers;

import com.example.ToontownLauncher.controllers.eventhandlers.AddToonController;
import com.example.ToontownLauncher.controllers.eventhandlers.EditToonController;
import com.example.ToontownLauncher.models.Toon;
import com.example.ToontownLauncher.models.manager.ToonListManager;
import com.example.ToontownLauncher.utils.ui.StyleManager;
import com.example.ToontownLauncher.utils.ui.ToonListInitializer;
import com.example.ToontownLauncher.controllers.eventhandlers.LauncherController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;

import java.io.IOException;
import java.net.URL;

public class DisplayController {

    @FXML
    private ListView<Toon> toonsListView;
    @FXML
    private Button playButton;

    private final String CSS_PATH = "/com/example/ToontownLauncher/styles.css";
    private ToonListInitializer uiInitializer;
    private Stage primaryStage;


    @FXML
    public void initialize() {
        ToonListManager toonManager = ToonListManager.getInstance();
        uiInitializer = new ToonListInitializer(toonsListView, toonManager);

        setupToonListView();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private void setupToonListView() {
        toonsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        toonsListView.setCellFactory(listView -> new CheckBoxListCell<>(toon -> {
            SimpleBooleanProperty selectedProperty = new SimpleBooleanProperty(toon.isSelected());
            selectedProperty.addListener((obs, wasSelected, isNowSelected) -> toon.setSelected(isNowSelected));
            return selectedProperty;
        }) {
            @Override
            public void updateItem(Toon toon, boolean empty) {
                super.updateItem(toon, empty);
                if (toon != null && !empty) {
                    setText(toon.getName());

                    this.setOnMouseClicked(event -> {
                        if (event.getClickCount() == 2) {
                            openEditToon(toon);
                        }
                    });
                } else {
                    setText(null);
                }
            }
        });
    }

    @FXML
    public void openAddToon() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ToontownLauncher/AddToon.fxml"));
            Parent root = loader.load();

            AddToonController formController = loader.getController();

            formController.setDisplayController(this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add Toon");
            stage.setScene(new Scene(root));
            stage.setResizable(false);

            StyleManager styleManager = new StyleManager();
            URL stylesheetURL = getClass().getResource(CSS_PATH);
            if (stylesheetURL != null) {
                styleManager.applyStylesheet(stage.getScene(), stylesheetURL.toExternalForm());
            } else {
                System.out.println("Stylesheet not found: " + CSS_PATH);
            }

            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            System.out.println("Error in loading FXML or controller: " + e.getMessage());
        }
    }

    private void openEditToon(Toon toon) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ToontownLauncher/EditToon.fxml"));
            Parent root = loader.load();

            EditToonController formController = loader.getController();

            formController.setDisplayController(this, toon);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Edit Toon");
            stage.setScene(new Scene(root));
            stage.setResizable(false);

            StyleManager styleManager = new StyleManager();
            URL stylesheetURL = getClass().getResource(CSS_PATH);
            if (stylesheetURL != null) {
                styleManager.applyStylesheet(stage.getScene(), stylesheetURL.toExternalForm());
            } else {
                System.out.println("Stylesheet not found: " + CSS_PATH);
            }

            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            System.out.println("Error in loading FXML or controller: " + e.getMessage());
        }
    }

    @FXML
    public void playClicked() {
        ToonListManager toonManager = ToonListManager.getInstance();

        ObservableList<Toon> selectedToons = FXCollections.observableArrayList(toonManager.getSelectedToons());

        if (!selectedToons.isEmpty()) {
            LauncherController.launchToons(selectedToons);
        } else {
            System.out.println("No toons selected.");
        }
    }
}