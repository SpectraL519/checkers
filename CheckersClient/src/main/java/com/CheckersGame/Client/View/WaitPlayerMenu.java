package com.CheckersGame.Client.View;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;



public class WaitPlayerMenu extends VBox {

    public WaitPlayerMenu () {
        super();
    }

    public  void render(){
        this.setBackground(new Background(new BackgroundFill(Color.CORAL, null, null)));
        this.setPadding(new Insets(10, 10, 10, 10));
        this.setSpacing(10);
        this.setAlignment(Pos.CENTER);
        this.setMinWidth(250);
        this.setMaxWidth(250);
        this.getChildren().addAll(new WelcomeText());
    }

    class WelcomeText extends Label {
        public WelcomeText () {
            super("Waiting for Your oponent...");
            CornerRadii corner = new CornerRadii(3);
            this.setBackground(new Background(new BackgroundFill(Color.WHITE, corner, null)));
            this.setMinWidth(230);
            this.setMaxWidth(230);
            this.setMinHeight(30);
            this.setMaxHeight(30);
            this.setAlignment(Pos.CENTER);
        }
    }

}
