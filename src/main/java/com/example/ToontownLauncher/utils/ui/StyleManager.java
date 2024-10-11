package com.example.ToontownLauncher.utils.ui;

import javafx.scene.Scene;

public class StyleManager {

    public void applyStylesheet(Scene scene, String stylesheetPath) {
        scene.getStylesheets().add(getClass().getResource(stylesheetPath).toExternalForm());
    }
}