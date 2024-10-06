package com.example.Launcher;

import com.example.Launcher.models.Toon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class UIInitializer {

    private ListView<Toon> toonsListView;
    private ToonManager toonManager;

    private static ObservableList<Toon> selectedToons = FXCollections.observableArrayList();

    public UIInitializer(ListView<Toon> toonsListView, ToonManager toonManager) {
        this.toonsListView = toonsListView;
        this.toonManager = toonManager;
        initialize();
    }

    // Set up the custom cell factory
    public void initialize() {
        toonsListView.setCellFactory(param -> new ListCell<>() {
            private CheckBox checkBox = new CheckBox();
            private Text toonName = new Text();

            private HBox hbox = new HBox(checkBox, toonName);  // HBox to hold the checkbox and Toon name

            {
                hbox.setSpacing(8);  // Set spacing between checkbox and Toon name
            }

            @Override
            protected void updateItem(Toon toon, boolean empty) {
                super.updateItem(toon, empty);
                if (empty || toon == null) {
                    setGraphic(null);
                } else {
                    toonName.setText(toon.getName());  // Set the name of the Toon
                    toonName.getStyleClass().add("text");  // Custom CSS for now
                    setGraphic(hbox);

                    checkBox.setSelected(selectedToons.contains(toon));  // Sync checkbox with selection state

                    // Handle checkbox selection (all temporary for now)
                    checkBox.setOnAction(event -> {
                        if (checkBox.isSelected()) {
                            selectedToons.add(toon);  // Add Toon to selected list
                        } else {
                            selectedToons.remove(toon);  // Remove Toon from selected list
                        }
                    });

                    setGraphic(hbox);  // Set the graphic to show the checkbox and Toon name
                }
            }
        });
        updateToonList();  // Load the initial list of Toons
    }

    public void updateToonList() {
        ObservableList<Toon> toons = FXCollections.observableArrayList(toonManager.getToons());
        toonsListView.setItems(toons);  // Populate the ListView with Toon objects
    }

    // Get the list of selected Toons
    public static ObservableList<Toon> getSelectedToons() {
        return selectedToons;  // This is now instance-based
    }
}