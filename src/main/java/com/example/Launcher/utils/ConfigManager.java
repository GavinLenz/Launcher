package com.example.Launcher.utils;

import java.io.*;

public class ConfigManager {


    // Need to fix the creation on the config file
    private static final String CONFIG_FILE = "config/config.txt";

    public static String readConfig() {
        File configFile = new File(CONFIG_FILE);
        if (!configFile.exists()) {
            System.out.println("Config file does not exist.");
            return null;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("ttr_path=")) {
                    return line.split("=")[1];  // Return the path
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;  // Return null if no valid path is found
    }

    public static void writeConfig(String path) {
        File configFile = new File(CONFIG_FILE);
        try {
            File parentDir = configFile.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                boolean dirsCreated = parentDir.mkdirs();
                if (dirsCreated) {
                    System.out.println("Directory created: " + parentDir.getAbsolutePath());
                } else {
                    System.err.println("Failed to create directory: " + parentDir.getAbsolutePath());
                    return;  // Exit if directory creation fails
                }
            }

            // Write the configuration file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(configFile))) {
                writer.write("ttr_path=" + path);
                System.out.println("Config file written successfully: " + configFile.getAbsolutePath());
            }

        } catch (IOException e) {
            System.err.println("Failed to write config file.");
            e.printStackTrace();
        }
    }
}