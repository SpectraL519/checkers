package com.CheckersGame.Server.Versions;

import com.CheckersGame.Server.Boards.*;





/**
 * @author Jakub Musiał
 * @version 1.0
 * Russian checkers factory class
 */
public class RussianVersion extends GameVersion {
    /**
     * RussianVersion class constructor
     */
    public RussianVersion () {
        this.board = new RussianBoard();
    }



    /**
     * Reinstantiates and reinitializes the russian checkers board
     */
    @Override
    public void resetBoard () {
        this.board = new RussianBoard();
        this.board.init();
    }
}
