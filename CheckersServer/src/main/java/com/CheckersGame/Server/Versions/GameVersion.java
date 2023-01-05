package com.CheckersGame.Server.Versions;

import com.CheckersGame.Server.Boards.*;





/**
 * @author Jakub Musiał
 * @version 1.0
 * The abstract factory class used to generate Board class instances
 */
public abstract class GameVersion {
    protected Board board; /** The board class instance */



    /**
     * Initializes the game board
     */
    public void initBoard() {
        this.board.init();
    }



    /** 
     * Returns the instance of the Board class
     * @return Board
     */
    public Board getBoard () {
        return this.board;
    }



    /**
     * Reinstantiates and reinitializes the game board
     */
    public abstract void resetBoard ();
}
