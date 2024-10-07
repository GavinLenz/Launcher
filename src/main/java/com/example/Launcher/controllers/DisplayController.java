package com.example.Launcher.controllers;

import com.example.Launcher.controllers.eventhandlers.FormEventHandlers;
import com.example.Launcher.controllers.eventhandlers.ToonEventHandlers;
import com.example.Launcher.models.Toon;
import com.example.Launcher.models.manager.ToonListManager;
import com.example.Launcher.utils.ui.ToonListInitializer;
import com.example.Launcher.controllers.eventhandlers.LauncherController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class DisplayController {

    @FXML
    private ListView<Toon> toonsListView;  // Bind this to your FXML

    @FXML
    private Button playButton; // Ensure this is properly bound to the FXML with fx:id="playButton"

    private LauncherController launcherController;  // Handles launching toons
    private ToonEventHandlers eventHandlers;        // Handles form and interaction events
    private ToonListInitializer uiInitializer;      // For ListView management
    private Stage primaryStage;  // Variable to hold the primary stage reference


    @FXML
    // Initialize the controller
    public void initialize() {
        // Get the singleton instance of ToonListManager
        ToonListManager toonManager = ToonListManager.getInstance();

        // Initialize UI initializer with the singleton manager
        uiInitializer = new ToonListInitializer(toonsListView, toonManager);  // Assign to class-level field

        // Initialize the LauncherController (for launching toons)
        launcherController = new LauncherController();  // Ensure launcherController is initialized

        // Set up the ListView with a checkbox and display only the name
        setupToonListView();
    }

    // Set the primary stage reference, used for various UI operations
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        if (eventHandlers != null) {
            eventHandlers.setPrimaryStage(primaryStage);  // Pass primaryStage to event handlers
        }
    }

    private void setupToonListView() {
        toonsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);  // Enable multiple selection

        toonsListView.setCellFactory(listView -> new CheckBoxListCell<>(new Callback<Toon, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(Toon toon) {
                SimpleBooleanProperty selectedProperty = new SimpleBooleanProperty(toon.isSelected());
                selectedProperty.addListener((obs, wasSelected, isNowSelected) -> {
                    toon.setSelected(isNowSelected); // Update selection state in the Toon object

                    // Add a debug print statement to check if the selection state is updated correctly
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
                } else {
                    setText(null);  // Clear text for empty cells
                }
            }
        });
    }

    @FXML
    // Method to play the selected toons when the "Play" button is pressed
    public void playClicked() {
        // Get all toons from the ToonListManager
        ToonListManager toonManager = ToonListManager.getInstance();
        List<Toon> allToons = toonManager.getToons();  // Ensure this returns the list of all available toons

        // Filter the toons that are selected via their checkbox (isSelected property)
        List<Toon> selectedToons = allToons.stream()
                .filter(Toon::isSelected)
                .collect(Collectors.toList());

        // Debug: Print the selected toons to the console
        selectedToons.forEach(toon -> System.out.println("Selected Toon: " + toon.getName() + " isSelected: " + toon.isSelected()));

        // Check if there are any selected toons
        if (!selectedToons.isEmpty()) {
            launcherController.launchToons(selectedToons);  // Use the launcher controller to play the selected toons
        } else {
            System.out.println("No toons selected.");
        }
    }

    // Method to open the Add Toon form
    @FXML
    public void addToonClicked() {
        try {
            // Load the Add Toon form (Form.fxml)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/Launcher/Form.fxml"));
            Parent root = loader.load();

            // Get the controller for Form.fxml
            FormEventHandlers formController = loader.getController();

            if (formController == null) {
                throw new IllegalStateException("FormEventHandlers controller is null.");
            }

            // Set this DisplayController in FormEventHandlers (for communication)
            formController.setDisplayController(this);

            // Create a new stage (window) for the form
            Stage stage = new Stage();
            stage.setTitle("Add Toon");
            stage.setScene(new Scene(root));
            stage.showAndWait();  // Wait for the form to close before resuming the calling method
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            System.out.println("Error in loading FXML or controller: " + e.getMessage());
        }
    }
}