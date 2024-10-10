package com.example.Launcher.utils.game;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.util.*;

public class LoginHandler {

    private static final String REQUEST_URL = "https://www.toontownrewritten.com/api/login?format=json";
    private final Map<String, String> loginRequest;

    // Constructor
    public LoginHandler(String username, String password) {
        loginRequest = new HashMap<>();
        loginRequest.put("username", username.trim());
        loginRequest.put("password", password.trim());

        // Debug logging
        System.out.println("Debug - Username: " + username);
        System.out.println("Debug - Password: " + password);
    }

    // Static method to start login
    public static void startLogin(String username, String password) {
        LoginHandler loginAttempt = new LoginHandler(username, password);
        loginAttempt.login();
    }

    // Main login method that determines OS and handles response
    public void login() {
        String osName = System.getProperty("os.name").toLowerCase();
        String patchManifestUrl = "https://www.toontownrewritten.com/manifest.json";  // Example URL

        // Use the LauncherHelper to determine the user-specific directory for game files
        String gameDirectory = LauncherHelper.getLauncherFilePath("").toString();

        // Check for game updates before login
        if (!checkForUpdates(gameDirectory, patchManifestUrl)) {
            System.out.println("Failed to update game files. Aborting login.");
            return;
        }

        if (osName.contains("mac")) {
            System.out.println("macOS detected, using Apache HTTPClient.");
            handleMacLogin();
        } else if (osName.contains("win")) {
            System.out.println("Windows detected, using Java HttpClient.");
            handleWindowsLogin();
        } else {
            System.out.println("Unknown OS, defaulting to macOS login handling.");
            handleMacLogin();
        }
    }

    // Apache HTTP Client (used for macOS)
    private void handleMacLogin() {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(REQUEST_URL);
            post.setHeader("Content-type", "application/x-www-form-urlencoded");

            // Set form parameters
            List<NameValuePair> params = new ArrayList<>();
            loginRequest.forEach((key, value) -> params.add(new BasicNameValuePair(key, value)));
            post.setEntity(new UrlEncodedFormEntity(params));

            // Execute the request
            try (CloseableHttpResponse response = client.execute(post)) {
                int statusCode = response.getCode();
                if (statusCode != 200) {
                    System.out.println("Error: Received HTTP status code " + statusCode);
                    return;
                }

                // Parse the raw response (ignoring GZIP handling for now)
                String rawResponse = EntityUtils.toString(response.getEntity());
                System.out.println("Raw API Response (macOS): " + rawResponse);
                handleResponse(LauncherHelper.jsonStringToMap(rawResponse));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    // Java HTTP Client (used for Windows)
    private void handleWindowsLogin() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(REQUEST_URL))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(buildUrlParameter()))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Raw API Response (Windows): " + response.body());
            handleResponse(LauncherHelper.jsonStringToMap(response.body()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Convert Map to URL parameters (used by both clients)
    private String buildUrlParameter() {
        StringBuilder parameter = new StringBuilder();
        for (Map.Entry<String, String> entry : loginRequest.entrySet()) {
            if (parameter.length() > 0) {
                parameter.append("&");
            }
            parameter.append(entry.getKey()).append("=").append(entry.getValue());
        }
        return parameter.toString();
    }

    // Response handler (same for both platforms)
    private void handleResponse(Map<String, String> response) {
        String success = response.get("success");
        if (success != null) {
            switch (success) {
                case "false":
                    System.out.println("Login Failed: " + response.get("banner"));
                    break;
                case "true":
                    System.out.println("Login Successful! Launching Game...");
                    String gameserver = response.get("gameserver");
                    String cookie = response.get("cookie");
                    launchGame(gameserver, cookie);
                    break;
                case "delayed":
                    retryLogin(response.get("queueToken"));
                    break;
                default:
                    System.out.println("Unexpected response from TTR.");
                    break;
            }
        } else {
            System.out.println("Error: Invalid response from server.");
        }
    }

    // Retry login in case of queue
    private void retryLogin(String queueToken) {
        loginRequest.put("queueToken", queueToken);
        login();
    }

    // Launch game (OS-specific handling)
    private void launchGame(String gameserver, String cookie) {
        String osName = System.getProperty("os.name").toLowerCase();
        boolean displayLogging = osName.contains("mac");
        NewLauncher.startLaunch(gameserver, cookie, displayLogging);
    }

    // Check for updates using the helper class
    public boolean checkForUpdates(String gameDirectory, String patchManifestUrl) {
        Patcher patcher = new Patcher();
        boolean isUpdated = patcher.checkUpdate(gameDirectory, patchManifestUrl);

        if (!isUpdated) {
            System.out.println("Failed to update game files. Aborting launch.");
            LauncherHelper.quitLauncher(1);  // Exit launcher or handle appropriately
        }
        return isUpdated;
    }
}