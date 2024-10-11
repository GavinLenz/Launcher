package com.example.Launcher.utils.ui;

import javafx.scene.Scene;

public class StyleManager {

    public void applyStylesheet(Scene scene, String stylesheetPath) {
        scene.getStylesheets().add(stylesheetPath);  // Directly add the external form URL
    }
}