package com.example.Launcher.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import com.example.Launcher.models.Toon;
import com.example.Launcher.ToonManager;
import com.example.Launcher.UIInitializer;
import javafx.stage.Stage;

public class DisplayController {

    @FXML
    private ListView<Toon> toonsListView;  // Bind this to your FXML
    private ToonManager toonManager;
    private UIInitializer uiInitializer;
    private EventHandlers eventHandlers;
    private Stage primaryStage;


    @FXML
    public void initialize() {
        toonManager = new ToonManager();  // Initialize ToonManager
        uiInitializer = new UIInitializer(toonsListView, toonManager);
        eventHandlers = new EventHandlers(toonManager, toonsListView, this, uiInitializer);
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    void openAddToonForm() {
        if (eventHandlers != null) {
            eventHandlers.openAddToonForm();  // Make sure eventHandlers is not null
        } else {
            System.out.println("EventHandlers is null!");
        }
    }
    @FXML
    void playSelectedToon() {
        if (eventHandlers != null) {
            eventHandlers.playSelectedToon(primaryStage);  // not sure
        } else {
            System.out.println("EventHandlers is null!");
        }
    }
    // 19. Add a new Toon to the list
    public void addToon(Toon toon) {
        toonManager.addToon(toon);
        uiInitializer.updateToonList();
    }
}