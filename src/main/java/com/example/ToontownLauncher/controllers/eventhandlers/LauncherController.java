package com.example.ToontownLauncher.controllers.eventhandlers;

import java.util.HashMap;
import java.util.List;

import com.example.ToontownLauncher.utils.game.Launcher;
import com.example.ToontownLauncher.utils.game.Login;
import com.example.ToontownLauncher.models.Toon;
import com.example.ToontownLauncher.models.errors.AlertManager;

import java.util.ArrayList;

import javafx.scene.control.Alert;

public class LauncherController {

    private static List<Login> invalidResponses;
    private static List<Login> twoFAResponses;
    private static List<Login> queuedResponses;

    public static void launchToons(List<Toon> toons) {
        // need to rework this whole for loop, i realised at the end and too tired to do it rn
        for (Toon toon : toons) {
            // starts the login process and gets the API response
            Login currentLogin = new Login(toon.getUsername(), toon.getPassword(), new InvalidLoginController());
            currentLogin.sendLoginRequest();
            HashMap<String, String> currentResponse = currentLogin.getResponse();
            String success = currentResponse.get("success");

            System.out.println("Login response: " + currentResponse);

            // launches game and skips to next iteration
            if ("true".equals(success)) {
                Launcher.startLaunch(currentResponse.get("gameserver"), currentResponse.get("cookie"));
                continue;
            }

            // adds name key-val pair so we know which account has issues with login
            currentResponse.put("name", toon.getName());

            // adds response to its corresponding list
            switch (success) {
                case "false":
                    invalidResponses = new ArrayList<>();
                    invalidResponses.add(currentLogin);
                    break;
                case "partial":
                    twoFAResponses = new ArrayList<>();
                    twoFAResponses.add(currentLogin);
                    break;
                case "delayed":
                    queuedResponses = new ArrayList<>();
                    queuedResponses.add(currentLogin);
                    break;
                default:
                    AlertManager.showAlert(Alert.AlertType.ERROR, "Unknown Response", "Unexpected response from TTR Login API.");
            }
        }
        handleInvalids();
        handleTwoFAs();
        handleQueued();
    }

    private static void handleInvalids() {
        if (invalidResponses == null) { return; }

        for (Login login : invalidResponses) {
            HashMap<String, String> response = login.getResponse();
            String name = response.get("name");
            String banner = response.get("banner");

            // TO-DO add to form which will display all invalid logins
        }
        // display form
        // no need to do anything after this
        invalidResponses = null;
    }

    private static void handleTwoFAs() {
        if (twoFAResponses == null) { return; }

        for (Login login : twoFAResponses) {
            HashMap<String, String> response = login.getResponse();
            String name = response.get("name");
            String banner = (response.get("banner").contains("ToonGuard")) ? "ToonGuard" : "2FA Code";

            // TO-DO add to form which prompts user to enter codes
        }
        // display form
    }
    
    // CALL WHEN USER ENTERS CODE VIA THE FORM BUTTON FUNCTION OR SMTH
    // clears loginDetails and sets authToken and appToken for next login attempt
    private static void twoFALogin(int index, String responseToken, String appToken) {
            Login login = twoFAResponses.get(index);
            HashMap<String, String> loginDetails = login.getLoginDetails();
            loginDetails.clear();
            loginDetails.put("authToken", responseToken);
            loginDetails.put("appToken", appToken);
            // i think we get the reference so we dont need to set? need to double check this
            // login.setLoginDetails(loginDetails);
            login.sendLoginRequest();
            
            // when done with all 2FA, make list null
            twoFAResponses = null;
    }

    private static void handleQueued() {
        if (queuedResponses == null) { return; }
        // fuck queued for now bc this never happens let's be real
    }
}