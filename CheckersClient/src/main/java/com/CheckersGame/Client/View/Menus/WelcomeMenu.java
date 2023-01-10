package com.CheckersGame.Client.View.Menus;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;



public class WelcomeMenu extends VBox {

    public WelcomeMenu () {
        super();
    }

    public  void render(){
        this.setBackground(new Background(new BackgroundFill(Color.CORAL, null, null)));
        this.setPadding(new Insets(10, 10, 10, 10));
        this.setSpacing(10);
        this.setAlignment(Pos.CENTER);
        this.setMinWidth(250);
        this.setMaxWidth(250);
        this.getChildren().addAll(new WelcomeText(), new StartButton());
    }

    class WelcomeText extends Label {
        public WelcomeText () {
            super("Welcome in Checkers Game!");
            CornerRadii corner = new CornerRadii(3);
            this.setBackground(new Background(new BackgroundFill(Color.WHITE, corner, null)));
            this.setMinWidth(230);
            this.setMaxWidth(230);
            this.setMinHeight(70);
            this.setMaxHeight(70);
            this.setAlignment(Pos.CENTER);
            this.setContentDisplay(ContentDisplay.CENTER);
        }
    }

    class StartButton extends Button {
        public StartButton () {
            super("START!");
            this.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                  System.out.println("Move to StartMenu or WaitMenu!");
                }
            });
        }
    }

}