package com.example.ToontownLauncher.utils.ui;

import javafx.scene.control.Alert;

public class AlertManager {

    public static void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.show();
    }
}