package com.CheckersGame.Client.View;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class Toolbar extends HBox {

    public Toolbar() {
        super();
    }

    public void render() {
        this.setPadding(new Insets(7, 7, 7, 7));
        this.setSpacing(10);
        this.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, null, null)));

        this.getChildren().addAll(new ExitButton(), new RestartButton(), new MockSituation());
    }

    class ExitButton extends Button {
        public ExitButton() {
            super("Exit game");
            this.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.out.println("Good Bye!");
                    Platform.exit();
                }
            });
        }
    }

    class RestartButton extends Button {
        public RestartButton() {
            super("Restart game");
            this.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.out.println("Restarting...");
                }
            });
        }
    }

    class MockSituation extends SplitMenuButton {
        public MockSituation() {
            super();
            this.setText("Mocks");

            MenuItem choice1 = new MenuItem("Simple endgame");
            MenuItem choice2 = new MenuItem("Queen endgame");
            MenuItem choice3 = new MenuItem("Pawn to queen");

            choice1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.out.println("Option 1 selected");
                }
            });

            choice2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.out.println("Option 2 selected");
                }
            });

            choice3.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.out.println("Option 3 selected");
                }
            });

            this.getItems().addAll(choice1, choice2, choice3);


        }
    }
}
