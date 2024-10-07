package com.example.Launcher.utils.ui;

import com.example.Launcher.models.Toon;
import com.example.Launcher.models.manager.ToonListManager;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.util.stream.Collectors;

public class ToonListInitializer {

    private ListView<Toon> toonsListView;
    private ToonListManager toonListManager;

    public ToonListInitializer(ListView<Toon> toonsListView, ToonListManager toonListManager) {
        this.toonsListView = toonsListView;
        this.toonListManager = toonListManager;

        // Allow multiple selections in the ListView
        toonsListView.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);

        // Bind the ListView to the observable list of toons
        updateToonList();
    }

    // Method to update the ListView with the current toon list
    public void updateToonList() {
        toonsListView.setItems(toonListManager.getToons());
    }

    // Method to get the selected toons from the ListView
    public ObservableList<Toon> getSelectedToons() {
        return toonsListView.getSelectionModel().getSelectedItems();
    }
}