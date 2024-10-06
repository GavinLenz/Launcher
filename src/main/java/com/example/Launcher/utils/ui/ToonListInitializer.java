package com.example.Launcher.utils.ui;

import com.example.Launcher.models.Toon;
import com.example.Launcher.models.manager.ToonListManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class ToonListInitializer {

    private ListView<Toon> toonsListView;
    private ToonListManager toonListManager;

    // Constructor to initialize the ToonListInitializer
    public ToonListInitializer(ListView<Toon> toonsListView, ToonListManager toonListManager) {
        this.toonsListView = toonsListView;
        this.toonListManager = toonListManager;
        updateToonList();
    }

    // Method to update the ListView with the current Toon list
    public void updateToonList() {
        toonsListView.setItems(FXCollections.observableArrayList(toonListManager.getToons()));
    }

    // Method to get the selected Toons from the ListView
    public Toon getSelectedToon() {
        return toonsListView.getSelectionModel().getSelectedItem();
    }
}