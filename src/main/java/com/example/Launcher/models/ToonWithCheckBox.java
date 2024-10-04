package com.example.Launcher.models;

import javafx.scene.control.CheckBox;

public class ToonWithCheckBox {
    private Toon toon;
    private CheckBox checkBox;

    public ToonWithCheckBox(Toon toon) {
        this.toon = toon;
        this.checkBox = new CheckBox();
    }

    public Toon getToon() {
        return toon;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public boolean isSelected() {
        return checkBox.isSelected();
    }
}