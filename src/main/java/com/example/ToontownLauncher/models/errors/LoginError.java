package com.example.ToontownLauncher.models.errors;

public class LoginError {

    private String toonName;
    private String errorMessage;


    public LoginError(String toonName, String errorMessage) {
        this.toonName = toonName;
        this.errorMessage = errorMessage;
    }

    public String getToonName() {
        return toonName;
    }

    public void setToonName(String toonName) {
        this.toonName = toonName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}