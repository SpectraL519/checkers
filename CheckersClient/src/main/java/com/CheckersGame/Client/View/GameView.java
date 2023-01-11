package com.CheckersGame.Client.View;

import com.CheckersGame.Client.GameController;
import com.CheckersGame.Client.View.GameViewComponents.Creditsbar;
import com.CheckersGame.Client.View.GameViewComponents.GameBoard;
import com.CheckersGame.Client.View.GameViewComponents.GameInfo;
import com.CheckersGame.Client.View.GameViewComponents.Toolbar;

import javafx.scene.layout.BorderPane;



public class GameView extends BorderPane {

    private Toolbar toolbar;
    public GameBoard board;
    private GameInfo gameinfo;
    private Creditsbar creditsbar;
    private GameController gameController;
    private String gameLog;
    public String colorOfPawns;


    //public GameView (int boardSize, int rowOfPawns, String colorOfPawns)
    public GameView (String colorOfPawns, GameController gameController, String gameLog) {
        this.gameController = gameController;
        this.colorOfPawns = colorOfPawns;
        this.gameLog = gameLog;
        this.toolbar = new Toolbar(this.gameController, this.colorOfPawns);
        this.creditsbar = new Creditsbar();
        this.board = new GameBoard("board:8;2,0,1", this.gameController);
        this.gameinfo = new GameInfo(this.colorOfPawns, this.gameController, this, this.gameLog);
        
        
    }

    

    private void renderToolbar () {
        this.toolbar.render();
    }

    private void renderBoard () {
        this.board.render();
    }

    private void renderGameinfo () {
        this.gameinfo.render();
    }

    private void renderCreditsbar () {
        this.creditsbar.render();
    }

    // private void renderPawns () {}



    public void render () {
        this.renderToolbar();
        this.renderBoard();
        this.renderCreditsbar();
        this.renderGameinfo();
  
        this.setTop(this.toolbar);
        this.setBottom(this.creditsbar);
        this.setLeft(this.board);
        this.setRight(this.gameinfo);
    }

}