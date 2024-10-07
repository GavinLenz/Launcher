package com.example.Launcher.patterns.command;

import com.example.Launcher.models.Toon;
import com.example.Launcher.models.manager.ToonListManager;

public class AddToonCommand implements Command {

    private final ToonListManager toonListManager;
    private final Toon toon;

    public AddToonCommand(ToonListManager toonListManager, Toon toon) {
        this.toonListManager = toonListManager;
        this.toon = toon;
    }

    @Override
    public void execute() {
        toonListManager.addToon(toon);
    }
}