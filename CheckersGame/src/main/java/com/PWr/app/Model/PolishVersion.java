package com.PWr.app.Model;





public class PolishVersion extends GameVersion {
    public PolishVersion () {
        this.boardSize = 10;
        this.pawnLines = 4;
        this.board = new int[this.boardSize][this.boardSize];
    }
}
