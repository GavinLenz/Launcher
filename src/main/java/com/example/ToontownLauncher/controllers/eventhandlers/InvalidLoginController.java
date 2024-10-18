package com.example.ToontownLauncher.controllers.eventhandlers;

import com.example.ToontownLauncher.utils.ui.StyleManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InvalidLoginController {

    @FXML
    private TableView<String[]> errorTable;
    @FXML
    private TableColumn<String[], String> toonColumn;
    @FXML
    private TableColumn<String[], String> reasonColumn;

    private final ObservableList<String[]> invalidListView = FXCollections.observableArrayList();


    @FXML
    public void initialize() {
        toonColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0]));
        reasonColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1]));
        errorTable.setItems(invalidListView);
    }

    public void showErrorForm() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ToontownLauncher/InvalidLogin.fxml"));
            Parent root = loader.load();

            InvalidLoginController controller = loader.getController();
            controller.initialize();
            controller.invalidListView.addAll(this.invalidListView);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Login Error");
            stage.setScene(new Scene(root));
            stage.setResizable(false);

            StyleManager styleManager = new StyleManager();
            URL stylesheetURL = getClass().getResource("/com/example/ToontownLauncher/styles.css");
            if (stylesheetURL != null) {
                System.out.println("Applying stylesheet: " + stylesheetURL.toExternalForm());
                styleManager.applyStylesheet(stage.getScene(), stylesheetURL.toExternalForm());
            } else {
                Logger.getLogger(InvalidLoginController.class.getName()).log(Level.WARNING, "Stylesheet not found: /com/example/ToontownLauncher/styles.css");
            }

            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addLoginError(String toonName, String errorMessage) {
        invalidListView.add(new String[] {toonName, errorMessage});
    }
}