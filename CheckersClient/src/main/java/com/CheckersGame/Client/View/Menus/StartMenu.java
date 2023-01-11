package com.CheckersGame.Client.View.Menus;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import com.CheckersGame.Client.GameController;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;



public class StartMenu extends BorderPane {

    private String checkedGame;
    private GameController gameController;
    private VBox menu;

    public StartMenu (GameController gameController) {
        super();
        System.out.println("StartMenu: checkpoint 1");
        this.gameController = gameController;
        this.checkedGame = null;
        this.menu = null;
        System.out.println("StartMenu: checkpoint 2");
    }

    public  void render(){
        System.out.println("StartMenu: checkpoint 3");
        this.menu = new VBox();
        this.menu.setBackground(new Background(new BackgroundFill(Color.CORAL, null, null)));
        this.menu.setPadding(new Insets(10, 10, 10, 10));
        this.menu.setSpacing(10);
        this.menu.setAlignment(Pos.CENTER);
        this.menu.setMinWidth(250);
        this.menu.setMaxWidth(250);
        this.menu.getChildren().addAll(new WelcomeText(), new RussianButton(this), new PolishButton(this), new CanadianButton(this), new StartButton(this, this.gameController));
        this.setCenter(this.menu);
        System.out.println("StartMenu: checkpoint 4");
    }

    class WelcomeText extends Label {
        public WelcomeText () {
            super("Select checkers type:");
            CornerRadii corner = new CornerRadii(3);
            this.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, corner, null)));
            this.setMinWidth(230);
            this.setMaxWidth(230);
            this.setMinHeight(30);
            this.setMaxHeight(30);
            this.setAlignment(Pos.CENTER);
        }
    }

    class RussianButton extends Button {
        public RussianButton (StartMenu startMenu) {
            super("Russian Checkers");
            this.setMinWidth(230);
            this.setMaxWidth(230);
            this.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                  startMenu.checkedGame = "russian";
                }
            });
        }
    }

    class PolishButton extends Button {
        public PolishButton (StartMenu startMenu) {
            super("Polish Checkers");
            this.setMinWidth(230);
            this.setMaxWidth(230);
            this.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                  startMenu.checkedGame = "polish";
                }
            });
        }
    }

    class CanadianButton extends Button {
        public CanadianButton (StartMenu startMenu) {
            super("Canadian Checkers");
            this.setMinWidth(230);
            this.setMaxWidth(230);
            this.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                  startMenu.checkedGame = "canadian";
                }
            });
        }
    }

    class StartButton extends Button {
        public StartButton (StartMenu startMenu, GameController gameController) {
            super("START!");
            this.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (startMenu.checkedGame != null) {
                        gameController.newGame(checkedGame);
                    }
                }
            });
        }
    }

}
