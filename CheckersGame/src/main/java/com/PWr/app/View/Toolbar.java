package com.PWr.app.View;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
// import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;





public class Toolbar extends HBox {
    public Toolbar () {
        super();
    }



    public void render () {
        this.setPadding(new Insets(10, 10, 10, 10));
        this.setSpacing(10);
        this.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY,null,null)));

        // this.getChildren().addAll(new Label("Test"));
        this.getChildren().addAll(new ExitButton(), new RestartButton());
    }


    class ExitButton extends Button {
        public ExitButton () {
            super("Exit game");
        }
    }

    class RestartButton extends Button {
        public RestartButton () {
            super("Restart game");
        }
    }
}
