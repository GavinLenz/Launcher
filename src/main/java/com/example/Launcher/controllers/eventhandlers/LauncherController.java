package com.example.Launcher.controllers.eventhandlers;

import com.example.Launcher.models.Toon;
import com.example.Launcher.models.manager.ToonListManager;

import java.util.List;

public class LauncherController {

    // Reference the Singleton instance of ToonListManager
    private ToonListManager toonListManager;

    // Constructor
    public LauncherController() {
        // Get the Singleton instance instead of creating a new instance
        this.toonListManager = ToonListManager.getInstance();
    }

    // placeholder method to launch toons
    public void launchToons(List<Toon> toons) {
        for (Toon toon : toons) {
            toon.launch();  // Launch the toon (assuming you have a launch method in the Toon class)
        }
    }

    // Load the toons
    public List<Toon> loadToons() {
        return toonListManager.getToons();  // Load the saved toons
    }

    // Add a toon
    public void addToon(Toon toon) {
        toonListManager.addToon(toon);  // Add a new toon and save it
    }

    // Remove a toon
    public void removeToon(Toon toon) {
        toonListManager.removeToon(toon);  // Remove toon and save the updated list
    }
}