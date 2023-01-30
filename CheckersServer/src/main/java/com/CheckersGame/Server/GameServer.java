package com.CheckersGame.Server;

import java.io.*;
import java.net.*;

import com.CheckersGame.Server.GameThreadHandlers.MultiPlayerGame;
import com.CheckersGame.Server.GameThreadHandlers.SinglePlayerGame;





/**
 * @author Jakub Musiał
 * @version 2.0
 * Checkers server application main class
 */
public class GameServer {    
    /** 
     * @param args
     */
    public static void main(String[] args) {

        Thread singlePlayerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    ServerSocket serverSocket = null;
                    
                    try {
                        serverSocket = new ServerSocket(4444);
                        System.out.println("Server listening (singleplayer): 4444");

                        Socket playerSocket = serverSocket.accept();
                        System.out.println("Player connected");
                        Thread gameThread = new Thread(new SinglePlayerGame(playerSocket));
                        gameThread.start();
                        serverSocket.close();
                    } 
                    catch (IOException e) {
                        e.printStackTrace();
                        try {
                            serverSocket.close();
                        } 
                        catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });



        Thread multiPlayerThread = new Thread(new Runnable() {
            @Override
            public void run() { 
                while (true) {
                    ServerSocket serverSocket = null;

                    try {
                        serverSocket = new ServerSocket(8888);
                        System.out.println("Server listening (multiplayer): 8888");

                        Socket playerWhite = serverSocket.accept();
                        System.out.println("First player connected");
                        System.out.println("Waiting for the second player");

                        Socket playerBlack = serverSocket.accept();
                        System.out.println("Second player connected");

                        Thread gameThread = new Thread(new MultiPlayerGame(playerWhite, playerBlack));
                        gameThread.start();
                        serverSocket.close();
                    } 
                    catch (IOException e) {
                        e.printStackTrace();
                        try {
                            serverSocket.close();
                        } 
                        catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });




        singlePlayerThread.start();
        multiPlayerThread.start();
    }
}
