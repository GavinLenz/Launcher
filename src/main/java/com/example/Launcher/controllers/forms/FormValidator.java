package com.example.Launcher.controllers.forms;

public class FormValidator {

    // Filler code for now

    public static boolean isValidName(String name) {
        return name != null && !name.isEmpty();
    }

    public static boolean isValidUsername(String username) {
        return username != null && !username.isEmpty();
    }

    public static boolean isValidPassword(String password)  {
        return password != null && !password.isEmpty();
    }
}
