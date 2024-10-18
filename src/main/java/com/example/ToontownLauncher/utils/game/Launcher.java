package com.example.ToontownLauncher.utils.game;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder;
import java.util.Map;

public class Launcher {

    private final String gameServer;
    private final String cookie;
    private final String path;


    private Launcher(String gameServer, String cookie) {
        this.gameServer = gameServer;
        this.cookie = cookie;

        path = "C:\\Program Files (x86)\\Toontown Rewritten\\";
    }

    public static void startLaunch(String gameserver, String cookie) {
        Launcher launchAttempt = new Launcher(gameserver, cookie);
        launchAttempt.launchGame();
    }

    private void launchGame() {
        File executableFile = new File(path + "TTREngine64.exe");

        if (!executableFile.exists() || !executableFile.canExecute()) {
            System.err.println("Executable not found or not executable: " + executableFile.getAbsolutePath());
            return;
        }

        ProcessBuilder pb = new ProcessBuilder(path + "TTREngine64.exe");

        pb.directory(new File(path));

        Map<String, String> env = pb.environment();
        env.clear();
        env.put("TTR_GAMESERVER", gameServer);
        env.put("TTR_PLAYCOOKIE", cookie);

        try {
            pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}