package com.CheckersGame.Client.View;

import com.CheckersGame.Client.GameController;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.application.Platform;



public class AppView {
    private Stage stage;
    public final static int WIDTH = 1040;
    public final static int HEIGHT = 760;


    private GameView gameView;
    public String colorOfPawns;
    public GameController gameController;
    private String gameLog;

    public AppView(Stage stage, GameController gameController) {
        this.stage = stage;
        this.gameController = gameController;

        System.out.println("AppView: checkpoint 1");

        this.stage.setOnCloseRequest(e -> Platform.exit());
        this.stage.setTitle("Checkers client");
        this.stage.show();

        System.out.println("AppView: checkpoint 2");

        this.gameView = null;
        this.gameLog = "";
    }

    public void renderGameView() {
        System.out.println("AppView: checkpoint 11");
        this.gameView = new GameView(colorOfPawns, this.gameController, this.gameLog);
        this.gameView.render();
        this.stage.setScene(new Scene(this.gameView));
        this.stage.setHeight(HEIGHT);
        this.stage.setWidth(WIDTH);
        this.stage.setResizable(false);
        System.out.println("AppView: checkpoint 12");
    }

    public void setPlayer(String color) {
        this.colorOfPawns = color;
    }

    public void updateLog (String message) {
        if(this.stage.getScene().getRoot() instanceof GameView) {
            this.gameLog = this.gameLog + "\n" + message;
        }
    }
}