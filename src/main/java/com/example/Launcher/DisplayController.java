package com.example.Launcher;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;

public class DisplayController {

    @FXML
    private ListView<ToonWithCheckBox> toonsListView;

    private ObservableList<ToonWithCheckBox> toonsWithCheckBoxes = FXCollections.observableArrayList();
    private static final String DATA_FILE = "toons_data.txt"; // File used to store toon data

    public void initialize() {
        loadToonsFromFile();
        toonsListView.setItems(toonsWithCheckBoxes);
        toonsListView.setCellFactory(listView -> new ListCell<ToonWithCheckBox>() {
            @Override
            protected void updateItem(ToonWithCheckBox item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.getToon().getName());  // Show only the toon's name
                    setGraphic(item.getCheckBox());     // Show the checkbox

                    item.getCheckBox().selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
                        if (isNowSelected) {
                            toonsListView.getSelectionModel().select(item);
                        } else {
                            toonsListView.getSelectionModel().clearSelection();
                        }
                    });
                } else {
                    setText(null);
                    setGraphic(null);
                }
            }
        });
    }

    public void addToon(Toon toon) {
        ToonWithCheckBox toonWithCheckBox = new ToonWithCheckBox(toon);
        toonsWithCheckBoxes.add(toonWithCheckBox);
        saveToonsToFile();
    }

    private void saveToonsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE))) {
            for (ToonWithCheckBox toonWithCheckBox : toonsWithCheckBoxes) {
                Toon toon = toonWithCheckBox.getToon();
                writer.write(toon.getName() + "," + toon.getUsername() + "," + toon.getPassword());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadToonsFromFile() {
        File file = new File(DATA_FILE);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");  // Assuming data is stored as name,username,password
                    if (data.length == 3) {
                        Toon toon = new Toon(data[0], data[1], data[2]);  // Create a Toon object
                        ToonWithCheckBox toonWithCheckBox = new ToonWithCheckBox(toon);
                        toonsWithCheckBoxes.add(toonWithCheckBox);  // Add to the list
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void openAddToonForm() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Form.fxml"));
            Parent root = loader.load();

            // Get reference to the form controller
            FormController formController = loader.getController();
            formController.setDisplayController(this);

            // Create and configure the new stage for the form
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);  // Block main window interaction
            stage.setTitle(" ");

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void playSelectedToon() {
        // Get the selected toon from the ListView
        ToonWithCheckBox selectedToonWithCheckBox = toonsListView.getSelectionModel().getSelectedItem();

        if (selectedToonWithCheckBox != null) {
            if (selectedToonWithCheckBox.isSelected()) {
                Toon selectedToon = selectedToonWithCheckBox.getToon();
                String username = selectedToon.getUsername();
                String password = selectedToon.getPassword();

                // Simulate checking with a server using the username and password
                System.out.println("Launching toon with username: " + username);

                // Create an alert for launching the toon
                showSuccessAlert(selectedToon.getName());
            } else {
                System.out.println("Checkbox not selected.");
                showNoSelectionAlert();  // If the checkbox is not selected, show a warning
            }
        } else {
            System.out.println("No toon selected.");
            showNoSelectionAlert();  // If no toon is selected in the ListView, show a warning
        }
    }

    private void showSuccessAlert(String toonName) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Toon Launcher");
        alert.setHeaderText(null);
        alert.setContentText("Launching toon: " + toonName);
        alert.showAndWait();
    }

    private void showNoSelectionAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("No Toon Selected");
        alert.setHeaderText(null);
        alert.setContentText("Please select a toon to play.");
        alert.showAndWait();
    }

    private void showFailureAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Failed");
        alert.setHeaderText(null);
        alert.setContentText("The username or password is incorrect. Please try again.");
        alert.showAndWait();
    }
}