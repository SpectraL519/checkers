package com.CheckersGame.Client.View;

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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;



public class StartMenu extends VBox {

    private String checkedGame = "";
    private String nickOne = "";
    private String nickTwo = "";

    public StartMenu () {
        super();
    }

    public  void render(){
        this.setBackground(new Background(new BackgroundFill(Color.CORAL, null, null)));
        this.setPadding(new Insets(10, 10, 10, 10));
        this.setSpacing(10);
        this.setAlignment(Pos.CENTER);
        this.setMinWidth(250);
        this.setMaxWidth(250);
        this.getChildren().addAll(new WelcomeText(), new GermanButton(), new PolishButton(), new CanadianButton(), new StartButton());
    }

    class WelcomeText extends Label {
        public WelcomeText () {
            super("Select checkers type:");
            CornerRadii corner = new CornerRadii(3);
            this.setBackground(new Background(new BackgroundFill(Color.WHITE, corner, null)));
            this.setMinWidth(230);
            this.setMaxWidth(230);
            this.setMinHeight(30);
            this.setMaxHeight(30);
            this.setAlignment(Pos.CENTER);
        }
    }

    class GermanButton extends Button {
        public GermanButton () {
            super("German Checkers");
            this.setMinWidth(230);
            this.setMaxWidth(230);
            this.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                  System.out.println("German selected");
                }
            });
        }
    }

    class PolishButton extends Button {
        public PolishButton () {
            super("Polish Checkers");
            this.setMinWidth(230);
            this.setMaxWidth(230);
            this.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                  System.out.println("Polish selected");
                }
            });
        }
    }

    class CanadianButton extends Button {
        public CanadianButton () {
            super("Canadian Checkers");
            this.setMinWidth(230);
            this.setMaxWidth(230);
            this.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                  System.out.println("Canadian selected");
                }
            });
        }
    }

    class StartButton extends Button {
        public StartButton () {
            super("START!");
            this.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                  System.out.println("Move to GameView!");
                }
            });
        }
    }

}
