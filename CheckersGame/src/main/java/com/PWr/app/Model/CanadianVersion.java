package com.PWr.app.Model;





public class CanadianVersion extends GameVersion {
    public CanadianVersion () {
        this.boardSize = 12;
        this.pawnLines = 5;
        this.board = new int[this.boardSize][this.boardSize];
    }



    @Override
    public int checkMove (int xCurr, int yCurr, int xMov, int yMov) {
        return 0;
    }
}
