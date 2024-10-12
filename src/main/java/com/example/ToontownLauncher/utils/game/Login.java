package com.example.ToontownLauncher.utils.game;

import com.example.ToontownLauncher.controllers.eventhandlers.InvalidLoginController;
import com.example.ToontownLauncher.models.errors.AlertManager;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import static java.net.http.HttpClient.newHttpClient;

import java.util.HashMap;
import javafx.scene.control.Alert;

public class Login {

    private HashMap<String, String> response;
    private HashMap<String, String> loginDetails;
    private InvalidLoginController invalidLoginController;  // Reference to InvalidLoginController

    public Login(String username, String password, InvalidLoginController invalidLoginController) {
        this.invalidLoginController = invalidLoginController;

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

            // Check for errors in the response and pass to InvalidLoginController
            if ("false".equals(response.get("success"))) {
                String errorMessage = response.get("banner");
                String toonName = loginDetails.get("username");
                invalidLoginController.addLoginError(toonName, errorMessage);
                invalidLoginController.showErrorForm();  // Show error form
            }
        } catch (Exception e) {
            String errorMsg = "Login error: Could not send a login request.";
            AlertManager.showAlert(Alert.AlertType.ERROR, "Login error", errorMsg);
            invalidLoginController.addLoginError(loginDetails.get("username"), errorMsg);  // Pass error to table as well
            invalidLoginController.showErrorForm();  // Show error form
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