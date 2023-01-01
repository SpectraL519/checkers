package com.CheckersGame.Server;

import com.CheckersGame.Server.Boards.Board;
import com.CheckersGame.Server.States.GameState;
import com.CheckersGame.Server.Versions.*;

import java.io.*;
import java.net.Socket;





public class Game implements Runnable {
    private Socket playerWhite;
    private BufferedReader inWhite;
    private PrintWriter outWhite;
    private CommandLine cmdWhite;
    
    private Socket playerBlack;
    private BufferedReader inBlack;
    private PrintWriter outBlack;
    private CommandLine cmdBlack;

    private GameVersion version;



    public Game () {}

    public Game (Socket playerWhite, Socket playerBlack) {
        this.playerWhite = playerWhite;
        this.playerBlack = playerBlack;
    }



    @Override
    public void run () {
        try {
            this.inWhite = new BufferedReader(new InputStreamReader(this.playerWhite.getInputStream()));
            this.outWhite = new PrintWriter(this.playerWhite.getOutputStream(), true);
            this.cmdWhite = new CommandLine(this, this.inWhite, this.outWhite);
            
            this.inBlack = new BufferedReader(new InputStreamReader(this.playerBlack.getInputStream()));
            this.outBlack = new PrintWriter(this.playerBlack.getOutputStream(), true);
            this.cmdBlack = new CommandLine(this, this.inBlack, this.outBlack);

            this.outWhite.println("Playing as white!\nTo start enter `newGame <version>`");
            this.outBlack.println("Playing as black!\nWaiting for the game to start...");



            while (true) {
                if (this.version == null) {
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
                    else if (state == GameState.BLACK) {
                        try {
                            String message = this.cmdBlack.execCommand();
                            System.out.println("(black) " + message);
    
                            if (message.startsWith("Error") || message.startsWith("Checkers console app commands:")) {
                                this.cmdBlack.sendMessage(message);
                            }
                            else {
                                this.cmdWhite.sendMessage(("(black) ") + message);
                                this.cmdBlack.sendMessage(message);
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



    public String getMoveMessage (int status) {
        return this.version.getMoveMessage(status);
    }
 


    public void newGame (String v) {
        switch (v) {
            case "polish": {
                this.version = new PolishVersion();
                break;
            }

            case "russian": {
                this.version = new RussianVersion();
                break;
            }

            case "canadian": {
                this.version = new CanadianVersion();
                break;
            }

            default: {
                System.out.println("Error: Invalid game version specified!");
                break;
            }
        }
    }



    public GameVersion getVersion () {
        return this.version;
    }



    public void initBoard () {
        this.version.initBoard();
    }



    public Board getBoard () {
        return this.version.getBoard();
    }



    public String getBoardDescription () {
        return this.version.getBoardDescription();
    }
    
    
    
    public void displayBoard () {
        this.version.displayBoard();
    }



    public GameState getState () {
        return this.getBoard().getState();
    }



    public int movePawn (int rCurr, int cCurr, int rMov, int cMov) {
        int status = this.version.movePawn(rCurr, cCurr, rMov, cMov);
        
        if (status == 10 || status == 20) {
            this.endGame();
        }

        return status;
    }



    public void endGame () {
        this.version = null;
    }



    public void restartGame () {
        this.version.reset();
    }



    public void mockEndgame (String player) {
        this.version.mockEndgame(player);
    }



    public void mockQueenEndgame (String player) {
        this.version.mockQueenEndgame(player);
    }



    public void mockPawnToQueen (String player) {
        this.version.mockPawnToQueen(player);
    }



    public int longestMove (int r, int c) {
        return this.version.longestMove(r, c);
    }
}
