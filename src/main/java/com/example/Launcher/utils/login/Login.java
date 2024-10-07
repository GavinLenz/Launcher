package com.example.Launcher.utils.login;

import org.json.JSONObject;

import com.example.Launcher.utils.launcher.Launcher;
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
    private final String URL = "https://www.toontownrewritten.com/api/login?format=json";
    private final Map<String, String> loginDetails;

    private Login(String username, String password) {
        loginDetails = new HashMap<>();
        loginDetails.put("username", username);
        loginDetails.put("password", password);
    }

    public static void startLogin(String username, String password) {
        Login loginAttempt = new Login(username, password);
        loginAttempt.sendHttpRequest();
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
                AlertManager.showAlert(Alert.AlertType.ERROR, "Unsuccessful Login", response.get("banner"));
                break;
            case "partial":
                // TO-DO
                // New pop-up window displaying banner AND getting auth token from user
                // response.get("banner");
                break;
            case "true":
                String gameserver = response.get("gameserver");
                String cookie = response.get("cookie");
                String manifest = response.get("manifest");
                Launcher.startLaunch(gameserver, cookie, manifest);
                break;
            case "delayed":
                String eta = response.get("eta");
                String position = response.get("position");
                
                try {
                    System.err.println(eta + ", " + position);
                    // TO-DO
                    // new pop-up window to display eta and position in queue to user
                    TimeUnit.SECONDS.sleep(30);
                } 
                catch (InterruptedException e) {
                    // TO-DO
                    // new pop-up window to display error
                    return;
                }

                loginDetails.clear();
                loginDetails.put("queueToken", response.get("queueToken"));
                sendHttpRequest();
                break;
            default:
                // TO-DO
                // New window pop-up to inform the user that the 
                // TTR login API has sent a response that we don't know about
        }
    }

}