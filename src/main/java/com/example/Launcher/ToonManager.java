package com.example.Launcher;

import com.example.Launcher.models.Toon;
import com.example.Launcher.models.ToonWithCheckBox;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ToonManager {

    private List<ToonWithCheckBox> toonsWithCheckBoxes = new ArrayList<>();

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
                    Toon toon = new Toon(details[0].trim(), details[1].trim(), details[2].trim());
                    toonsWithCheckBoxes.add(new ToonWithCheckBox(toon));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<ToonWithCheckBox> getToonsWithCheckBoxes() {
        return toonsWithCheckBoxes;
    }

    public void addToon(Toon toon) {
        ToonWithCheckBox toonWithCheckBox = new ToonWithCheckBox(toon);
        toonsWithCheckBoxes.add(toonWithCheckBox);
        saveToonsToFile();
    }

    private void saveToonsToFile() {
        String filePath = "src/main/resources/toons_data.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (ToonWithCheckBox toonWithCheckBox : toonsWithCheckBoxes) {
                Toon toon = toonWithCheckBox.getToon();
                writer.write(toon.getName() + "," + toon.getUsername() + "," + toon.getPassword());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}