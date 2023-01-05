package com.CheckersGame.Server.Versions;

import com.CheckersGame.Server.Boards.*;





/**
 * @author Jakub Musiał
 * @version 1.0
 * Russian checkers factory class
 */
public class PolishVersion extends GameVersion {
    /**
     * PolishVersion class constructor
     */
    public PolishVersion () {
        this.board = new PolishBoard();
    }



    /**
     * Reinstantiates and reinitializes the polish checkers board
     */
    @Override
    public void resetBoard () {
        this.board = new PolishBoard();
        this.board.init();
    }
}
