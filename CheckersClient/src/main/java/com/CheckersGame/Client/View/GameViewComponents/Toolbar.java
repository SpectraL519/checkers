package com.CheckersGame.Client.View.GameViewComponents;

import com.CheckersGame.Client.GameController;
import com.CheckersGame.Client.View.GameView;

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

    private GameController gameController;
    private String colorOfPawns;

    public Toolbar(GameController gameController, String colorOfPawns) {
        super();
        this.gameController = gameController;
        this.colorOfPawns = colorOfPawns;
    }

    public void render() {
        this.setPadding(new Insets(7, 7, 7, 7));
        this.setSpacing(10);
        this.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, null, null)));

        this.getChildren().addAll(new GameType(this.gameController), new MockSituation(this.gameController), new RestartButton(this.gameController), new ExitButton(this.gameController));
    }

    class GameType extends SplitMenuButton {
        public GameType(GameController gameController) {
            super();
            this.setText("New game");

            MenuItem choice1 = new MenuItem("Russian");
            MenuItem choice2 = new MenuItem("Polish");
            MenuItem choice3 = new MenuItem("Canadian");

            choice1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if(gameController.isAstive()) {
                        gameController.mock("simple", colorOfPawns);
                    }
                }
            });

            choice2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if(gameController.isAstive()) {
                        gameController.mock("queen", colorOfPawns);
                    }
                }
            });

            choice3.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if(gameController.isAstive()) {
                        gameController.mock("convert", colorOfPawns);
                    }
                }
            });

            this.getItems().addAll(choice1, choice2, choice3);


        }
    }

    class MockSituation extends SplitMenuButton {
        public MockSituation(GameController gameController) {
            super();
            this.setText("Mocks");

            MenuItem choice1 = new MenuItem("Simple endgame");
            MenuItem choice2 = new MenuItem("Queen endgame");
            MenuItem choice3 = new MenuItem("Pawn to queen");

            choice1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if(gameController.isAstive()) {
                        gameController.mock("simple", colorOfPawns);
                    }
                }
            });

            choice2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if(gameController.isAstive()) {
                        gameController.mock("queen", colorOfPawns);
                    }
                }
            });

            choice3.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if(gameController.isAstive()) {
                        gameController.mock("convert", colorOfPawns);
                    }
                }
            });

            this.getItems().addAll(choice1, choice2, choice3);


        }
    }

    
    class RestartButton extends Button {
        public RestartButton(GameController gameController) {
            super("Restart game");
            this.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    gameController.restartGame();
                }
            });
        }
    }

    class ExitButton extends Button {
        public ExitButton(GameController gameController) {
            super("Exit game");
            this.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.out.println("Good Bye!");
                    gameController.closeApplication(0);
                }
            });
        }
    }

}
