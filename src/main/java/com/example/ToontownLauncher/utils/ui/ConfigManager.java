package com.example.ToontownLauncher.utils.ui;

import java.io.*;
import java.util.Properties;

public class ConfigManager {

    private static final String CONFIG_PATH = "config.properties";

    public static String readConfig() {
        Properties prop = new Properties();
        try (InputStream input = new FileInputStream(CONFIG_PATH)) {
            prop.load(input);
            return prop.getProperty("path");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeConfig(String path) {
        Properties prop = new Properties();
        try (OutputStream output = new FileOutputStream(CONFIG_PATH)) {
            prop.setProperty("path", path);
            prop.store(output, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}