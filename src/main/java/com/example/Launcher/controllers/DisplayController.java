package com.example.Launcher.controllers;

import com.example.Launcher.controllers.eventhandlers.FormEventHandlers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.stage.Stage;
import com.example.Launcher.models.Toon;
import com.example.Launcher.models.manager.ToonListManager;
import com.example.Launcher.utils.ui.ToonListInitializer;
import com.example.Launcher.controllers.eventhandlers.ToonEventHandlers;
import javafx.util.Callback;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;

import java.io.IOException;

public class DisplayController {

    @FXML
    private ListView<Toon> toonsListView;  // Bind this to your FXML

    @FXML
    private Button playSelectedButton; // Bind the play button to the FXML

    private ToonListManager toonManager;
    private ToonListInitializer uiInitializer;
    private ToonEventHandlers eventHandlers;
    private Stage primaryStage;

    @FXML
    public void initialize() {
        // Initialize ToonListManager and UIInitializer
        toonManager = new ToonListManager();
        uiInitializer = new ToonListInitializer(toonsListView, toonManager);

        // Initialize EventHandlers
        eventHandlers = new ToonEventHandlers(toonManager, toonsListView, this, uiInitializer);

        // Set up the ListView with a checkbox and display only the name
        setupToonListView();
    }

    private void setupToonListView() {
        // Use a CheckBoxListCell to display a checkbox with the toon name
        toonsListView.setCellFactory(CheckBoxListCell.forListView(new Callback<Toon, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(Toon toon) {
                SimpleBooleanProperty selectedProperty = new SimpleBooleanProperty(toon.isSelected());
                selectedProperty.addListener((obs, wasSelected, isNowSelected) -> toon.setSelected(isNowSelected));
                return selectedProperty;
            }
        }));

        // Set a custom cell factory to display only the name of the toon
        toonsListView.setCellFactory(listView -> new CheckBoxListCell<>(new Callback<Toon, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(Toon toon) {
                return new SimpleBooleanProperty(toon.isSelected());
            }
        }) {
            @Override
            public void updateItem(Toon toon, boolean empty) {
                super.updateItem(toon, empty);
                if (toon != null && !empty) {
                    setText(toon.getName());  // Display only the name of the toon
                } else {
                    setText(null);  // Clear text for empty cells
                }
            }
        });
    }

    // Set the primary stage and pass it to EventHandlers
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        if (eventHandlers != null) {
            eventHandlers.setPrimaryStage(primaryStage);
        }
    }

    // Event handler for adding a toon
    @FXML
    public void openAddToonForm() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/Launcher/Form.fxml"));
            Parent root = loader.load();

            // Ensure the controller is instantiated correctly
            FormEventHandlers formController = loader.getController();
            if (formController == null) {
                throw new IllegalStateException("FormEventHandlers controller is null.");
            }

            // Pass the DisplayController reference to FormEventHandlers
            formController.setDisplayController(this); // Pass this DisplayController instance

            // Create a new stage for the form
            Stage stage = new Stage();
            stage.setTitle("Add Toon");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            System.out.println("Error in loading FXML or controller: " + e.getMessage());
        }
    }

    @FXML
    public void playSelectedToon() {
        // Debugging: Print out all toons and their selection status
        toonManager.getToons().forEach(toon -> {
            System.out.println("Toon: " + toon.getName() + " - Selected: " + toon.isSelected());
        });

        // Now filter and act on the selected toons
        toonManager.getToons().stream()
                .filter(Toon::isSelected)
                .forEach(toon -> System.out.println("Playing toon: " + toon.getName()));
    }

    @FXML
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
            toonManager.addToon(toon);
            uiInitializer.updateToonList();
        } else {
            System.out.println("Error: Toon object is null.");
        }
    }
}