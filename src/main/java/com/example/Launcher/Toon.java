package com.example.Launcher;

import java.io.*;
import java.util.List;

public class Toon  implements Serializable {
    private String name;
    private String username;
    private String password;
    private List<String> toons;

    public Toon(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void saveToFile(String filename) {
        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write("Toon Name: " + name + "\n");
            writer.write("Username: " + username + "\n");
            writer.write("Password: " + password + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Toon loadFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String name = reader.readLine();
            String username = reader.readLine();
            String password = reader.readLine();

            Toon toon = new Toon(name, username, password);
            return toon;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addToon(Toon toon) {
        this.toons.add(String.valueOf(toon));
    }
}