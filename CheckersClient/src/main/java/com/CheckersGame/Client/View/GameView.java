package com.CheckersGame.Client.View;

import com.CheckersGame.Client.GameController;
import com.CheckersGame.Client.View.GameViewComponents.Creditsbar;
import com.CheckersGame.Client.View.GameViewComponents.GameBoard;
import com.CheckersGame.Client.View.GameViewComponents.GameInfo;
import com.CheckersGame.Client.View.GameViewComponents.Toolbar;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;




/**
 * @author Krzysztof Dobrucki
 * @author Jakub Musiał
 * @version 1.0
 * Game view application main class
 */
public class GameView extends BorderPane {
    private Stage stage;
    private GameController gameController;

    private final static int WIDTH = 1040;
    private final static int HEIGHT = 760;

    private Toolbar toolbar;
    public GameBoard board;
    private GameInfo gameInfo;
    private Creditsbar creditsbar;
    private Label welcomeLabel;
    private Label renderingLabel;


    /**
     * GameView class constructor
     */
    public GameView (Stage stage, GameController gameController) {
        this.stage = stage;
        this.gameController = gameController;

        this.stage.setOnCloseRequest(e -> gameController.closeApplication(0));
        this.stage.setTitle("Checkers client");
        this.stage.show();
    }

    
    /**
     * Renders Toolbar
     */
    private void renderToolbar () {
        this.toolbar = new Toolbar(this.gameController);
        this.toolbar.render();
    }


    /**
     * Renders GameInfo
     */
    private void renderGameInfo () {
        this.gameInfo = new GameInfo(this.gameController, this);
        this.gameInfo.render();
    }


    /**
     * Renders Creditsbar
     */
    private void renderCreditsbar () {
        this.creditsbar = new Creditsbar();
        this.creditsbar.render();
    }


    /**
     * Renders WelcomeLabel
     */
    private void renderWelcomeLabel () {
        this.welcomeLabel = new Label("Welcome to the Checkers Game!");
        this.welcomeLabel.setFont(Font.font("Monospace", 36));
        this.welcomeLabel.setAlignment(Pos.CENTER);
    }


    /**
     * Renders RenderingLabel
     */
    private void renderRenderingLabel () {
        this.renderingLabel = new Label("Waiting for board render...");
        this.renderingLabel.setFont(Font.font("Monospace", 36));
        this.renderingLabel.setAlignment(Pos.CENTER);
    }


    /**
     * Renders Board
     */
    public void renderBoard (String description) {
        this.setLeft(this.renderingLabel);
        this.board = new GameBoard(description, this.gameController);
        this.board.render();
        this.setLeft(this.board);
    }


    /**
     * Starts renders and adds to stage
     */
    public void render () {
        this.stage.setScene(new Scene(this));
        this.stage.setHeight(HEIGHT);
        this.stage.setWidth(WIDTH);
        this.stage.setResizable(false);
        this.setBackground(new Background(new BackgroundFill(Color.CORNSILK, null, getInsets())));

        this.renderWelcomeLabel();
        this.renderToolbar();
        this.renderCreditsbar();
        this.renderGameInfo();
        this.renderRenderingLabel();
  
        this.setTop(this.toolbar);
        this.setBottom(this.creditsbar);
        this.setLeft(this.welcomeLabel);
        this.setRight(this.gameInfo);
    }


    /**
     * Updates GameLog
     */
    public void updateGameLog (String log) {
        this.gameInfo.updateLog(log);
    }


    /**
     * Sets Player
     */
    public void setPlayer (String player) {
        this.gameInfo.setPlayer(player);
    }
}