package com.example.Launcher.controllers.eventhandlers;

<<<<<<< Updated upstream:src/main/java/com/example/Launcher/controllers/EventHandlers.java
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
=======
import com.example.Launcher.controllers.DisplayController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
>>>>>>> Stashed changes:src/main/java/com/example/Launcher/controllers/eventhandlers/ToonEventHandlers.java
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.example.Launcher.models.Toon;
import com.example.Launcher.models.manager.ToonListManager;
import com.example.Launcher.utils.GameLauncher;
import com.example.Launcher.utils.Login;
import com.example.Launcher.utils.ui.ToonListInitializer;

import java.io.IOException;
import java.util.Objects;

<<<<<<< Updated upstream:src/main/java/com/example/Launcher/controllers/EventHandlers.java
import com.example.Launcher.UIInitializer;
import com.example.Launcher.models.Toon;
import com.example.Launcher.ToonManager;
import com.example.Launcher.utils.Login;


public class EventHandlers {

    private ToonManager toonManager;
=======
public class ToonEventHandlers {

    private ToonListManager toonManager;
>>>>>>> Stashed changes:src/main/java/com/example/Launcher/controllers/eventhandlers/ToonEventHandlers.java
    private ListView<Toon> toonsListView;
    private DisplayController displayController;
    private ToonListInitializer uiInitializer;
    private Stage primaryStage;  // Add this to store primaryStage

    public ToonEventHandlers(ToonListManager toonManager, ListView<Toon> toonsListView,
                             DisplayController displayController, ToonListInitializer uiInitializer) {
        this.toonManager = toonManager;
        this.toonsListView = toonsListView;
        this.displayController = displayController;
        this.uiInitializer = uiInitializer;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;  // Ensure the primaryStage is passed to this class
    }

<<<<<<< Updated upstream:src/main/java/com/example/Launcher/controllers/EventHandlers.java
    protected void addToonHandler() {
=======
    @FXML
    public void openAddToonForm() {
>>>>>>> Stashed changes:src/main/java/com/example/Launcher/controllers/eventhandlers/ToonEventHandlers.java
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/Launcher/views/Form.fxml"));
            Parent root = loader.load();

            FormController formController = loader.getController();
            formController.setDisplayController(displayController);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add Toon");

            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/Launcher/styles.css")).toExternalForm());

            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

<<<<<<< Updated upstream:src/main/java/com/example/Launcher/controllers/EventHandlers.java
    void playClickHandler() {
        ObservableList<Toon> selectedToons = UIInitializer.getSelectedToons();  // Access selected toons from UIInitializer
=======
    public void playSelectedToon(Stage primaryStage) {
        ObservableList<Toon> selectedToons = uiInitializer.getSelectedToons();  // Get selected Toons
>>>>>>> Stashed changes:src/main/java/com/example/Launcher/controllers/eventhandlers/ToonEventHandlers.java

        if (selectedToons.isEmpty()) {
            showNoSelectionAlert();  // Handle no Toon selected
            return;
        }

        for (Toon toon : selectedToons) {
            String username = toon.getUsername();
            String password = toon.getPassword();

<<<<<<< Updated upstream:src/main/java/com/example/Launcher/controllers/EventHandlers.java
            Login.startLogin(username, password);
=======
            if (Login.verifyCredentials(username, password)) {
                GameLauncher.launchGame(primaryStage, toon.getGameserver(), toon.getCookie());  // Ensure game is launched
                showSuccessAlert(toon.getName());
            } else {
                showFailureAlert();
            }
>>>>>>> Stashed changes:src/main/java/com/example/Launcher/controllers/eventhandlers/ToonEventHandlers.java
        }
    }

    // REMOVE LATER - will be handled by alertHandler 
    private void showNoSelectionAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("No Toon Selected");
        alert.setHeaderText(null);
        alert.setContentText("Please select a toon to play.");
        alert.showAndWait();
    }
}