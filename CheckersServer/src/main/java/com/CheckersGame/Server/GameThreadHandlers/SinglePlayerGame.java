package com.CheckersGame.Server.GameThreadHandlers;

import com.CheckersGame.Server.Boards.Board;

import java.io.*;
import java.net.Socket;
import java.util.*;





/**
 * @author Jakub Musiał
 * @version 1.0
 * Singleplayer game thread handler
 */
public class SinglePlayerGame extends Game implements Runnable {
    private Socket playerSocket; /** A socket for the player */
    private CommandLine cmd; /** A CommandLine class instance handling communication with the player */
    


    /**
     * SinglePlayerGame class constructor
     * @param playerWhite
     * @param playerBlack
     */
    public SinglePlayerGame (Socket playerSocket) {
        this.playerSocket = playerSocket;
    }



    private ArrayList <String> getRandomColors () {
        ArrayList <String> colors = new ArrayList<>();
        colors.add("white");
        colors.add("black");
        
        Collections.shuffle(colors, new Random());

        return colors;
    }



    /** 
     * Game thread (game loop) handling method 
     */
    @Override
    public void run () {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(this.playerSocket.getInputStream()));
            PrintWriter output = new PrintWriter(this.playerSocket.getOutputStream(), true);            
            
            ArrayList <String> colors = this.getRandomColors();
            String player = colors.get(0);
            String bot = colors.get(1);
            
            this.cmd = new CommandLine(this, input, output, player);
            this.cmd.sendInit();

            
            while (true) {
                if (this.board == null) {
                    System.out.println("Waiting for the player to select game mode...");
                    
                    try {
                        String message = this.cmd.execCommand();
                        System.out.println("(" + player + ") " + message);
                        this.cmd.sendMessage(message);
                    }
                    catch (IOException e) {
                        System.out.println("Error: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
                else {
                    String name = this.getState().getName();
                    if (name.equals(player)) {
                        String message = this.cmd.execCommand();
                        if (this.board != null) {
                            System.out.println("(" + player + ") " + message);
                            this.cmd.sendMessage(message);
                        }
                    }
                    else if (name.equals(bot)) {
                        int botMovementStatus = this.getBoard().botMovement(bot);
                        String botMessage = this.getBoard().getMoveMessage(botMovementStatus);
                        this.cmd.sendMessage("(" + bot + ") " + botMessage);
                        this.checkGameEnd(botMovementStatus);
                    }
                    else {
                        this.cmd.sendMessage("Error: Invalid game state!");
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
        this.checkGameEnd(status);

        return status;
    }



    /**
     * Game end checking method
     * @param status
     */
    private void checkGameEnd (int status) {
        if (status == Board.WHITE_WINS) {
            try {
                String message = String.format("Game ended: WHITE wins");
                this.cmd.sendMessage(message);
                this.board = null;
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
                this.cmd.sendMessage(message);
                this.board = null;
            }
            catch (IOException e) {
                System.err.println("IOError: " + e.getMessage());
                e.printStackTrace();
            }
            catch (NullPointerException e) {}

            this.end();
        }
    }
}
