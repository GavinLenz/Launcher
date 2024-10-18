package com.example.ToontownLauncher.models.manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.stream.Collectors;

import com.example.ToontownLauncher.models.Toon;

public class ToonListManager {

    private static ToonListManager instance;
    private final ObservableList<Toon> toons;
    private final ToonFileManager fileManager;


    private ToonListManager() {
        this.fileManager = new ToonFileManager();
        List<Toon> loadedToons = fileManager.loadToonsFromFile();
        toons = FXCollections.observableArrayList(loadedToons);
    }

    public static ToonListManager getInstance() {
        if (instance == null) {
            synchronized (ToonListManager.class) {
                if (instance == null) {
                    instance = new ToonListManager();
                }
            }
        }
        return instance;
    }

    public ObservableList<Toon> getToons() {
        return toons;
    }

    public void addToon(Toon toon) {
        toons.add(toon);
        saveToons();
    }

    public void removeToon(Toon toon) {
        toons.remove(toon);
        saveToons();
    }

    public void updateToon(Toon updatedToon) {
        for (Toon toon : toons) {
            if (toon.getName().equals(updatedToon.getName())) {
                toon.setUsername(updatedToon.getUsername());
                toon.setPassword(updatedToon.getPassword());
                saveToons();
                return;
            }
        }
        System.out.println("Toon not found: " + updatedToon.getName());
    }

    public List<Toon> getSelectedToons() {
        return toons.stream()
                .filter(Toon::isSelected)
                .collect(Collectors.toList());
    }

    private void saveToons() {
        fileManager.saveToonsToFile(toons);
    }
}