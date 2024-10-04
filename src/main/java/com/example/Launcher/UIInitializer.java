package com.example.Launcher;

import com.example.Launcher.models.ToonWithCheckBox;
import com.example.Launcher.models.ToonWithCheckBoxCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class UIInitializer {

    private ListView<ToonWithCheckBox> toonsListView;
    private ToonManager toonManager;

    public UIInitializer(ListView<ToonWithCheckBox> toonsListView, ToonManager toonManager) {
        this.toonsListView = toonsListView;
        this.toonManager = toonManager;
        initialize();
    }

    public void initialize() {
        toonsListView.setCellFactory(param -> new ToonWithCheckBoxCell());
        updateToonList();
    }

    public void updateToonList() {
        ObservableList<ToonWithCheckBox> toons = FXCollections.observableArrayList(toonManager.getToonsWithCheckBoxes());
        toonsListView.setItems(toons);
    }
}