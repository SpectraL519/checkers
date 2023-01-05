package com.CheckersGame.Client.View;

import javafx.scene.layout.BorderPane;



public class GameView extends BorderPane {

    private Toolbar toolbar;
    public GameBoard board;
    private GameInfo gameinfo;
    private Creditsbar creditsbar;

    public GameView (int boardSize, int rowOfPawns) {
        // add params
        this.toolbar = new Toolbar();
        this.creditsbar = new Creditsbar();
        this.board = new GameBoard(boardSize, 600, rowOfPawns);
        this.gameinfo = new GameInfo();
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