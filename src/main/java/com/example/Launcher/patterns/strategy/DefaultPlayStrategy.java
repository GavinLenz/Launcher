package com.example.Launcher.patterns.strategy;

import com.example.Launcher.models.Toon;

public class DefaultPlayStrategy implements PlayStrategy {
    @Override
    public void play(Toon toon) {
        System.out.println("Playing toon: " + toon.getName());
        // Add actual logic for launching the toon
    }
}
