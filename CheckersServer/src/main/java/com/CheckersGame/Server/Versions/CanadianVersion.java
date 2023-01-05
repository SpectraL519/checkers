package com.CheckersGame.Server.Versions;

import com.CheckersGame.Server.Boards.*;





/**
 * @author Jakub Musiał
 * @version 1.0
 * Russian checkers factory class
 */
public class CanadianVersion extends GameVersion {
    /**
     * CanadianVersion class constructor
     */
    public CanadianVersion () {
        this.board = new CanadianBoard();
    }



    /**
     * Reinstantiates and reinitializes the canadian checkers board
     */
    @Override
    public void resetBoard () {
        this.board = new CanadianBoard();
        this.board.init();
    }
}
