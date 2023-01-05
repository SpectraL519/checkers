package com.CheckersGame.Client.View;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
        this.getChildren().addAll(new GermanButton(), new PolishButton(), new CanadianButton(), new NicknamePlayer1(), new NicknamePlayer2(), new CurrentLabel(), new StartButton());
    }

    class NicknamePlayer1 extends TextField {
        public NicknamePlayer1 () {
            super("enter 1st player's nick");
        }
    }

    class NicknamePlayer2 extends TextField {
        public NicknamePlayer2 () {
            super("enter 2nd player's nick");
        }
    }

    class GermanButton extends Button {
        public GermanButton () {
            super("German Checkers");
            this.setMinWidth(230);
            this.setMaxWidth(230);
        }
    }

    class PolishButton extends Button {
        public PolishButton () {
            super("Polish Checkers");
            this.setMinWidth(230);
            this.setMaxWidth(230);
        }
    }

    class CanadianButton extends Button {
        public CanadianButton () {
            super("Canadian Checkers");
            this.setMinWidth(230);
            this.setMaxWidth(230);
        }
    }

    class StartButton extends Button {
        public StartButton () {
            super("START!");
        }
    }

    class CurrentLabel extends Label {
        public CurrentLabel () {
            super("CURRENT CHOICE\nCheckers: Polish\nnick 1: kubus\nnick 2: krzys");
            CornerRadii corner = new CornerRadii(3);
            this.setBackground(new Background(new BackgroundFill(Color.WHITE, corner, null)));
            this.setMinWidth(230);
            this.setMaxWidth(230);
            this.setAlignment(Pos.CENTER);
        }
    }

}
