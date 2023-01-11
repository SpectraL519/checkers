package com.CheckersGame.Server;

import com.CheckersGame.Server.Boards.Board;
import com.CheckersGame.Server.States.GameState;
import com.CheckersGame.Server.Versions.*;

import java.io.*;
import java.net.Socket;





/**
 * @author Jakub Musiał
 * @version 1.0
 * Class handling the game thread
 */
public class Game implements Runnable {
    private Socket playerWhite; /** A socket for the player WHITE */
    private CommandLine cmdWhite; /** A CommandLine class instance handling communication with the player WHITE */
    
    private Socket playerBlack; /** A socket for the player BLACK */
    private CommandLine cmdBlack; /** A CommandLine class instance handling communication with the player BLACK */

    private GameVersion version; /** A game board factory class object */
    private Board board; /** The game board */



    /**
     * Default Game class constructor
     */
    public Game () {}



    /**
     * Game class constructor
     * @param playerWhite
     * @param playerBlack
     */
    public Game (Socket playerWhite, Socket playerBlack) {
        this.playerWhite = playerWhite;
        this.playerBlack = playerBlack;
    }



    /** 
     * Game thread (game loop) handling method 
     */
    @Override
    public void run () {
        try {
            BufferedReader inWhite = new BufferedReader(new InputStreamReader(this.playerWhite.getInputStream()));
            PrintWriter outWhite = new PrintWriter(this.playerWhite.getOutputStream(), true);
            this.cmdWhite = new CommandLine(this, inWhite, outWhite);
            
            BufferedReader inBlack = new BufferedReader(new InputStreamReader(this.playerBlack.getInputStream()));
            PrintWriter outBlack = new PrintWriter(this.playerBlack.getOutputStream(), true);
            this.cmdBlack = new CommandLine(this, inBlack, outBlack);

            this.cmdWhite.sendInit("white");
            this.cmdBlack.sendInit("black");



            while (true) {
                if (this.board == null) {
                    // white selects the game mode
                    System.out.println("Waiting for player WHITE to select game mode...");
                    
                    try {
                        String message = this.cmdWhite.execCommand();
                        System.out.println("(white) " + message);

                        if (message.startsWith("Error") || message.startsWith("Checkers console app commands:")) {
                            this.cmdWhite.sendMessage(message);
                        }
                        else {
                            this.cmdWhite.sendMessage(message);
                            this.cmdBlack.sendMessage("(white) " + message);
                        }
                    }
                    catch (IOException e) {
                        System.out.println("Error: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
                else {
                    GameState state = this.getState();
                    if (state == GameState.WHITE) {
                        try {
                            String message = this.cmdWhite.execCommand();
                            if (this.board != null) {
                                System.out.println("(white) " + message);

                                if (message.startsWith("Error") || message.startsWith("Checkers console app commands:")) {
                                    this.cmdWhite.sendMessage(message);
                                }
                                else {
                                    this.cmdWhite.sendMessage(message);
                                    this.cmdBlack.sendMessage("(white) " + message);
                                }
                            }
                        }
                        catch (IOException e) {
                            System.out.println("Error: " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                    else if (state == GameState.BLACK) {
                        try {
                            String message = this.cmdBlack.execCommand();
                            if (this.board != null) {
                                System.out.println("(black) " + message);

                                if (message.startsWith("Error") || message.startsWith("Checkers console app commands:")) {
                                    this.cmdBlack.sendMessage(message);
                                }
                                else {
                                    this.cmdWhite.sendMessage(("(black) ") + message);
                                    this.cmdBlack.sendMessage(message);
                                }
                            }
                        }
                        catch (IOException e) {
                            System.out.println("Error: " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                    else {
                        this.cmdWhite.sendMessage("Error: Invalid game state!");
                        this.cmdBlack.sendMessage("Error: Invalid game state!");
                    }
                }
            }
        }
        catch (IOException e) {
            System.err.println("IOError: " + e.getMessage());
            e.printStackTrace();
        }
    }


    
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
            return null;
        }

        return this.board.getDescription();
    }
    
    
    
    /**
     * Displays the current game board in the server's terminal
     */
    public void displayBoard () {
        if (this.board == null) {
            return;
        }

        this.board.display();
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
     * Tries to move a pawn on the current board from the position `current` to the position `movement`.
     * @param rCurr
     * @param cCurr
     * @param rMov
     * @param cMov
     * @return int
     */
    public int movePawn (int rCurr, int cCurr, int rMov, int cMov) {
        if (this.board == null) {
            return Board.UNKNOWN_ERROR;
        }

        int status = this.board.movePawn(rCurr, cCurr, rMov, cMov);
        
        if (status == Board.WHITE_WINS) {
            try {
                String message = String.format("Game ended: WHITE wins ==> Pawn moved: (%d,%d) -> (%d,%d)", rCurr, cCurr, rMov, cMov);
    
                this.cmdWhite.sendMessage(message);
                this.cmdBlack.sendMessage("(white) " + message);
            }
            catch (IOException e) {
                System.err.println("IOError: " + e.getMessage());
                e.printStackTrace();
            }
            catch (NullPointerException e) {}

            this.end();
        }
        else if (status == Board.BLACK_WINS) {
            try {
                String message = String.format("Game ended: BLACK wins ==> Pawn moved: (%d,%d) -> (%d,%d)", rCurr, cCurr, rMov, cMov);
    
                this.cmdWhite.sendMessage("(black) " + message);
                this.cmdBlack.sendMessage(message);
            }
            catch (IOException e) {
                System.err.println("IOError: " + e.getMessage());
                e.printStackTrace();
            }
            catch (NullPointerException e) {}

            this.end();
        }

        return status;
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
     * Returns the longest possible move a player can make by moving a pawn on the position (`r`, `c`)
     * @param r
     * @param c
     * @return int
     */
    public int longestMove (int r, int c) {
        if (this.board == null) {
            return -1;
        }

        return this.board.longestMove(r, c);
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
