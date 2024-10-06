package com.example.Launcher.models;

import java.io.Serializable;

public class Toon implements Serializable {
    private final String name;
    private final String username;
    private final String password;

    public Toon(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    // Copy constructor (if needed)
    public Toon(Toon toon) {
        this.name = toon.name;
        this.username = toon.username;
        this.password = toon.password;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}