package com.PWr.app.View;

import javafx.scene.layout.BorderPane;




public class GameView extends BorderPane {
    private Toolbar toolbar;
    private GameBoard board;



    public GameView (int boardSize) {
        // add params
        this.setPrefSize(1024, 720);

        this.toolbar = new Toolbar();
        this.toolbar.render();

        this.board = new GameBoard(8, 600);
        this.board.render();
    }

    

    public void render () {
        this.setTop(this.toolbar);
        this.setCenter(this.board);
    }

    public void renderToolbar () {}

    public void renderBoard () {}

    public void renderPawns () {}

    public void movePawn () {}
}