package com.example.Launcher;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;

import static java.net.http.HttpClient.newHttpClient;


public class Login {
    // TTR API login URL
    // https://github.com/ToontownRewritten/api-doc/blob/master/login.md
    private final String URL = "https://www.toontownrewritten.com/api/login?format=json";
    
    // User's login details
    private final Map<String, String> loginDetails;

    // Constructor method - Adds username and password to a key-value map
    private Login(String username, String password) {
        loginDetails = new HashMap<>();
        loginDetails.put("username", username);
        loginDetails.put("password", password);
    }

    // All outside calls to login should use this static method
    public static void startLogin(String username, String password) {
        Login loginAttempt = new Login(username, password);
        loginAttempt.sendHttpRequest();
    }

    // Sends an HTTP POST request to TTR API login URL
    private void sendHttpRequest() {
        HttpClient client = newHttpClient();
        HttpResponse<String> httpResponse;

            // Builds the HTTP request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .header("Content-Type", "application/x-www-form-urlencoded; text/plain; charset=UTF-8")
                .POST(HttpRequest.BodyPublishers.ofString(buildUrlParameter()))
                .build();

        // Attempts to send an HTTP request
        try {
            httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            // TO-DO
            // New window pop-up to inform the user that we are unable to send a login request
            return;
        }

        // Handles the API's responses
        handleResponse(jsonStringToHashMap(httpResponse.body()));
    }

    // Formats the parameters in "param1=value1&param2=value2&..."
    private String buildUrlParameter() {
        StringBuilder parameter = new StringBuilder();
        for (Map.Entry<String, String> entry : loginDetails.entrySet()) {
            if (!parameter.isEmpty()) {
                parameter.append("&");
            }
            parameter.append(entry.getKey()).append("=").append(entry.getValue());
        }
        return parameter.toString();
    }
    
    // Converts the API's JSON response into a HashMap for easier access
    private Map<String, String> jsonStringToHashMap(String str) {
        Map<String, String> map = new HashMap<>();
        JSONObject jsonObj = new JSONObject(str);
        Iterator<String> keys = jsonObj.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            String value = jsonObj.getString(key);
            map.put(key, value);
        }
        return map;
    }

    // Handles all API responses
    private void handleResponse(Map<String, String> response) {
        // Status of login request
        String success = response.get("success");

        switch (success) {
            case "false":
            // Failure response
                // TO-DO
                // New pop-up window displaying banner
                // response.get("banner");
                break;
            case "partial":
            // Partially-authenticated response. Requires either ToonGuard or 2FA token
                // TO-DO
                // New pop-up window displaying banner AND getting auth token from user
                // response.get("banner");
                break;
            case "true":
            // Non-queued response
                String gameserver = response.get("gameserver");
                String cookie = response.get("cookie");
                String manifest = response.get("manifest");
                // TO-DO
                // create a LaunchTT instance - still need to code launcher
                break;
            case "delayed":
            // Queued response
                String eta = response.get("eta");
                String position = response.get("position");
                
                // TTR API doc suggests sending another POST request in no more than 30 sec intervals
                try {
                    // TO-DO
                    // new pop-up window to display eta and position in queue to user
                    TimeUnit.SECONDS.sleep(30);
                } 
                catch (InterruptedException e) {
                    // TO-DO
                    // new pop-up window to display error
                    return;
                }
                
                // A queued login request will only require the queueToken
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
