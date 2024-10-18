package com.example.ToontownLauncher.patterns.command;

import com.example.ToontownLauncher.models.Toon;
import com.example.ToontownLauncher.models.manager.ToonListManager;

public class AddToonCommand implements Command {

    private final Toon toon;
    ToonListManager toonManager = ToonListManager.getInstance();


    public AddToonCommand(Toon toon) {
        this.toon = toon;
    }

    @Override
    public void execute() {
        toonManager.addToon(toon);
    }
}