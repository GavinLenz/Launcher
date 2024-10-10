package com.example.Launcher.utils.game;

import eu.hansolo.fx.heatmap.Launcher;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder;
import java.util.Map;

public class NewLauncher {
    private String gameserver;
    private String cookie;
    private String path;
    private boolean displayLogging;

    private static final String WIN32_BIN = "TTREngine";
    private static final String WIN64_BIN = "TTREngine64";
    private static final String LINUX_BIN = "TTREngine";
    private static final String DARWIN_BIN = "TTREngine";  // Adjusted to match your Mac binary

    // Constructor
    private NewLauncher(String gameserver, String cookie, boolean displayLogging) {
        this.gameserver = gameserver;
        this.cookie = cookie;
        this.displayLogging = displayLogging;

        // Set the path to the game's executable based on the platform
        this.path = determineGamePath();
    }

    // Main method to start the game launch process
    public static void startLaunch(String gameserver, String cookie, boolean displayLogging) {
        String patchManifestUrl = "https://www.toontownrewritten.com/manifest.json";  // Example URL
        String gameDirectory = LauncherHelper.getLauncherFilePath("").toString();  // Get the user-specific game directory

        // Initialize patcher and check for updates
        Patcher patcher = new Patcher();
        boolean isUpdated = patcher.checkUpdate(gameDirectory, patchManifestUrl);

        if (!isUpdated) {
            System.out.println("Failed to update game files. Aborting launch.");
            return;
        }

        // Proceed with game launch if updated
        NewLauncher launchAttempt = new NewLauncher(gameserver, cookie, displayLogging);
        launchAttempt.launchGame();
    }

    // Determine the correct path to the game binary based on the OS
    private String determineGamePath() {
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("mac")) {
            return LauncherHelper.getLauncherFilePath("").resolve(DARWIN_BIN).toString();  // Use user home directory
        } else if (osName.contains("win")) {
            return LauncherHelper.getLauncherFilePath("").resolve(
                    System.getProperty("os.arch").endsWith("64") ? WIN64_BIN : WIN32_BIN
            ).toString();
        } else if (osName.contains("linux")) {
            return LauncherHelper.getLauncherFilePath("").resolve(LINUX_BIN).toString();
        }
        return "";  // Default case, should not happen in practice
    }

    // Launch the game with the appropriate environment variables and options
    private void launchGame() {
        String osName = System.getProperty("os.name").toLowerCase();
        File processFile = new File(path);

        // Set executable permission on Unix-like systems
        if (!osName.contains("win")) {
            LauncherHelper.setExecutablePermission(processFile);
        }

        ProcessBuilder pb = new ProcessBuilder(path);
        pb.directory(new File(path).getParentFile());  // Set working directory to the parent of the binary

        // Set environment variables
        Map<String, String> env = pb.environment();
        env.clear();  // Clear any existing environment variables
        env.put("TTR_GAMESERVER", gameserver);
        env.put("TTR_PLAYCOOKIE", cookie);

        // Start the process
        try {
            if (displayLogging) {
                pb.inheritIO();  // Display logs in the console
                pb.start();
            } else {
                pb.redirectErrorStream(true);
                pb.redirectOutput(ProcessBuilder.Redirect.DISCARD);  // Suppress logs
                pb.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}