package com.example.Launcher.models;

import javafx.scene.control.ListCell;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class ToonWithCheckBoxCell extends ListCell<ToonWithCheckBox> {
    private HBox hbox = new HBox();
    private Text name = new Text();
    private CheckBox checkBox = new CheckBox();

    public ToonWithCheckBoxCell() {
        super();
        hbox.getChildren().addAll(checkBox, name);
        hbox.getStyleClass().add("hbox-cell");
        name.getStyleClass().add("text");
        checkBox.getStyleClass().add("check-box");
    }

    @Override
    protected void updateItem(ToonWithCheckBox item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            name.setText(item.getToon().getName());
            checkBox.setSelected(item.isSelected());
            setGraphic(hbox);
        }
    }
}
