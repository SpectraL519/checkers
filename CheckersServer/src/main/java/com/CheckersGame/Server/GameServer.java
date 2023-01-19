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
    // https://stackoverflow.com/questions/38868774/how-to-run-two-instances-of-java-socket-servers-listenening-on-two-different-por
    public static void main(String[] args) {

        Thread singlePlayerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                ServerSocket serverSocket = null;

                while (true) {
                    try {
                        serverSocket = new ServerSocket(4444);

                        Socket playerSocket = serverSocket.accept();
                        // TODO: start a single player thread
                        playerSocket.close();

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
                ServerSocket serverSocket = null;

                while (true) {
                    try {
                        serverSocket = new ServerSocket(8888);

                        Socket playerWhite = serverSocket.accept();
                        System.out.println("First player connected");
                        System.out.println("Waiting for the second player");

                        Socket playerBlack = serverSocket.accept();
                        System.out.println("Second player connected");

                        Thread gameThread = new Thread(new Game(playerWhite, playerBlack));
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
