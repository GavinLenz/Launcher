package com.example.Launcher.patterns.strategy;

import com.example.Launcher.models.Toon;
import com.example.Launcher.utils.Login; //CHANGE BACK TO com.example.Launcher.utils.Login

public class DefaultPlayStrategy implements PlayStrategy {

    @Override
    public void play(Toon toon) {
        System.out.println("Playing toon: " + toon.getName());
        // Add actual logic for launching the toon

        Login.startLogin(toon.getUsername(), toon.getPassword());
    }
}
