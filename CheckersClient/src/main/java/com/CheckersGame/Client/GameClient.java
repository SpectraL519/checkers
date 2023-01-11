package com.CheckersGame.Client;

import java.io.*;
import java.net.*;





/**
 * @author Jakub Musiał
 * @version 1.0
 * Class handling the client thread
 */
public class GameClient {
    private Socket socket; /** A socket for the client to connect to the server */

    private BufferedReader input; /** The client's input stream handling masseges sent from a server */
    private PrintWriter output; /** The clients's output stream handling sending messages to a server */

    private GameController controller; /** MVC::Controller class instance */



    /**
     * GameClient class constructor
     */
    public GameClient (GameController controller) {
        this.controller = controller;
    }



    /**
     * Starts the client thread
     */
    public void start () {
        this.listen();
        this.getInit();
    }



    /**
     * Tries to connect to the server
     */
    private void listen () {
        this.controller.displayWaitScreen("opponentAwaiting");
        try {
            System.out.println("listen");
            this.socket = new Socket("localhost", 4444);
            this.input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.output = new PrintWriter(this.socket.getOutputStream(), true);
            
        } 
        catch (UnknownHostException e) {
            System.err.println("Unknown host: localhost");
            this.controller.closeApplication(1);
        } 
        catch (IOException e) {
            System.err.println("I/O error");
            this.controller.closeApplication(1);
        }
    }



    /**
     * Handles the initial message recieved from the client
     */
    private void getInit () {
        System.out.println("before player");
        try {
            String player = this.input.readLine();
            System.out.println("player: " + player);
            this.controller.setPlayer(player);

            switch (player) {
                case "white": {
                    this.controller.chooseGameMode();
                    break;
                }

                case "black": {
                    this.controller.displayWaitScreen("modeSelection");
                    break;
                }

                default: {
                    System.err.println("Invalid player info");
                    this.controller.closeApplication(1);
                }
            }
        }
        catch (IOException e) {
            System.err.println("I/O error");
            //this.controller.updateLog("I/O error");
            this.controller.closeApplication(1);
        }
    }



    public void getMessage() {
        try{
            String message = this.input.readLine();
            
            if (message.startsWith("cmd:")) {
                this.controller.setActive(true);
            }
            else if (message.startsWith("board:")) {
                this.controller.renderBoard(message);
            }
            else {
                this.controller.updateLog(message);
            }
        }
        catch (IOException e) {
            this.controller.updateLog("IOError: " + e.getMessage());
            e.printStackTrace();
        }
    }



    public void sendMessage(String message) {
        this.output.println(message);   
    }
}
