package com.example.ToontownLauncher.models.manager;

import com.example.ToontownLauncher.models.Toon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ToonFileManager {

    private static final String TOON_FILE_PATH = "src/main/resources/com/example/ToontownLauncher/toons_data.txt"; // Path to your file


    public ObservableList<Toon> loadToonsFromFile() {
        List<Toon> toons = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(TOON_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0].trim();
                    String username = parts[1].trim();
                    String password = parts[2].trim();
                    toons.add(new Toon(name, username, password));
                }
            }
            System.out.println("Toons loaded successfully.");
        } catch (IOException e) {
            System.out.println("Failed to load toons from file: " + e.getMessage());
            e.printStackTrace();
        }
        return FXCollections.observableArrayList(toons);
    }

    public void saveToonsToFile(ObservableList<Toon> toons) {
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