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





public class GameView extends BorderPane {
    private Stage stage;
    private GameController gameController;
    public String player;

    private final static int WIDTH = 1040;
    private final static int HEIGHT = 760;

    private Toolbar toolbar;
    public GameBoard board;
    private GameInfo gameInfo;
    private Creditsbar creditsbar;
    private Label welcomeLabel;
    private Label renderingLabel;



    public GameView (Stage stage, GameController gameController) {
        this.stage = stage;
        this.gameController = gameController;

        this.stage.setOnCloseRequest(e -> Platform.exit());
        this.stage.setTitle("Checkers client");
        this.stage.show();
    }

    

    private void renderToolbar () {
        this.toolbar = new Toolbar(this.gameController, this.player);
        this.toolbar.render();
    }



    private void renderGameInfo () {
        this.gameInfo = new GameInfo(this.gameController, this);
        this.gameInfo.render();
    }



    private void renderCreditsbar () {
        this.creditsbar = new Creditsbar();
        this.creditsbar.render();
    }



    private void renderWelcomeLabel () {
        this.welcomeLabel = new Label("Welcome to the Checkers Game!");
        this.welcomeLabel.setFont(Font.font("Monospace", 36));
        this.welcomeLabel.setAlignment(Pos.CENTER);
    }



    private void renderRenderingLabel () {
        this.renderingLabel = new Label("Waiting for board render...");
        this.renderingLabel.setFont(Font.font("Monospace", 36));
        this.renderingLabel.setAlignment(Pos.CENTER);
    }



    public void renderBoard (String description) {
        this.setLeft(this.renderingLabel);
        this.board = new GameBoard(description, this.gameController);
        this.board.render();
        this.setLeft(this.board);
    }



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



    public void updateGameLog (String log) {
        this.gameInfo.updateLog(log);
    }



    public void setPlayer (String player) {
        this.gameInfo.setPlayer(player);
    }
}