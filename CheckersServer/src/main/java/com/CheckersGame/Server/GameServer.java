package com.CheckersGame.Server;

import java.io.*;
import java.net.*;





/**
 * @author Jakub Musiał
 * @version 1.0
 * Checkers server application main class
 */
public class GameServer {
    
    /** 
     * @param args
     */
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(4444)) {
            System.out.println("Server is listening on port 4444");

            while (true) {
                Socket playerWhite = serverSocket.accept();
                System.out.println("First player connected");
                System.out.println("Waiting for the second player");

                BufferedReader inWhite = new BufferedReader(new InputStreamReader(playerWhite.getInputStream()));
                PrintWriter outWhite = new PrintWriter(playerWhite.getOutputStream(), true);
                CommandLine cmdWhite = new CommandLine(inWhite, outWhite);

                cmdWhite.sendInit("white");

                Socket playerBlack = serverSocket.accept();
                System.out.println("Second player connected");

                BufferedReader inBlack = new BufferedReader(new InputStreamReader(playerBlack.getInputStream()));
                PrintWriter outBlack = new PrintWriter(playerBlack.getOutputStream(), true);
                CommandLine cmdBlack = new CommandLine(inBlack, outBlack);

                cmdBlack.sendInit("black");

                Thread gameThread = new Thread(new Game(playerWhite, cmdWhite, playerBlack, cmdBlack));
                gameThread.start();
            }
        } 
        catch (IOException e) {
            System.out.println("I/O exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
