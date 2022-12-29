package com.PWr.app.Model;

import com.PWr.app.Server.Boards.Board;
import com.PWr.app.Server.Versions.GameVersion;
import com.PWr.app.Server.States.GameState;

import java.io.*;
import java.net.*;




public class GameClient {
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    private CommandLine cmd;

    private int player;
    private final int WHITE = 1;
    private final int BLACK = 2;



    public GameClient () {
        this.cmd = new CommandLine(this);
    }



    public void start () {
        this.listen();
        this.getInit();

        while (true) {
            
        }
    }



    private void listen () {
        try {
            this.socket = new Socket("localhost", 4444);
            this.input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.output = new PrintWriter(this.socket.getOutputStream(), true);
        } 
        catch (UnknownHostException e) {
            System.out.println("Unknown host: localhost");
            System.exit(1);
        } 
        catch (IOException e) {
            System.out.println("I/O error");
            System.exit(1);
        }
    }



    private void getInit () {
        try {
            System.out.println(input.readLine());
            System.out.println(input.readLine());
        }
        catch (IOException e) {
            System.out.println("I/O error");
            System.exit(1);
        }
    }



    public void newGame (String v) {
        // TODO
    }



    public GameVersion getVersion () {
        // TODO
        return null;
    }



    public void initBoard () {
        // TODO
    }



    public void displayBoard () {
        // TODO
    }



    public Board getBoard () {
        // TODO
        return null;
    }



    public GameState getState () {
        // TODO
        return null;
    }



    public int movePawn (int rCurr, int cCurr, int rMov, int cMov) {
        // TODO
        return -1;
    }



    public void endGame () {
        // TODO
    }



    public void restartGame () {
        // TODO
    }



    public void mockEndgame (String player) {
        // TODO
    }



    public void mockQueenEndgame (String player) {
        // TODO
    }



    public void mockPawnToQueen (String player) {
        // TODO
    }



    public int longestMove (int r, int c) {
        // TODO
        return -1;
    }
}
