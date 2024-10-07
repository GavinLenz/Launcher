package com.example.Launcher.models.manager;

import com.example.Launcher.models.Toon;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ToonFileManager {

    private static final String TOON_FILE_PATH = "src/main/resources/toons_data.txt"; // Path to your file

    // Load toons from a text file
    public List<Toon> loadToonsFromFile() {
        List<Toon> toons = new ArrayList<>();  // Create a new list every time to avoid stale references
        try (BufferedReader reader = new BufferedReader(new FileReader(TOON_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0].trim();
                    String username = parts[1].trim();
                    String password = parts[2].trim();
                    toons.add(new Toon(name, username, password));  // Add each loaded Toon
                }
            }
            System.out.println("Toons loaded successfully.");
        } catch (IOException e) {
            System.out.println("Failed to load toons from file: " + e.getMessage());
            e.printStackTrace();
        }
        return toons;  // Return the loaded list
    }

    // Save toons to a text file
    public void saveToonsToFile(List<Toon> toons) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TOON_FILE_PATH))) {
            for (Toon toon : toons) {
                writer.write(toon.getName() + "," + toon.getUsername() + "," + toon.getPassword());
                writer.newLine();
            }
            System.out.println("Toons saved successfully.");
        } catch (IOException e) {
            System.out.println("Failed to save toons: " + e.getMessage());
            e.printStackTrace();
        }
    }
}