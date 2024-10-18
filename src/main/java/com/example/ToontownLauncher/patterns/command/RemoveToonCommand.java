package com.example.ToontownLauncher.patterns.command;

import com.example.ToontownLauncher.models.Toon;
import com.example.ToontownLauncher.models.manager.ToonListManager;

public class RemoveToonCommand implements Command {

    private final Toon toon;
    ToonListManager toonManager = ToonListManager.getInstance();


    public RemoveToonCommand(Toon toon) {
        this.toon = toon;
    }

    @Override
    public void execute() {
        toonManager.removeToon(toon);
    }
}
