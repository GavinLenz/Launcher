package com.example.Launcher.utils;

import javafx.concurrent.Task;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class GameLauncher {
    private String gameserver;
    private String cookie;
    private String manifest;
    private String path;

    public void launcher(String gameserver, String cookie, String manifest, Stage primaryStage) {
        this.gameserver = gameserver;
        this.cookie = cookie;
        this.manifest = manifest;

        // get path from config file instead
        path = "'/Applications/Toontown Launcher.app/Contents/MacOS/Toontown Launcher'";

        launchGame(primaryStage, gameserver, cookie);
    }

    public static void launchGame(Stage primaryStage, String gameserver, String cookie) {
        // Read the game path from the config file
        String path = ConfigManager.readConfig();

        // Check if the path is valid; if not, try to auto-detect
        if (!PathFinder.isValidPath(path)) {
            System.out.println("Invalid path, trying to auto-detect...");
            path = PathFinder.autoDetectPath();

            if (!PathFinder.isValidPath(path)) {
                System.out.println("Auto-detection failed, prompting user for path...");
                // Prompt user for path (could be a UI dialog)
                // this is if we cant figure it out

                // Save the path if it's valid
                if (path != null && PathFinder.isValidPath(path)) {
                    ConfigManager.writeConfig(path);
                } else {
                    System.out.println("No valid path provided.");
                    return;
                }
            } else {
                // Save the detected path
                ConfigManager.writeConfig(path);
            }
        }

        // If path is valid, proceed to launch the game
        if (path != null && PathFinder.isValidPath(path)) {
            System.out.println("Launching game from: " + path);

            ProcessBuilder pb = new ProcessBuilder(path);

            pb.directory(new File(path));

            // Clear and set the environment variables
            Map<String, String> env = pb.environment();
            env.clear();
            env.put("TTR_GAMESERVER", gameserver);
            env.put("TTR_PLAYCOOKIE", cookie);

            try {
                pb.start();
                System.out.println("Game launched successfully.");
            } catch (IOException e) {
                System.out.println("Failed to launch the game.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Failed to find a valid path to the TTR executable.");
        }
    }

    // Updated pathExecutable method with background task
    public static void pathExecutable(String path) {
        if (path == null || path.isEmpty()) {
            System.out.println("Path is null or empty.");
            return;
        }

        // Create a JavaFX Task to run the game launch in a background thread
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                try {
                    // Use the `open` command to launch the app bundle properly on macOS
                    String[] command = {"open", path, "--args", "--game", "--skip-launcher"};
                    Process process = Runtime.getRuntime().exec(command);

                    // Optionally capture output or error from the process
                    BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

                    String s;
                    while ((s = stdInput.readLine()) != null) {
                        System.out.println("Output: " + s);
                    }

                    while ((s = stdError.readLine()) != null) {
                        System.out.println("Error: " + s);
                    }

                    int exitCode = process.waitFor();  // Wait for the process to complete

                    if (exitCode == 0) {
                        System.out.println("Game launched successfully.");
                    } else {
                        System.out.println("Game failed to launch with exit code: " + exitCode);
                    }
                } catch (IOException | InterruptedException e) {
                    System.out.println("Failed to launch the game: " + e.getMessage());
                    e.printStackTrace();
                }
                return null;
            }
        };

        // Run the task in a background thread
        new Thread(task).start();

        // Optional: Add listeners to the task to track completion or failure
        task.setOnSucceeded(e -> {
            System.out.println("Background task completed successfully.");
        });

        task.setOnFailed(e -> {
            System.out.println("Background task failed.");
        });
    }
}