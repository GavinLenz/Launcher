package com.example.ToontownLauncher.models;

public class Toon {

    private String name;
    private String username;
    private String password;
    private boolean selected;  // To track selection status in UI

    public Toon(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.selected = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "Toon{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", selected=" + selected +
                '}';
    }
}