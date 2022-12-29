package com.PWr.app.Server;

import java.io.*;
import java.net.Socket;

import com.PWr.app.Server.Boards.Board;
import com.PWr.app.Server.States.GameState;
import com.PWr.app.Server.Versions.*;





public class Game implements Runnable {
    private Socket playerWhite;
    private Socket playerBlack;

    private GameVersion version;



    public Game () {}

    public Game (Socket playerWhite, Socket playerBlack) {
        this.playerWhite = playerWhite;
        this.playerBlack = playerBlack;
    }



    @Override
    public void run () {
        try {
            BufferedReader inWhite = new BufferedReader(new InputStreamReader(playerWhite.getInputStream()));
            PrintWriter outWhite = new PrintWriter(playerWhite.getOutputStream(), true);

            BufferedReader inBlack = new BufferedReader(new InputStreamReader(playerBlack.getInputStream()));
            PrintWriter outBlack = new PrintWriter(playerBlack.getOutputStream(), true);



            outWhite.println("Playing as white!\nTo start enter `newGame <version>`");
            outBlack.println("Playing as black!\nWaiting for the game to start...");



            while (true) {
                // TODO
            }
        }
        catch (IOException e) {
            System.err.println("IOError:");
            e.printStackTrace();
        }
    }
 


    public void newGame (String v) {
        switch (v) {
            case "polish": {
                System.out.println("Polish version has been selected");
                this.version = new PolishVersion();
                break;
            }

            case "russian": {
                System.out.println("Russian version has been selected");
                this.version = new RussianVersion();
                break;
            }

            case "canadian": {
                System.out.println("Canadian version has been selected");
                this.version = new CanadianVersion();
                break;
            }

            default: {
                System.out.println("Invalid game version specified!");
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



    public void displayBoard () {
        this.version.displayBoard();
    }



    public Board getBoard () {
        return this.version.getBoard();
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
