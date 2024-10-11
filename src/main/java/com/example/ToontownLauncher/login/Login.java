package com.example.ToontownLauncher.login;

import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import static java.net.http.HttpClient.newHttpClient;

import java.util.HashMap;

import com.example.ToontownLauncher.utils.ui.AlertManager;
import javafx.scene.control.Alert;


public class Login {
    private HashMap<String, String> response;
    private HashMap<String, String> loginDetails;

    public Login(String username, String password) {
        response = new HashMap<>();
        loginDetails = new HashMap<>();
        loginDetails.put("username", username);
        loginDetails.put("password", password);
    }

    // response getter
    public HashMap<String, String> getResponse() {
        return response;
    }
    // loginDetails getter and setter
    public HashMap<String,String> getLoginDetails() {
        return loginDetails;
    }
    public void setLoginDetails(HashMap<String, String> loginDetails) {
        this.loginDetails = loginDetails;
    }

    public void sendLoginRequest() {
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
            jsonStringToHashMap(httpResponse.body());
        } catch (Exception e) {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Login error", "Could not send a login request.");
            e.printStackTrace();
            return;
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