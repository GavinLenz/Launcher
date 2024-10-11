package com.example.ToontownLauncher.launcher;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder;
import java.util.Map;


public class Launcher {
    private String gameserver;
    private String cookie;
    private String path;

    private Launcher(String gameserver, String cookie) {
        this.gameserver = gameserver;
        this.cookie = cookie;

        // get path from config file instead
        path = "C:\\Program Files (x86)\\Toontown Rewritten\\";
    }

    public static void startLaunch(String gameserver, String cookie) {
        Launcher launchAttempt = new Launcher(gameserver, cookie);
        launchAttempt.launchGame();
    }

    private void launchGame() {
        File executableFile = new File(path, "TTREngine64.exe");

        // Check if the file exists and is executable
        if (!executableFile.exists() || !executableFile.canExecute()) {
            System.err.println("Executable not found or not executable: " + executableFile.getAbsolutePath());
            return;
        }

        ProcessBuilder pb = new ProcessBuilder(path + "TTREngine64.exe");

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
}