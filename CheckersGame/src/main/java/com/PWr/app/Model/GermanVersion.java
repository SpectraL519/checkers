package com.PWr.app.Model;





public class GermanVersion extends GameVersion {
    public GermanVersion () {
        this.initColor = "Beige";

        this.boardSize = 8;
        this.pawnLines = 3;
        this.board = new int[this.boardSize][this.boardSize];
    }
}
