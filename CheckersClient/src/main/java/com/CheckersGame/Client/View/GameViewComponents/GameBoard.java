package com.CheckersGame.Client.View.GameViewComponents;

import com.CheckersGame.Client.GameController;
import com.CheckersGame.Client.View.GameViewComponents.Fields.CenterField;
import com.CheckersGame.Client.View.GameViewComponents.Fields.CornerField;
import com.CheckersGame.Client.View.GameViewComponents.Fields.Field;
import com.CheckersGame.Client.View.GameViewComponents.Fields.PositionField;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;





/**
 * @author Krzysztof Dobrucki
 * @version 1.0
 * GameBoard class
 */
public class GameBoard extends GridPane {

    private int boardSize;
    private int pixelSize;
    public MoveManager controller;
    public Field[][] board;
    public String[] descriptionArray;
    

    /**
     * GameBoard class constructor
     * @param boardDescription
     * @param gameController
     */
    public GameBoard (String boardDescription, GameController gameController) {
        super();

        if (!boardDescription.startsWith("board:")) {
            System.out.println("Error: Invalid board description");
            return;
        }

        boardDescription = boardDescription.replace("board:", "");

        this.descriptionArray = boardDescription.split(";");
        this.boardSize = Integer.parseInt(descriptionArray[0]);

        this.pixelSize = 600;
        this.controller = new MoveManager(this);
        this.board = new Field[this.boardSize+2][this.boardSize+2];

        this.setMinWidth(pixelSize);
        this.setMaxWidth(pixelSize);
        this.setMinHeight(pixelSize);
        this.setMaxHeight(pixelSize);
    }


    /**
     * Shows game board
     */
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
        //show pawns
        //1-white; 2-black; 10-whiteQueen; 20-blackQueen
        int length = descriptionArray.length;
        
        for (int i = 1; i < length; i++) {
            String pawnDescription[] = descriptionArray[i].split(",");

            int row = Integer.parseInt(pawnDescription[0]);
            int column = Integer.parseInt(pawnDescription[1]);
            int pawnType = Integer.parseInt(pawnDescription[2]);

            if(pawnType == 1){
                board[column+1][row+1].showWhitePawn(true);
            }else if(pawnType == 2){
                board[column+1][row+1].showBlackPawn(true);
            }else if(pawnType == 10){
                board[column+1][row+1].showWhitePawnQueen(true);
            }else if(pawnType == 20){
                board[column+1][row+1].showBlackPawnQueen(true);
            }else{
                System.out.println("Error: Invalid type of pawn!");
            }
        }

    }

}
