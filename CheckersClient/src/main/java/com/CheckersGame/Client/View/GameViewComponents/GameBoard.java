package com.CheckersGame.Client.View.GameViewComponents;

import com.CheckersGame.Client.View.GameViewComponents.Fields.CenterField;
import com.CheckersGame.Client.View.GameViewComponents.Fields.CornerField;
import com.CheckersGame.Client.View.GameViewComponents.Fields.Field;
import com.CheckersGame.Client.View.GameViewComponents.Fields.PositionField;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;



public class GameBoard extends GridPane {

    private int boardSize;
    private int pixelSize;
    private int rowOfPawns;
    public MoveManager controller;
    public Field[][] board;

    public GameBoard (int boardSize, int pixelSize, int rowOfPawns) {
        super();

        this.boardSize = boardSize;
        this.pixelSize = pixelSize;
        this.rowOfPawns = rowOfPawns;
        this.controller = new MoveManager(this);
        this.board = new Field[this.boardSize+2][this.boardSize+2];

        this.setMinWidth(pixelSize);
        this.setMaxWidth(pixelSize);
        this.setMinHeight(pixelSize);
        this.setMaxHeight(pixelSize);
    }



    public void render () {
        
        Color colorsCenter[] = {Color.BEIGE, Color.DARKGREEN};
        Color colorsBorders[] = {Color.BEIGE, Color.DARKSEAGREEN};
        //Add Corners
        board[0][0] = new CornerField(colorsBorders[0], 25, 25);
        board[0][this.boardSize+1] = new CornerField(colorsBorders[1], 25, 25);
        board[this.boardSize+1][0] = new CornerField(colorsBorders[1], 25, 25);
        board[this.boardSize+1][this.boardSize+1] = new CornerField(colorsBorders[0], 25, 25);
        //Add Positions
        for(int i=1; i<this.boardSize+1; i++){
            //Top
            board[i][0] = new PositionField(colorsBorders[i%2], 25, this.pixelSize/this.boardSize, i);
        }
        for(int i=1; i<this.boardSize+1; i++){
            //Bottom
            board[i][this.boardSize+1] = new PositionField(colorsBorders[(i-1)%2], 25, this.pixelSize/this.boardSize, i);
        }
        for(int i=1; i<this.boardSize+1; i++){
            //Left
            board[0][i] = new PositionField(colorsBorders[i%2], this.pixelSize/this.boardSize, 25, this.boardSize+1-i);
        }
        for(int i=1; i<this.boardSize+1; i++){
            //Right
            board[this.boardSize+1][i] = new PositionField(colorsBorders[(i-1)%2], this.pixelSize/this.boardSize, 25, this.boardSize+1-i);
        }
        //Add Center
        for (int i = 1; i < this.boardSize+1; i++) {
            int start = i % 2;
            for (int j = 1; j < this.boardSize+1; j++) {
                board[i][j] = new CenterField(colorsCenter[(start + j) % 2], this.pixelSize / this.boardSize, this.pixelSize / this.boardSize, j, i, this);
            }
        }
        //Add Fields
        for (int i = 0; i < this.boardSize+2; i++) {
            for (int j = 0; j < this.boardSize+2; j++) {
                this.add(board[i][j], i, j);
            }
        }
        //Add start position of black pawns
        for(int i = 1; i <= this.rowOfPawns; i++){
            for(int j = 1; j <= this.boardSize; j++){
                if((i%2==1 && j%2==0) || (i%2==0 && j%2==1))
                board[j][i].showBlackPawn(true);
            }
        }
        //Add start position of white pawns
        for(int i = this.boardSize - this.rowOfPawns + 1; i <= this.boardSize; i++){
            for(int j = 1; j <= this.boardSize; j++){
                if((i%2==0 && j%2==1) || (i%2==1 && j%2==0))
                board[j][i].showWhitePawn(true);
            }
        }

    }

}
