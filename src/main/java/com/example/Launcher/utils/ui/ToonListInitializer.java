package com.example.Launcher.utils.ui;

import com.example.Launcher.models.Toon;
import com.example.Launcher.models.manager.ToonListManager;
import javafx.scene.control.ListView;

public class ToonListInitializer {

    private ListView<Toon> toonsListView;
    private ToonListManager toonListManager;

    public ToonListInitializer(ListView<Toon> toonsListView, ToonListManager toonListManager) {
        this.toonsListView = toonsListView;
        this.toonListManager = toonListManager;
        initialize();
    }

    public void initialize() {
        toonsListView.setItems(toonListManager.getToons());
    }

    public void updateToonList() {
        toonsListView.setItems(toonListManager.getToons());
    }
}