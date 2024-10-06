package com.example.Launcher.utils.game;

import java.io.File;

public class PathFinder {

    public static boolean isValidPath(String path) {
        File file = new File(path);
        return file.exists() && file.isDirectory();
    }

    public static String autoDetectPath() {
        String defaultPath = "/Applications/ToontownLauncher"; // Example default path
        return isValidPath(defaultPath) ? defaultPath : null;
    }
}