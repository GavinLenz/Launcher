package com.example.Launcher;

import com.example.Launcher.models.manager.ToonFileManager;
import com.example.Launcher.models.manager.ToonListManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;
import com.example.Launcher.models.Toon;
import com.example.Launcher.controllers.DisplayController;

public class Main extends Application {

    private ToonFileManager toonFileManager = new ToonFileManager();
    private ToonListManager toonListManager = new ToonListManager();

    @Override
    public void start(Stage primaryStage) throws Exception {
        List<Toon> savedToons = toonFileManager.loadToonsFromFile();
        toonListManager.getToons().addAll(savedToons);  // Load saved toons

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/Launcher/Display.fxml"));
        Scene scene = new Scene(loader.load(), 500, 320);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Toontown Launcher");

        DisplayController controller = (DisplayController) loader.getController();
        controller.setPrimaryStage(primaryStage);

        primaryStage.show();
    }

<<<<<<< Updated upstream
        //  Modify the Info.plist file as required
        //  String plistPath = "/Applications/Toontown Launcher.app/Contents/Info.plist";
        //  modifyPlist(plistPath);

        // needs fixing
        // eventHandlers = new EventHandlers();

        // if (eventHandlers != null) {
        //     eventHandlers.playSelectedToon(primaryStage);
        // } else {
        //     System.out.println("EventHandlers is null!");
        // }
=======
    @Override
    public void stop() {
        toonFileManager.saveToonsToFile(toonListManager.getToons());
>>>>>>> Stashed changes
    }

    public static void main(String[] args) {
        launch(args);
    }
}