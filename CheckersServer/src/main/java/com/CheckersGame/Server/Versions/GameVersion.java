package com.CheckersGame.Server.Versions;

import com.CheckersGame.Server.Boards.*;
import com.CheckersGame.Server.States.GameState;





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



    public String getBoardDescription () {
        return this.board.getDescription();
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



    public String getMoveMessage (int status) {
        return this.board.getMoveMessage(status);
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
