package com.PWr.app.Model;





public class RussianVersion extends GameVersion {
    public RussianVersion () {
        this.boardSize = 8;
        this.pawnLines = 3;
        this.board = new int[this.boardSize][this.boardSize];
    }
}
