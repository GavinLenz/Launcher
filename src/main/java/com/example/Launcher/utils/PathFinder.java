package com.example.Launcher.utils;

import java.io.File;

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
                } else {
                    System.out.println("Invalid path: " + path);
                }
            }
        } else if (OSUtils.isMac()) {
            String[] commonPaths = {
                    "/Users/gavinlenz/Library/Application Support/Toontown Rewritten/Toontown Rewritten.app/Contents/MacOS/TTREngine",
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
        /*} else if (OSUtils.isLinux()) {
            String[] commonPaths = {
                    "? ? ?"
            };
            for (String path : commonPaths) {
                System.out.println("Checking path: " + path);
                if (isValidPath(path)) {
                    System.out.println("Found valid path: " + path);
                    return path;
                } else {
                    System.out.println("Invalid path: " + path);
                }
            } */
        }
        System.out.println("Auto-detection failed.");
        return null;
    }

    public static boolean isValidPath(String path) {
        if (path == null || path.isEmpty()) {
            return false;
        }

        File file = new File(path);
        return file.exists() && file.isFile();
    }
}