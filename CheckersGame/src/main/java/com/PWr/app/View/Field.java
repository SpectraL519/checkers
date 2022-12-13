package com.PWr.app.View;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;





public class Field extends Pane {
    public Field (Color color, int pixelSize) {
        super();
        this.setMinWidth(pixelSize);
        this.setMaxWidth(pixelSize);
        this.setMinHeight(pixelSize);
        this.setMaxHeight(pixelSize);
        this.setBackground(new Background(new BackgroundFill(color, null, null)));
    }
}
