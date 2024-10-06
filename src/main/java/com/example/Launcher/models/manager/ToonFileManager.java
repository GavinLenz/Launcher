package com.example.Launcher.models.manager;

import com.example.Launcher.models.Toon;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ToonFileManager {

    private static final String TOON_FILE_PATH = "src/main/resources/toons_data.txt";
    private List<Toon> toons = new ArrayList<>();

    // Load toons from a text file
    public List<Toon> loadToonsFromFile() {
        toons.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(TOON_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0];
                    String username = parts[1];
                    String password = parts[2];
                    toons.add(new Toon(name, username, password));
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to load toons from file: " + e.getMessage());
        }
        return toons;
    }

    public void saveToonsToFile(List<Toon> toons) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TOON_FILE_PATH))) {
            for (Toon toon : toons) {
                writer.write(toon.getName() + "," + toon.getUsername() + "," + toon.getPassword());
                writer.newLine();
            }
            System.out.println("Toons saved successfully.");
        } catch (IOException e) {
            System.out.println("Failed to save toons: " + e.getMessage());
            e.printStackTrace(); // Add stack trace to see detailed error
        }
    }
}