package com.example.Launcher.utils;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.HashMap;

import static java.net.http.HttpClient.newHttpClient;

public class Login {
    private final String URL = "https://www.toontownrewritten.com/api/login?format=json";
    private final Map<String, String> loginDetails;

    private Login(String username, String password) {
        loginDetails = new HashMap<>();
        loginDetails.put("username", username);
        loginDetails.put("password", password);
    }

    public static void startLogin(String username, String password) {
        if (verifyCredentials(username, password)) {
            Login loginAttempt = new Login(username, password);
            loginAttempt.sendHttpRequest();
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    private static boolean verifyCredentials(String username, String password) {
        String filePath = "src/main/resources/toons_data.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] details = line.split(",");
                if (details.length == 3) {
                    String storedUsername = details[1].trim();
                    String storedPassword = details[2].trim();

                    // Debug statement to check what credentials are being compared
                    System.out.println("Comparing entered username: " + username + " with stored username: " + storedUsername);
                    System.out.println("Comparing entered password: " + password + " with stored password: " + storedPassword);

                    // Comparing stored credentials with entered credentials
                    if (storedUsername.equals(username) && storedPassword.equals(password)) {
                        System.out.println("Credentials match!");
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Credentials do not match.");
        return false;
    }

    private void sendHttpRequest() {
        HttpClient client = newHttpClient();
        HttpResponse<String> httpResponse;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .header("Content-Type", "application/x-www-form-urlencoded; text/plain; charset=UTF-8")
                .POST(HttpRequest.BodyPublishers.ofString(buildUrlParameter()))
                .build();

        try {
            httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        handleResponse(jsonStringToHashMap(httpResponse.body()));
    }

    private String buildUrlParameter() {
        StringBuilder parameter = new StringBuilder();
        for (Map.Entry<String, String> entry : loginDetails.entrySet()) {
            if (parameter.length() > 0) {
                parameter.append("&");
            }
            parameter.append(entry.getKey()).append("=").append(entry.getValue());
        }
        return parameter.toString();
    }

    private Map<String, String> jsonStringToHashMap(String str) {
        Map<String, String> map = new HashMap<>();
        JSONObject jsonObj = new JSONObject(str);
        jsonObj.keys().forEachRemaining(key -> map.put(key, jsonObj.getString(key)));
        return map;
    }

    private void handleResponse(Map<String, String> response) {
        String success = response.get("success");
        switch (success) {
            case "false":
                System.out.println("Login failed: " + response.get("banner"));
                break;
            case "true":
                System.out.println("Login successful!");
                break;
            case "delayed":
                System.out.println("Queued: position " + response.get("position") + ", ETA: " + response.get("eta"));
                break;
            default:
                System.out.println("Unknown response: " + success);
        }
    }
}