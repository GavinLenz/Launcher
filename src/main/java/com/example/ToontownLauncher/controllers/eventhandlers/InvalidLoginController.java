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

public class InvalidLoginController {

    @FXML
    private TableView<String[]> errorTable;

    @FXML
    private TableColumn<String[], String> toonColumn;

    @FXML
    private TableColumn<String[], String> reasonColumn;

    private final ObservableList<String[]> invalidListView = FXCollections.observableArrayList();
    private final String CSS_PATH = "/com/example/ToontownLauncher/styles.css";

    @FXML
    public void initialize() {
        // Set up the columns
        toonColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0]));
        reasonColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1]));
        // Bind the data to the TableView
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
            stage.initModality(Modality.APPLICATION_MODAL);  // Make it modal
            stage.setTitle("Login Error");
            stage.setScene(new Scene(root));

            StyleManager styleManager = new StyleManager();
            URL stylesheetURL = getClass().getResource(CSS_PATH);
            if (stylesheetURL != null) {
                styleManager.applyStylesheet(stage.getScene(), stylesheetURL.toExternalForm());
            } else {
                System.out.println("Stylesheet not found: " + CSS_PATH);
            }

            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addLoginError(String toonName, String errorMessage) {
        // Add the error to the observable list, which automatically updates the table
        invalidListView.add(new String[] {toonName, errorMessage});
    }
}