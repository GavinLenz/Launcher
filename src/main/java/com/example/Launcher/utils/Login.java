package com.example.Launcher.utils;

import org.json.JSONObject;

import com.example.Launcher.utils.ui.AlertManager;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.HashMap;

import static java.net.http.HttpClient.newHttpClient;

import javafx.scene.control.Alert;

public class Login {
    private final Map<String, String> LOGIN_DETAILS;

    private Login(String username, String password) {
        LOGIN_DETAILS = new HashMap<>();
        LOGIN_DETAILS.put("username", username);
        LOGIN_DETAILS.put("password", password);
    }

    public static void startLogin(String username, String password) {
        Login loginAttempt = new Login(username, password);
        loginAttempt.sendHttpRequest();
    }

    private void sendHttpRequest() {
        HttpClient client = newHttpClient();
        HttpResponse<String> httpResponse;

        String URL = "https://www.toontownrewritten.com/api/login?format=json";
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
        for (Map.Entry<String, String> entry : LOGIN_DETAILS.entrySet()) {
            if (!parameter.isEmpty()) {
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

        if ("false".equals(success)) {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Unsuccessful Login", response.get("banner"));
            return;
        }

        if ("true".equals(success)) {
            String gameserver = response.get("gameserver");
            String cookie = response.get("cookie");
            String manifest = response.get("manifest");

            if (gameserver == null || cookie == null || manifest == null) {
                AlertManager.showAlert(Alert.AlertType.ERROR, "Error", "Missing response values.");
            } else {
                Launcher.startLaunch(gameserver, cookie, manifest);
            }
        } else if ("delayed".equals(success)) {
            String eta = response.get("eta");
            String position = response.get("position");
            System.err.println(eta + ", " + position);

            try {
                TimeUnit.SECONDS.sleep(30);
            } catch (InterruptedException e) {
                return;
            }

            LOGIN_DETAILS.clear();
            LOGIN_DETAILS.put("queueToken", response.get("queueToken"));
            sendHttpRequest();
        } else {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Unknown Response", "Unexpected response from TTR API.");
        }
    }
}