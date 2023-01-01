package com.CheckersGame.Server;

import java.io.*;
import java.net.*;





public class GameServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(4444)) {
            System.out.println("Server is listening on port 4444");

            while (true) {
                Socket playerWhite = serverSocket.accept();
                System.out.println("First player connected");
                System.out.println("Waiting for the second player");

                Socket playerBlack = serverSocket.accept();
                System.out.println("Second player connected");

                Thread gameThread = new Thread(new Game(playerWhite, playerBlack));
                gameThread.start();
            }
        } 
        catch (IOException e) {
            System.out.println("I/O exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
