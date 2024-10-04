package com.example.Launcher.utils;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

public class PathPrompt {

    public static String promptUserForPath(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Toontown Rewritten Executable");

        if (OSUtils.isWindows()) {
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Executable Files", "*.exe"));
        } else if (OSUtils.isMac()) {
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Applications", "*.app"));
        }

        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());  // Debugging: Check if the file is selected
            if (PathFinder.isValidPath(selectedFile.getAbsolutePath())) {
                ConfigManager.writeConfig(selectedFile.getAbsolutePath());
                return selectedFile.getAbsolutePath();
            }
        }
        return null;
    }
}
