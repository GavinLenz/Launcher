package com.example.Launcher.controllers.eventhandlers;

import com.example.Launcher.models.Toon;
import com.example.Launcher.models.manager.ToonListManager;

import java.util.List;

public class LauncherController {

    // Create a ToonListManager object
    private ToonListManager toonListManager;

    public LauncherController() {
        this.toonListManager = new ToonListManager();
    }

    public List<Toon> loadToons() {
        return toonListManager.getToons();  // Load the saved toons
    }

    public void addToon(Toon toon) {
        toonListManager.addToon(toon);  // Add a new toon and save it
    }

    public void removeToon(Toon toon) {
        toonListManager.removeToon(toon);  // Remove toon and save the updated list
    }
}