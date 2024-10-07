package com.example.Launcher.controllers;

import com.example.Launcher.controllers.eventhandlers.FormEventHandlers;
import com.example.Launcher.models.Toon;
import com.example.Launcher.models.manager.ToonListManager;
import com.example.Launcher.utils.ui.ToonListInitializer;
import com.example.Launcher.controllers.eventhandlers.ToonEventHandlers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;

import java.io.IOException;

public class DisplayController {

    @FXML
    private ListView<Toon> toonsListView;  // Bind this to your FXML

    @FXML
    private Button playSelectedButton; // Bind the play button to the FXML

    private ToonListInitializer uiInitializer;
    private ToonEventHandlers eventHandlers;
    private Stage primaryStage;  // Variable to hold the primary stage reference

    @FXML
    // Initialize the controller
    public void initialize() {
        // Get the singleton instance of ToonListManager
        ToonListManager toonManager = ToonListManager.getInstance();

        // Initialize UI initializer with the singleton manager
        uiInitializer = new ToonListInitializer(toonsListView, toonManager);

        // Initialize EventHandlers
        eventHandlers = new ToonEventHandlers(toonManager, toonsListView, this, uiInitializer);

        // Set up the ListView with a checkbox and display only the name
        setupToonListView();
    }

    // Set the primary stage
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        if (eventHandlers != null) {
            eventHandlers.setPrimaryStage(primaryStage);  // Pass primaryStage to event handlers
        }
    }

    // Set up the ListView with a checkbox and display only the name
    private void setupToonListView() {
        toonsListView.setCellFactory(listView -> new CheckBoxListCell<>(new Callback<Toon, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(Toon toon) {
                SimpleBooleanProperty selectedProperty = new SimpleBooleanProperty(toon.isSelected());
                selectedProperty.addListener((obs, wasSelected, isNowSelected) -> {
                    toon.setSelected(isNowSelected); // Update selection state in the Toon object
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
// Open the Add Toon form
    public void openAddToonForm() {
        try {
            // Load the FXML file and get the root node of the form
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/Launcher/Form.fxml"));
            Parent root = loader.load();

            // Get the controller for Form.fxml
            FormEventHandlers formController = loader.getController();
            if (formController == null) {
                throw new IllegalStateException("FormEventHandlers controller is null.");
            }

            // IMPORTANT: Set the DisplayController reference to FormEventHandlers
            formController.setDisplayController(this); // Pass the main DisplayController instance

            // Create a new stage (window) for the form
            Stage stage = new Stage();
            stage.setTitle("Add Toon");
            stage.setScene(new Scene(root));

            // Only after setting the DisplayController reference, show the form to the user
            stage.showAndWait(); // This pauses execution until the form is closed
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            System.out.println("Error in loading FXML or controller: " + e.getMessage());
        }
    }

    @FXML
    // Play the selected toons
    public void playSelectedToon() {
        ToonListManager toonManager = ToonListManager.getInstance(); // Use singleton instance

        // Now filter and act on the selected toons
        toonManager.getSelectedToons().forEach(toon -> {
            System.out.println("Playing toon: " + toon.getName());
            // Add code here to handle playing the selected toons (e.g., launch the game)
        });
    }

    @FXML
    // Add a toon when the Add Toon button is clicked
    public void addToonClicked() {
        if (eventHandlers != null) {
            eventHandlers.addToonHandler();  // Calls the appropriate handler from ToonEventHandlers
        } else {
            System.out.println("EventHandlers is null!");
        }
    }

    // Method to add a toon and update the UI
    public void addToon(Toon toon) {
        if (toon != null) {
            ToonListManager.getInstance().addToon(toon); // Use singleton to add toon
            uiInitializer.updateToonList();
        } else {
            System.out.println("Error: Toon object is null.");
        }
    }
}