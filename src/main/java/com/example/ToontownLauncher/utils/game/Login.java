package com.example.ToontownLauncher.utils.game;

import com.example.ToontownLauncher.models.errors.AlertManager;
import javafx.scene.control.Alert;

import java.util.HashMap;

import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import static java.net.http.HttpClient.newHttpClient;

public class Login {

    private final HashMap<String, String> response;
    private final HashMap<String, String> loginDetails;


    public Login(HashMap<String, String> loginDetails) {
        response = new HashMap<>();
        this.loginDetails = loginDetails;
        sendLoginRequest();
    }

    public HashMap<String, String> getResponse() {
        return response;
    }

    private void sendLoginRequest() {
        HttpClient client = newHttpClient();
        HttpResponse<String> httpResponse;

        String url = "https://www.toontownrewritten.com/api/login?format=json";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/x-www-form-urlencoded; text/plain; charset=UTF-8")
                .POST(HttpRequest.BodyPublishers.ofString(buildUrlParameter()))
                .build();

        try {
            httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
            jsonStringToHashMap(httpResponse.body());
        } catch (Exception e) {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Login error", "Could not send a login request.");
            e.printStackTrace();
        }
    }

    private String buildUrlParameter() {
        StringBuilder parameter = new StringBuilder();
        for (HashMap.Entry<String, String> entry : loginDetails.entrySet()) {
            if (!parameter.isEmpty()) {
                parameter.append("&");
            }
            parameter.append(entry.getKey()).append("=").append(entry.getValue());
        }
        return parameter.toString();
    }

    private void jsonStringToHashMap(String str) {
        JSONObject jsonObj = new JSONObject(str);
        jsonObj.keys().forEachRemaining(key -> response.put(key, jsonObj.getString(key)));
    }
}