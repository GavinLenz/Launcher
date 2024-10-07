package com.example.Launcher.utils.launcher;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder;
import java.util.Map;


public class Launcher {
    private String gameserver;
    private String cookie;
    private String manifest;
    private String path;

    private Launcher (String gameserver, String cookie, String manifest) {
        this.gameserver = gameserver;
        this.cookie = cookie;
        this.manifest = manifest;

        // get path from config file instead
        path = "C:/Program Files (x86)/Toontown Rewritten/";
    }

    public static void startLaunch(String gameserver, String cookie, String manifest) {
        Launcher launchAttempt = new Launcher(gameserver, cookie, manifest);
        launchAttempt.launchGame();
    }

    private void launchGame() {
        ProcessBuilder pb = new ProcessBuilder(path + "TTREngine64.exe");

        // IMPORTANT - path cannot have the executable file included
        // sets the working directory (also where start() will run)
        pb.directory(new File(path));

        // clears current environment variables and adds gameserver and cookie
        Map<String, String> env = pb.environment();
        env.clear();
        env.put("TTR_GAMESERVER", gameserver);
        env.put("TTR_PLAYCOOKIE", cookie);
        
        // executes the command
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
