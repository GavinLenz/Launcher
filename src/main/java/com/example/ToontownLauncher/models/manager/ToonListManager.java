package com.example.ToontownLauncher.models.manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.stream.Collectors;

import com.example.ToontownLauncher.models.Toon;

public class ToonListManager {

    // Singleton instance
    private static ToonListManager instance;

    // ObservableList to track and update UI
    private ObservableList<Toon> toons;

    // File manager for I/O
    private ToonFileManager fileManager;

    // Constructor for Singleton
    private ToonListManager() {
        this.fileManager = new ToonFileManager();
        List<Toon> loadedToons = fileManager.loadToonsFromFile();  // Load from file
        toons = FXCollections.observableArrayList(loadedToons);    // Wrap in ObservableList
    }

    // Thread-safe Singleton instance getter
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
            if (toon.getName().equals(updatedToon.getName())) {  // Assuming name is unique
                toon.setUsername(updatedToon.getUsername());
                toon.setPassword(updatedToon.getPassword());
                saveToons();
                return;
            }
        }
        System.out.println("Toon not found: " + updatedToon.getName());
    }

    // Return selected toons (assuming Toon has isSelected() method)
    public List<Toon> getSelectedToons() {
        return toons.stream()
                .filter(Toon::isSelected)
                .collect(Collectors.toList());
    }

    private void saveToons() {
        fileManager.saveToonsToFile(toons);
    }
}