package com.CheckersGame.Server.GameThreadHandlers;

import com.CheckersGame.Server.Boards.Board;
import com.CheckersGame.Server.States.GameState;

import java.io.*;
import java.net.Socket;





/**
 * @author Jakub Musiał
 * @version 1.0
 * Multiplayer game thread handler
 */
public class MultiPlayerGame extends Game implements Runnable {
    private Socket playerWhite; /** A socket for the player WHITE */
    private CommandLine cmdWhite; /** A CommandLine class instance handling communication with the player WHITE */
    
    private Socket playerBlack; /** A socket for the player BLACK */
    private CommandLine cmdBlack; /** A CommandLine class instance handling communication with the player BLACK */
    
    
    
    /**
     * Default MultiPlayerGaem class constructor
     */
    public MultiPlayerGame () {}
    
    
    
    /**
     * MultiPlayerGame class constructor
     * @param playerWhite
     * @param playerBlack
     */
    public MultiPlayerGame (Socket playerWhite, Socket playerBlack) {
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
            this.cmdWhite = new CommandLine(this, inWhite, outWhite, "white");
            
            BufferedReader inBlack = new BufferedReader(new InputStreamReader(this.playerBlack.getInputStream()));
            PrintWriter outBlack = new PrintWriter(this.playerBlack.getOutputStream(), true);
            this.cmdBlack = new CommandLine(this, inBlack, outBlack, "black");
            
            this.cmdWhite.sendInit();
            this.cmdBlack.sendInit();

            while (true) {
                if (this.board == null) {
                    // white selects the game mode
                    System.out.println("Waiting for player WHITE to select game mode...");
                    
                    String message = this.cmdWhite.execCommand();
                    System.out.println("(white) " + message);

                    if (message.startsWith("Error")) {
                        this.cmdWhite.sendMessage(message);
                    }
                    else {
                        this.cmdWhite.sendMessage(message);
                        this.cmdBlack.sendMessage("(white) " + message);
                    }
                }
                else {
                    GameState state = this.getState();
                    if (state == GameState.WHITE) {
                        String message = this.cmdWhite.execCommand();
                        if (this.board != null) {
                            System.out.println("(white) " + message);

                            if (message.startsWith("Error")) {
                                this.cmdWhite.sendMessage(message);
                            }
                            else {
                                this.cmdWhite.sendMessage(message);
                                this.cmdBlack.sendMessage("(white) " + message);
                            }
                        }
                    }
                    else if (state == GameState.BLACK) {
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
     * Tries to move a pawn on the current board from the position `current` to the position `movement`.
     * @param rCurr
     * @param cCurr
     * @param rMov
     * @param cMov
     * @return int
     */
    @Override
    public int movePawn (int rCurr, int cCurr, int rMov, int cMov) {
        if (this.board == null) {
            return Board.UNKNOWN_ERROR;
        }

        int status = this.board.movePawn(rCurr, cCurr, rMov, cMov);
        
        if (status == Board.WHITE_WINS) {
            try {
                String message = String.format("Game ended: WHITE wins");
    
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
                String message = String.format("Game ended: BLACK wins");
    
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
}
