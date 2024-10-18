package com.example.ToontownLauncher.controllers.eventhandlers;

import java.util.HashMap;
import java.util.List;

import com.example.ToontownLauncher.utils.game.Launcher;
import com.example.ToontownLauncher.utils.game.Login;
import com.example.ToontownLauncher.models.Toon;
import com.example.ToontownLauncher.models.errors.AlertManager;

import javafx.scene.control.Alert;

public class LauncherController {

    public static void launchToons(List<Toon> toons) {
        InvalidLoginController invalidLogins = null;
        HashMap<String, String> loginValues = new HashMap<>();

        for (Toon toon : toons) {
            loginValues.put("username", toon.getUsername());
            loginValues.put("password", toon.getPassword());

            loginValues = new Login(loginValues).getResponse();

            String success = loginValues.get("success");

            if ("true".equals(success)) {
                Launcher.startLaunch(loginValues.get("gameserver"), loginValues.get("cookie"));
                continue;
            }

            String name = toon.getName();
            String banner;

            switch (success) {
                case "false":
                    banner = loginValues.get("banner");
                    if (invalidLogins == null) { invalidLogins = new InvalidLoginController(); }
                    invalidLogins.addLoginError(name, banner);

                    break;
                case "partial":
                    banner = loginValues.get("banner").contains("ToonGuard") ? "ToonGuard: " : "2FA Code: ";

                    break;
                case "delayed":
                    String position = loginValues.get("position");
                    String eta = loginValues.get("eta");
                    banner = String.format("Position: %s. Estimated wait time: %s.", position, eta);

                    break;
                default:
                    AlertManager.showAlert(Alert.AlertType.ERROR, "Unknown Response", "Unexpected response from TTR Login API.");
            }
        }

        if (invalidLogins != null) {
            invalidLogins.showErrorForm();
        }
    }
}