package com.example.Launcher.models.manager;

import com.example.Launcher.models.Toon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.stream.Collectors;

public class ToonListManager {

    private static ToonListManager instance;  // Singleton instance
    private ObservableList<Toon> toons;  // ObservableList to track and update UI
    private final ToonFileManager fileManager;  // File manager for I/O

    // Private constructor for Singleton
    private ToonListManager() {
        fileManager = new ToonFileManager();
        List<Toon> loadedToons = fileManager.loadToonsFromFile();  // Load from file
        this.toons = FXCollections.observableArrayList(loadedToons);  // Wrap in ObservableList
    }

    // Get the singleton instance of ToonListManager
    public static ToonListManager getInstance() {
        if (instance == null) {
            instance = new ToonListManager();
        }
        return instance;
    }

    // Get the observable list of toons
    public ObservableList<Toon> getToons() {
        return toons;
    }

    // Add a toon and save to file
    public void addToon(Toon toon) {
        toons.add(toon);  // Add to the observable list
        fileManager.saveToonsToFile(toons);  // Save the updated list
    }

    // Remove a toon and save the updated list
    public void removeToon(Toon toon) {
        toons.remove(toon);  // Remove from the observable list
        fileManager.saveToonsToFile(toons);  // Save the updated list
    }

    // Return selected toons (assuming Toon has isSelected() method)
    public List<Toon> getSelectedToons() {
        return toons.stream()
                .filter(Toon::isSelected)  // Filter toons that are selected
                .collect(Collectors.toList());
    }
}