package com.example.Launcher.models.manager;

import com.example.Launcher.models.Toon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ToonListManager {
    private ObservableList<Toon> toons = FXCollections.observableArrayList();

    public ObservableList<Toon> getToons() {
        return toons;
    }

    public void addToon(Toon toon) {
        toons.add(toon);
    }

    public void removeToon(Toon toon) {
        toons.remove(toon);
    }
}