package com.PWr.app.View;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;





public class GameBoard extends GridPane {
    private int boardSize;
    private int pixelSize;



    public GameBoard (int boardSize, int pixelSize) {
        super();

        this.boardSize = boardSize;
        this.pixelSize = pixelSize;

        this.setMinWidth(pixelSize);
        this.setMaxWidth(pixelSize);
        this.setMinHeight(pixelSize);
        this.setMaxHeight(pixelSize);
    }



    public void render () {
        Color colors[] = {Color.BEIGE, Color.DARKGREEN};

        for (int i = 0; i < this.boardSize; i++) {
            int start = i % 2;
            for (int j = 0; j < this.boardSize; j++) {
                this.add(new Field(colors[(start + j) % 2], this.pixelSize / this.boardSize), i, j);
            }
        }
    }

    public void movePawn () {}
}
