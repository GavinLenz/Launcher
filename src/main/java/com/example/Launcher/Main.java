package com.example.Launcher;

import com.example.Launcher.controllers.EventHandlers;
import com.example.Launcher.utils.GameLauncher;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

import static com.example.Launcher.PlistModifier.modifyPlist;

public class Main extends Application {

    private Stage primaryStage;
    private EventHandlers eventHandlers;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;  // Store the reference to primaryStage

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/Launcher/Display.fxml"));
        primaryStage.setTitle("Toontown Launcher");
        primaryStage.setScene(new Scene(loader.load(), 500, 320));
        primaryStage.setResizable(false);
        primaryStage.getScene().getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/Launcher/styles.css")).toExternalForm());

        primaryStage.show();

        //  Modify the Info.plist file as required
        //  String plistPath = "/Applications/Toontown Launcher.app/Contents/Info.plist";
        //  modifyPlist(plistPath);

        // needs fixing
        eventHandlers = new EventHandlers();

        if (eventHandlers != null) {
            eventHandlers.playSelectedToon(primaryStage);
        } else {
            System.out.println("EventHandlers is null!");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}