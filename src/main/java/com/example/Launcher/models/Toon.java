package com.example.Launcher.models;

import java.io.Serializable;

public class Toon implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String username;
    private String password;
    private boolean selected;  // Add this field for checkbox selection

    public Toon(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.selected = false;  // Default to false
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return name;  // Simplified for display
    }
}