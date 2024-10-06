package com.example.Launcher.utils.game;

import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class GameLauncher {

    private static String path;
    private final String gameserver;
    private final String cookie;
    private static String username;
    private static String password;

    public GameLauncher(String path, String gameserver, String cookie, String username, String password) {
        GameLauncher.path = path;
        this.gameserver = gameserver;
        this.cookie = cookie;
        GameLauncher.username = username;
        GameLauncher.password = password;
    }

    public static void launchGame(Stage primaryStage, String gameserver, String cookie) {
        ProcessBuilder pb = new ProcessBuilder(path);
        pb.directory(new File(path));

        Map<String, String> env = pb.environment();
        env.clear();
        env.put("TTR_GAMESERVER", gameserver);
        env.put("TTR_PLAYCOOKIE", cookie);
        env.put("TTR_USERNAME", username);
        env.put("TTR_PASSWORD", password);

        try {
            pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}