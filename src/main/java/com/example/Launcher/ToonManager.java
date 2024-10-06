package com.example.Launcher;

import com.example.Launcher.models.Toon;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ToonManager {

    private List<Toon> toons = new ArrayList<>();  // Now manages only Toon objects

    public ToonManager() {
        loadToonsFromFile();
    }

    private void loadToonsFromFile() {
        String filePath = "src/main/resources/toons_data.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] details = line.split(",");
                if (details.length == 3) {
                    // Create and add Toon to the list
                    Toon toon = new Toon(details[0].trim(), details[1].trim(), details[2].trim());
                    toons.add(toon);  // No need for ToonWithCheckBox anymore
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Toon> getToons() {
        return toons;
    }

    public void addToon(Toon toon) {
        toons.add(toon);
        saveToonsToFile();
    }

    private void saveToonsToFile() {
        String filePath = "src/main/resources/toons_data.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Toon toon : toons) {
                writer.write(toon.getName() + "," + toon.getUsername() + "," + toon.getPassword());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}