package com.example.Launcher.patterns.factory;

import com.example.Launcher.models.Toon;

public class ToonFactory {

    public static Toon createToon(String name, String username, String password) {
        return new Toon(name, username, password);
    }
}
