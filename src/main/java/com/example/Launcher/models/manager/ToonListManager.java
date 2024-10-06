package com.example.Launcher.models.manager;

import com.example.Launcher.models.Toon;
import java.util.List;

public class ToonListManager {
    private List<Toon> toons;
    private final ToonFileManager fileManager = new ToonFileManager();

    public ToonListManager() {
        this.toons = fileManager.loadToonsFromFile();
    }

    // Getter method to retrieve the list of toons
    public List<Toon> getToons() {
        return toons;
    }

    // Method to add a toon and save to the file
    public void addToon(Toon toon) {
        toons.add(toon);
        fileManager.saveToonsToFile(toons);
    }

    // Method to remove a toon and save the updated list
    public void removeToon(Toon toon) {
        toons.remove(toon);
        fileManager.saveToonsToFile(toons);
    }
}