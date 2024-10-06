package com.example.Launcher.models.manager;

import com.example.Launcher.models.Toon;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ToonFileManager {
    private static final String TOON_FILE_PATH = "toons.dat";

    public List<Toon> loadToonsFromFile() {
        List<Toon> toons = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(TOON_FILE_PATH))) {
            toons = (List<Toon>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Failed to load toons from file: " + e.getMessage());
        }
        return toons;
    }

    public void saveToonsToFile(List<Toon> toons) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(TOON_FILE_PATH))) {
            oos.writeObject(toons);
        } catch (IOException e) {
            System.out.println("Failed to save toons: " + e.getMessage());
        }
    }
}