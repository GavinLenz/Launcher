package com.example.Launcher.utils;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder;
import java.util.Map;


public class Launcher {
    private String gameserver;
    private String cookie;
    private String manifest;
    private String path;

    private Launcher(String gameserver, String cookie, String manifest) {
        this.gameserver = gameserver;
        this.cookie = cookie;
        this.manifest = manifest;

        // get path from config file instead
        path = System.getProperty("user.home") + "/Library/Application Support/Toontown Rewritten/Toontown Rewritten.app/Contents/MacOS/";
    }

    public static void startLaunch(String gameserver, String cookie, String manifest) {
        Launcher launchAttempt = new Launcher(gameserver, cookie, manifest);
        launchAttempt.launchGame();
    }

    private void launchGame() {
        File executableFile = new File(path, "./TTREngine");

        // Check if the file exists and is executable
        if (!executableFile.exists() || !executableFile.canExecute()) {
            System.err.println("Executable not found or not executable: " + executableFile.getAbsolutePath());
            return;
        }

        ProcessBuilder pb = new ProcessBuilder("./TTREngine");

        // Set working directory without the executable in the path
        pb.directory(new File(path));

        // Clear environment variables and set the necessary ones
        Map<String, String> env = pb.environment();
        env.clear();
        env.put("TTR_GAMESERVER", gameserver);
        env.put("TTR_PLAYCOOKIE", cookie);

        try {
            pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getPath() {

        return null;
    }
}