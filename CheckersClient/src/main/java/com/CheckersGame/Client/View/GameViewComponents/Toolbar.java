package com.CheckersGame.Client.View.GameViewComponents;

import com.CheckersGame.Client.View.GameView;
import com.CheckersGame.Client.GameController;

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




/**
 * @author Krzysztof Dobrucki
 * @author Jakub Musiał
 * @version 1.0
 * Toolbar class
 */
public class Toolbar extends HBox {
    private GameView view;
    private GameController gameController;

    private GameMode gameModeButton;
    private GameType newGameButton;
    private MockSituation mockMenu;
    private RestartButton restartButton;
    private ExitButton exitButton;


    /**
     * Toolbar class constructor
     * @param view
     * @param gameController
     */
    public Toolbar(GameView view, GameController gameController) {
        super();
        this.view = view;
        this.gameController = gameController;
    }


    /**
     * Renders Toolbar
     */
    public void render() {
        this.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, getInsets())));

        this.setPadding(new Insets(7, 7, 7, 7));
        this.setSpacing(10);
        this.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, null, null)));

        this.gameModeButton = new GameMode(this.view, this.gameController);
        this.newGameButton = new GameType(this.gameController);
        this.mockMenu = new MockSituation(this.gameController);
        this.restartButton = new RestartButton(this.gameController);
        this.exitButton = new ExitButton(this.gameController);

        this.getChildren().addAll(this.gameModeButton, this.newGameButton, this.mockMenu, this.restartButton, this.exitButton);
    }



    /**
     * Game mode selection menu
     */
    class GameMode extends SplitMenuButton {
        /**
         * GameMode class constructor
         * @param view
         * @param gameController
         */
        public GameMode (GameView view, GameController gameController) {
            super();

            this.setText("Game mode");
            
            MenuItem singlePlayer = new MenuItem("Single player");
            singlePlayer.setOnAction(new EventHandler <ActionEvent> () {
                @Override
                public void handle (ActionEvent event) {
                    view.renderWelcomeLabel();
                    gameController.startModel("singleplayer");
                }
            });

            MenuItem multiPlayer = new MenuItem("Multi player");
            multiPlayer.setOnAction(new EventHandler <ActionEvent> () {
                @Override
                public void handle (ActionEvent event) {
                    view.renderWelcomeLabel();
                    gameController.startModel("multiplayer");
                }
            });

            this.getItems().addAll(singlePlayer, multiPlayer);
        }
    }


    /**
     * GameType class contains game types
     */
    class GameType extends SplitMenuButton {
        public GameType(GameController gameController) {
            super();
            this.setText("New game");

            MenuItem choice1 = new MenuItem("Russian checkers");
            MenuItem choice2 = new MenuItem("Polish checkers");
            MenuItem choice3 = new MenuItem("Canadian checkers");

            choice1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    gameController.newGame("russian");
                }
            });

            choice2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    gameController.newGame("polish");
                }
            });

            choice3.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    gameController.newGame("canadian");
                }
            });

            this.getItems().addAll(choice1, choice2, choice3);
        }
    }


    /**
     * MockSituation class contains mock types
     */
    class MockSituation extends SplitMenuButton {
        private MenuItem simple;
        private MenuItem queen;
        private MenuItem convert;

        public MockSituation(GameController gameController) {
            super();
            this.setText("Mocks");

            this.simple = new MenuItem("Simple endgame");
            this.queen = new MenuItem("Queen endgame");
            this.convert = new MenuItem("Pawn to queen");

            this.simple.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if(gameController.isAstive()) {
                        gameController.mock("simple");
                    }
                }
            });

            this.queen.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if(gameController.isAstive()) {
                        gameController.mock("queen");
                    }
                }
            });

            this.convert.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if(gameController.isAstive()) {
                        gameController.mock("convert");
                    }
                }
            });

            this.getItems().addAll(this.simple, this.queen, this.convert);
        }
    }

    
    /**
     * RestartButton class restarts game
     */
    class RestartButton extends Button {
        public RestartButton(GameController gameController) {
            super("Restart game");
            this.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (gameController.isAstive()) {
                        gameController.restartGame();
                    }
                }
            });
        }
    }


    /**
     * ExitButton class closes app
     */
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
