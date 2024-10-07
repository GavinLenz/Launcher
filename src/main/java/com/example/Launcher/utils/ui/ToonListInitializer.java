package com.example.Launcher.utils.ui;

import com.example.Launcher.models.Toon;
import com.example.Launcher.models.manager.ToonListManager;
import javafx.collections.ListChangeListener;
import javafx.scene.control.ListView;

public class ToonListInitializer {

    private ListView<Toon> toonsListView;
    private ToonListManager toonListManager;

    public ToonListInitializer(ListView<Toon> toonsListView, ToonListManager toonListManager) {
        this.toonsListView = toonsListView;
        this.toonListManager = toonListManager;
        updateToonList();
        addObserver(); // Add an observer to the list
    }

    // Observer that reacts to changes in the ObservableList
    private void addObserver() {
        toonListManager.getToons().addListener((ListChangeListener<Toon>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    System.out.println("Toon was added: " + change.getAddedSubList());
                }
                if (change.wasRemoved()) {
                    System.out.println("Toon was removed: " + change.getRemoved());
                }
            }
            updateToonList(); // Update the UI when changes happen
        });
    }

    public void updateToonList() {
        toonsListView.setItems(toonListManager.getToons());
    }
}