package com.CheckersGame.Client.View.GameViewComponents;

public class MoveManager {

    public int oldRow;
    public int oldColumn;

    public int newRow;
    public int newColumn;

    public GameBoard board;

    public MoveManager(GameBoard board) {
        this.board = board;

        this.oldColumn = 0;
        this.oldRow = 0;
        this.newColumn = 0;
        this.newRow = 0;
    }

    public void askController(int row, int column) {
            if (oldColumn == 0 && oldRow == 0) {
                if (board.board[column][row].whitePawn.isVisible() || board.board[column][row].blackPawn.isVisible()) {
                oldColumn = column;
                oldRow = row;
                }
            } else {
                newColumn = column;
                newRow = row;
                makeMove();
            }
    }

    private void makeMove() {
        if(board.board[oldColumn][oldRow].whitePawn.isVisible()){
            board.board[oldColumn][oldRow].whitePawn.setVisible(false);
            board.board[newColumn][newRow].whitePawn.setVisible(true);
            board.board[newColumn][newRow].blackPawn.setVisible(false);
        }
        else{
            board.board[oldColumn][oldRow].blackPawn.setVisible(false);
            board.board[newColumn][newRow].blackPawn.setVisible(true);
            board.board[newColumn][newRow].whitePawn.setVisible(false);
        }
        this.oldColumn = 0;
        this.oldRow = 0;
        this.newColumn = 0;
        this.newRow = 0;
    }
}
