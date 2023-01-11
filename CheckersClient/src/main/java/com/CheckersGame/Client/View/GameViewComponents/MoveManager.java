package com.CheckersGame.Client.View.GameViewComponents;

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
                if (board.board[column][row].whitePawn.isVisible() || board.board[column][row].blackPawn.isVisible()) {
                oldColumn = column;
                oldRow = row;
                }
            } else {
                newColumn = column;
                newRow = row;
                //makeMove();
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
