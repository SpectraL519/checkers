package com.PWr.app.Server.Versions;

import com.PWr.app.Server.Boards.*;
import com.PWr.app.Server.States.GameState;





// This is a factory class
public abstract class GameVersion {
    protected Board board;



    public void initBoard() {
        this.board.init();
    }



    public void displayBoard() {
        this.board.display();
    }



    public Board getBoard () {
        return this.board;
    }



    public GameState getState () {
        return this.board.getState();
    }



    public int movePawn (int rCurr, int cCurr, int rMov, int cMov) {
        // < 0: move NOT ok - genereal errrors
        // 0: move NOT ok - not optimal take
        // 1: move ok but no further move is allowed
        // 2: move ok and furhter moves allowed

        return this.board.movePawn(rCurr, cCurr, rMov, cMov);
    }



    public abstract void reset ();



    public void mockEndgame (String player) {
        this.board.mockEndgame(player);
    }



    public void mockQueenEndgame (String player) {
        this.board.mockQueenEndgame(player);
    }



    public void mockPawnToQueen (String player) {
        this.board.mockPawnToQueen(player);
    }



    public int longestMove (int r, int c) {
        return this.board.longestMove(r, c);
    }
}
