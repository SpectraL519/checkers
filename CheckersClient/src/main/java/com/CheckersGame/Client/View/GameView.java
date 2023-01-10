package com.CheckersGame.Client.View;

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
    public String colorOfPawns;

    public GameView (int boardSize, int rowOfPawns, String colorOfPawns) {
        // add params
        this.toolbar = new Toolbar();
        this.creditsbar = new Creditsbar();
        this.board = new GameBoard(boardSize, 600, rowOfPawns);
        this.gameinfo = new GameInfo(colorOfPawns);
        this.colorOfPawns = colorOfPawns;
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