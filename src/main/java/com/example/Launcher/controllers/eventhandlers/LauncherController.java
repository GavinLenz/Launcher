package com.example.Launcher.controllers.eventhandlers;

import com.example.Launcher.models.Toon;
import com.example.Launcher.models.manager.ToonListManager;

import java.util.List;

public class LauncherController {

    // Method to launch toons using the strategy pattern
    public void launchToons(List<Toon> toons) {
        // Get Singleton instance directly inside the method (if needed)
        ToonListManager toonListManager = ToonListManager.getInstance();

        for (Toon toon : toons) {
            toon.play();  // Call the play method, which uses the current play strategy
        }
    }
}