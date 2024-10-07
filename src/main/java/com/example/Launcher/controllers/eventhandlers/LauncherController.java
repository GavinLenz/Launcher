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

    // Method to launch toons using the strategy pattern
    public void launchToons(List<Toon> toons) {
        for (Toon toon : toons) {
            toon.play();  // Call the play method, which uses the current play strategy
        }
    }
}