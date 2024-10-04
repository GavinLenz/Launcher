package com.example.Launcher.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import com.example.Launcher.models.Toon;
import com.example.Launcher.ToonManager;
import com.example.Launcher.UIInitializer;
import com.example.Launcher.models.ToonWithCheckBox;

import java.util.Objects;

public class DisplayController {

    @FXML
    private ListView<ToonWithCheckBox> toonsListView;

    private ToonManager toonManager = new ToonManager();
    private UIInitializer uiInitializer;
    private EventHandlers eventHandlers;

    @FXML
    public void initialize() {
        uiInitializer = new UIInitializer(toonsListView, toonManager);
        eventHandlers = new EventHandlers(toonManager, toonsListView, this);
        toonsListView.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/Launcher/styles.css")).toExternalForm());
    }

    public void addToon(Toon toon) {
        toonManager.addToon(toon);
        uiInitializer.updateToonList();
    }

    @FXML
    private void openAddToonForm() {
        eventHandlers.openAddToonForm();
    }

    @FXML
    private void playSelectedToon() {
        eventHandlers.playSelectedToon();
    }
}