package com.example.ToontownLauncher.patterns.command;

import com.example.ToontownLauncher.models.Toon;
import com.example.ToontownLauncher.models.manager.ToonListManager;

public class UpdateToonCommand implements Command {

    private final Toon toon;
    ToonListManager toonManager = ToonListManager.getInstance();


    public UpdateToonCommand(Toon toon) {
        this.toon = toon;
    }

    @Override
    public void execute() {
        toonManager.updateToon(toon);
    }
}
