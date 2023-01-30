package com.CheckersGame.Client.View;

import com.CheckersGame.Client.GameController;
import com.CheckersGame.Client.View.GameViewComponents.Creditsbar;
import com.CheckersGame.Client.View.GameViewComponents.GameBoard;
import com.CheckersGame.Client.View.GameViewComponents.GameInfo;
import com.CheckersGame.Client.View.GameViewComponents.Toolbar;

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
        if (this.toolbar == null) {
            this.toolbar = new Toolbar(this, this.gameController);
            this.toolbar.render();
        }
        this.setTop(this.toolbar);
    }


    /**
     * Renders GameInfo
     */
    private void renderGameInfo () {
        if (this.gameInfo == null) {
            this.gameInfo = new GameInfo(this.gameController, this);
            this.gameInfo.render();
        }
        this.setRight(this.gameInfo);
    }


    /**
     * Renders Creditsbar
     */
    private void renderCreditsbar () {
        if (this.creditsbar == null) {
            this.creditsbar = new Creditsbar();
            this.creditsbar.render();
        }
        this.setBottom(this.creditsbar);
    }


    /**
     * Renders WelcomeLabel
     */
    public void renderWelcomeLabel () {
        if (this.welcomeLabel == null) {
            this.welcomeLabel = new Label("Welcome to the Checkers Game!");
            this.welcomeLabel.setFont(Font.font("Monospace", 36));
            this.welcomeLabel.setAlignment(Pos.CENTER);
        }
        this.setLeft(this.welcomeLabel);
    }


    /**
     * Renders RenderingLabel
     */
    private void renderRenderingLabel () {
        if (this.renderingLabel == null) {
            this.renderingLabel = new Label("Waiting for board render...");
            this.renderingLabel.setFont(Font.font("Monospace", 36));
        }
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