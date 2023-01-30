package com.CheckersGame.Server.GameThreadHandlers;

import com.CheckersGame.Server.Boards.Board;
import com.CheckersGame.Server.States.GameState;
import com.CheckersGame.Server.Versions.*;





/**
 * @author Jakub Musiał
 * @version 2.0
 * Game thread handelr (abstract)
 */
public abstract class Game {
    protected GameVersion version; /** A game board factory class object */
    protected Board board; /** The game board */



    /**
     * Default Game class constructor
     */
    public Game () {}



    /**
     * Pawn movement handling method (abstract)
     * @param rCurr
     * @param cCurr
     * @param rMov
     * @param cMov
     * @return int
     */
    public abstract int movePawn (int rCurr, int cCurr, int rMov, int cMov);


    
    /** 
     * Returns the current game version
     * @return GameVersion
     */
    public GameVersion getVersion () {
        return this.version;
    }


    
    /** 
     * Returns the current game board
     * @return Board
     */
    public Board getBoard () {
        return this.board;
    }


    
    /** 
     * Returns a description of the current game board in the following format:
     * `boardSize;pawn_1_raw,pawn_1_column,pawn_1_type;(...);pawn_n_raw,pawn_n_column,pawn_n_type`
     * @return String
     */
    public String getBoardDescription () {
        if (this.board == null) {
            return "board: null";
        }

        return this.board.getDescription();
    }


    
    /** 
     * Returns the current game state
     * @return GameState
     */
    public GameState getState () {
        if (this.board == null) {
            return null;
        }

        return this.getBoard().getState();
    }



    /** 
     * Starts a new game of version `v` and initiates the game board
     * @param v
     */
    public void newGame (String v) {
        switch (v) {
            case "polish": {
                this.version = new PolishVersion();
                this.board = this.version.getBoard();
                this.board.init();
                break;
            }

            case "russian": {
                this.version = new RussianVersion();
                this.board = this.version.getBoard();
                this.board.init();
                break;
            }

            case "canadian": {
                this.version = new CanadianVersion();
                this.board = this.version.getBoard();
                this.board.init();
                break;
            }

            default: {
                System.out.println("Error: Invalid game version specified!");
                break;
            }
        }
    }



    /** 
     * Returns a move message for a given status
     * @param status
     * @return String
     */
    public String getMoveMessage (int status) {
        if (this.board == null) {
            return null;
        }

        return this.board.getMoveMessage(status);
    }



    /**
     * Ends the current game (if a game has been started)
     */
    public void end () {
        this.board = null;
    }



    /** 
     * Restarts the current game (if a game has been started)
     */
    public void restart () {
        if (this.version == null) {
            return;
        }

        this.version.resetBoard();
        this.board = this.version.getBoard();
    }


    
    /**
     * Mocks a simple endgame situation where the next (winning) move belongs to the specified `player`  
     * @param player
     */
    public void mockEndgame (String player) {
        if (this.board == null) {
            return;
        }

        this.board.mockEndgame(player);
    }



    
    /** 
     * Mocks a queen endgame situation where the next (winning) move belongs to the specified `player`  
     * @param player
     */
    public void mockQueenEndgame (String player) {
        if (this.board == null) {
            return;
        }

        this.board.mockQueenEndgame(player);
    }



    
    /** 
     * Mocks an endgame situation in which the specified `player`'s move will change a pawn to a queen
     * @param player
     */
    public void mockPawnToQueen (String player) {
        if (this.board == null) {
            return;
        }

        this.board.mockPawnToQueen(player);
    }
}
