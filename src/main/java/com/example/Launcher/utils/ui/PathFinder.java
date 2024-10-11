package com.example.Launcher.utils.ui;

import java.io.File;

public class PathFinder {

    public static boolean isValidPath(String path) {
        File file = new File(path);
        return file.exists() && file.isDirectory();
    }

    public static String autoDetectPath() {
        String[] possiblePaths = {
                "C:/Program Files (x86)/Toontown Rewritten/",
                "C:/Program Files/Toontown Rewritten/",
                "C:/Program Files (x86)/Disney/Disney Online/Toontown Rewritten/",
                "C:/Program Files/Disney/Disney Online/Toontown Rewritten/",
                "C:/Users/Public/Desktop/Toontown Rewritten/"
        };

        for (String path : possiblePaths) {
            if (isValidPath(path)) {
                return path;
            }
        }

        return null;
    }
}