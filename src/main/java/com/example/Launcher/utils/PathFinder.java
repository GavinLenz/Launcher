package com.example.Launcher.utils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class PathFinder {

    public static String autoDetectPath() {
        if (OSUtils.isWindows()) {
            String[] commonPaths = { // Common paths for Windows, still need to check if the path is valid
                    "C:\\Program Files\\Toontown Rewritten\\TTR.exe",
                    "C:\\Program Files (x86)\\Toontown Rewritten\\TTR.exe"
            };
            for (String path : commonPaths) {
                if (isValidPath(path)) {
                    System.out.println("Found valid path: " + path);
                    return path;
                }
            }
        } else if (OSUtils.isMac()) {
            String[] commonPaths = {
                    "/Applications/Toontown Launcher.app/Contents/MacOS/Toontown Launcher"
            };
            for (String path : commonPaths) {
                System.out.println("Checking path: " + path);
                if (isValidPath(path)) {
                    System.out.println("Found valid path: " + path);
                    return path;
                } else {
                    System.out.println("Invalid path: " + path);
                }
            }
        }
        System.out.println("Auto-detection failed.");
        return null;
    }

    public static boolean isValidPath(String path) {
        if (path == null || path.isEmpty()) {
            return false;
        }

        if (path.endsWith(".app")) {
            path = path + "/Contents/MacOS/TTR";
        }

        return Files.exists(Paths.get(path));
    }

    public static void launchGame(String path) {
        if (path == null || path.isEmpty()) {
            System.out.println("Path is null or empty.");
            return;
        }

        try {
            String[] command = {"/bin/sh", "-c", path};
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            System.out.println("Failed to launch the game: " + e.getMessage());
        }
    }
}