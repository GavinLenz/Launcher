package com.example.Launcher.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import com.example.Launcher.models.Toon;
import com.example.Launcher.models.manager.ToonListManager;
import com.example.Launcher.utils.ui.ToonListInitializer;
import com.example.Launcher.controllers.eventhandlers.ToonEventHandlers;

public class DisplayController {

    @FXML
    private ListView<Toon> toonsListView;  // Bind this to your FXML
<<<<<<< Updated upstream
    private ToonManager toonManager;
    private UIInitializer uiInitializer;
    private EventHandlers eventHandlers;
=======

    private ToonListManager toonManager;
    private ToonListInitializer uiInitializer;
    private ToonEventHandlers eventHandlers;
    private Stage primaryStage;
>>>>>>> Stashed changes

    @FXML
    public void initialize() {
        // Initialize ToonListManager and UIInitializer
        toonManager = new ToonListManager();
        uiInitializer = new ToonListInitializer(toonsListView, toonManager);

        // Initialize EventHandlers
        eventHandlers = new ToonEventHandlers(toonManager, toonsListView, this, uiInitializer);

        // Ensure EventHandlers are initialized properly
        if (eventHandlers == null) {
            System.out.println("Error: EventHandlers is null.");
        }
    }

<<<<<<< Updated upstream
    // public void setPrimaryStage(Stage primaryStage) {
    //     this.primaryStage = primaryStage;
    // }

    @FXML
    void addToonClicked() {
        if (eventHandlers != null) {
            eventHandlers.addToonHandler();  // Make sure eventHandlers is not null
=======
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        // Pass primaryStage to EventHandlers after setting it
        eventHandlers.setPrimaryStage(primaryStage);  // Ensure primaryStage is passed properly
    }

    @FXML
    public void openAddToonForm() {
        if (eventHandlers != null) {
            eventHandlers.openAddToonForm();
>>>>>>> Stashed changes
        } else {
            System.out.println("EventHandlers is null!");
        }
    }

    @FXML
<<<<<<< Updated upstream
    void playClicked() {
        if (eventHandlers != null) {
            eventHandlers.playClickHandler();  // not sure
=======
    public void playSelectedToon() {
        if (eventHandlers != null) {
            eventHandlers.playSelectedToon(primaryStage);
>>>>>>> Stashed changes
        } else {
            System.out.println("EventHandlers is null!");
        }
    }

    public void addToon(Toon toon) {
        toonManager.addToon(toon);
        uiInitializer.updateToonList();  // Update the UI to reflect new toon
    }
}