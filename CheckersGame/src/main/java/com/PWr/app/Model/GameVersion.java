package com.PWr.app.Model;





// This is a factory class
public abstract class GameVersion {
    protected int boardSize;
    protected int pawnLines;
    protected int[][] board;



    public void initPawns() {
        // Pawns:
        // 0 - no pawn
        // 1 - white
        // 10 - white queen
        // 2 - black
        // 20 - black queen

        // Init white pawns
        int limit = this.boardSize - this.pawnLines - 1;
        for (int i = this.boardSize - 1; i > limit; i--) {
            int startPos = (i + 1) % 2;
            for (int j = startPos; j < this.boardSize; j += 2) {
                this.board[i][j] = 1;
            }
        }
        
        // Init black pawns
        for (int i = 0; i < this.pawnLines; i++) {
            int startPos = (i + 1) % 2;
            for (int j = startPos; j < this.boardSize; j += 2) {
                this.board[i][j] = 2;
            }
        }
    }



    public void displayPawns() {
        for (int i = 0; i < this.boardSize; i++) {
            for (int j = 0; j < this.boardSize; j++) {
                System.out.printf("%d ", this.board[i][j]);
            }
            System.out.printf("\n");
        }
    }
}
