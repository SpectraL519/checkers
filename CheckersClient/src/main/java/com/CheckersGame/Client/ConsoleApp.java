package com.CheckersGame.Client;





/**
 * @author Jakub Musiał
 * @version 1.0
 * Checkers client console application main class
 */
public class ConsoleApp {
    /** 
     * @param args[]
     */
    public static void main(String args[]) {
        GameClient client = new GameClient();
        client.start();
    }
}
