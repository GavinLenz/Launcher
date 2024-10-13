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
    private ListView<Toon> toonsListView;  // Bind this to your FXML

    @FXML
    private Button playButton; // Ensure this is properly bound to the FXML with fx:id="playButton"

    private ToonListInitializer uiInitializer;      // For ListView management
    private Stage primaryStage;

    @FXML
    // Initialize the controller
    public void initialize() {
        ToonListManager toonManager = ToonListManager.getInstance(); // Get the singleton instance of ToonListManager
        uiInitializer = new ToonListInitializer(toonsListView, toonManager);  // Assign to class-level field

        // Set up the ListView with a checkbox and display the name
        setupToonListView();
    }

    // Set the primary stage reference, used for various UI operations
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    // Setup ListView to handle Toon selection with checkboxes
    private void setupToonListView() {
        toonsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);  // Enable multiple selection

        toonsListView.setCellFactory(listView -> new CheckBoxListCell<>(new Callback<Toon, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(Toon toon) {
                SimpleBooleanProperty selectedProperty = new SimpleBooleanProperty(toon.isSelected());
                selectedProperty.addListener((obs, wasSelected, isNowSelected) -> {
                    toon.setSelected(isNowSelected); // Update selection state in the Toon object

                    // Debug: Check if the selection state is updated correctly
                    System.out.println("Toon: " + toon.getName() + " is now " + (isNowSelected ? "selected" : "deselected"));
                });
                return selectedProperty;
            }
        }) {
            @Override
            public void updateItem(Toon toon, boolean empty) {
                super.updateItem(toon, empty);
                if (toon != null && !empty) {
                    setText(toon.getName());  // Display the name of the Toon

                    // Add a mouse double-click listener to the cell (but outside the checkbox area)
                    this.setOnMouseClicked(event -> {
                        if (event.getClickCount() == 2) {
                            // Double-click detected, open the edit pane or perform the required action
                            openEditToon(toon);
                        }
                    });
                } else {
                    setText(null);  // Clear text for empty cells
                }
            }
        });
    }

    @FXML
    // Method to open the Add Toon form (this was previously in ToonEventHandlers)
    public void openAddToon() {
        try {
            // Load the Add Toon form (AddToon.fxml)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ToontownLauncher/AddToon.fxml"));
            Parent root = loader.load();

            // Get the controller for AddToon.fxml
            AddToonController formController = loader.getController();

            // Set this DisplayController in FormEventHandlers (for communication)
            formController.setDisplayController(this);

            // Create a new stage (window) for the form
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL); // Set modality for the new window
            stage.setTitle("Add Toon");
            stage.setScene(new Scene(root));
            stage.setResizable(false);

            StyleManager styleManager = new StyleManager();
            URL stylesheetURL = getClass().getResource("/com/example/ToontownLauncher/styles.css");
            if (stylesheetURL != null) {
                styleManager.applyStylesheet(stage.getScene(), stylesheetURL.toExternalForm());
            } else {
                System.out.println("Stylesheet not found: /com/example/ToontownLauncher/styles.css");
            }

            stage.showAndWait();  // Wait for the form to close before resuming the calling method
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            System.out.println("Error in loading FXML or controller: " + e.getMessage());
        }
    }

    private void openEditToon(Toon toon) {
        try {
            // Load the Add Toon form (AddToon.fxml)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ToontownLauncher/EditToon.fxml"));
            Parent root = loader.load();

            // Get the controller for AddToon.fxml
            EditToonController formController = loader.getController();

            // Set this DisplayController in FormEventHandlers (for communication)
            formController.setDisplayController(this, toon);

            // Create a new stage (window) for the form
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL); // Set modality for the new window
            stage.setTitle("Edit Toon");
            stage.setScene(new Scene(root));
            stage.setResizable(false);

            StyleManager styleManager = new StyleManager();
            URL stylesheetURL = getClass().getResource("/com/example/ToontownLauncher/styles.css");
            if (stylesheetURL != null) {
                styleManager.applyStylesheet(stage.getScene(), stylesheetURL.toExternalForm());
            } else {
                System.out.println("Stylesheet not found: /com/example/ToontownLauncher/styles.css");
            }

            stage.showAndWait();  // Wait for the form to close before resuming the calling method
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            System.out.println("Error in loading FXML or controller: " + e.getMessage());
        }
    }

    @FXML
    // Method to play the selected toons when the "Play" button is pressed
    public void playClicked() {
        // Get the instance of ToonListManager
        ToonListManager toonManager = ToonListManager.getInstance();

        // Get the selected toons (ObservableList) directly from ToonListManager
        ObservableList<Toon> selectedToons = FXCollections.observableArrayList(toonManager.getSelectedToons());

        // Check if there are any selected toons
        if (!selectedToons.isEmpty()) {
            LauncherController.launchToons(selectedToons);
        } else {
            System.out.println("No toons selected.");
        }
    }
}