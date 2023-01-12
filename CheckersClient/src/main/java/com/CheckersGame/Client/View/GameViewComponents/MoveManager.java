package com.CheckersGame.Client.View.GameViewComponents;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

public class MoveManager {

    public int oldRow;
    public int oldColumn;

    public int newRow;
    public int newColumn;

    public GameBoard board;

    public MoveManager(GameBoard board) {
        this.board = board;

        this.oldColumn = -1;
        this.oldRow = -1;
        this.newColumn = -1;
        this.newRow = -1;
    }

    public void askController(int row, int column) {
            if (oldColumn == -1 && oldRow == -1) {
                if (board.board[column][row].whitePawn.isVisible() || board.board[column][row].blackPawn.isVisible() || board.board[column][row].whitePawnQueen.isVisible() || board.board[column][row].blackPawnQueen.isVisible()) {
                    oldColumn = column;
                    oldRow = row;
                    this.board.board[column][row].setBackground(new Background(new BackgroundFill(Color.GOLDENROD, null, null)));
                }
            } else {
                newColumn = column;
                newRow = row;
                this.board.board[column][row].setBackground(new Background(new BackgroundFill(Color.GOLDENROD, null, null)));
            }
    }

    public int[] makeMove() {
        int[] coordinatres = new int[] {this.oldRow - 1, this.oldColumn - 1 , this.newRow - 1, this.newColumn - 1};
        this.oldColumn = -1;
        this.oldRow = -1;
        this.newColumn = -1;
        this.newRow = -1;
        return coordinatres;
    }
}
