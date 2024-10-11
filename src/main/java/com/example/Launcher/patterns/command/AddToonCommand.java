package com.example.Launcher.patterns.command;

import com.example.Launcher.models.Toon;
import com.example.Launcher.models.manager.ToonListManager;

public class AddToonCommand implements Command {

    private final Toon toon;
    ToonListManager toonManager = ToonListManager.getInstance();

    public AddToonCommand(Toon toon) {
        this.toon = toon;
    }

    @Override
    public void execute() {
        // Directly access the Singleton instance
        toonManager.addToon(toon);
    }
}