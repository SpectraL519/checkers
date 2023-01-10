package com.CheckersGame.Client;

import java.io.*;
import java.net.*;
import java.util.Scanner;




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
        try {
            this.socket = new Socket("localhost", 4444);
            this.input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.output = new PrintWriter(this.socket.getOutputStream(), true);
            this.controller.displayMessage("Waiting for the oponnent to connect...");
        } 
        catch (UnknownHostException e) {
            this.controller.displayMessage("Unknown host: localhost");
            this.controller.closeApplication(1);
        } 
        catch (IOException e) {
            this.controller.displayMessage("I/O error");
            this.controller.closeApplication(1);
        }
    }



    /**
     * Handles the initial message recieved from the client
     */
    private void getInit () {
        try {
            String player = this.input.readLine();
            this.controller.setPlayer(player);

            switch(player) {
                case "white": {
                    this.controller.chooseGameMode();
                    break;
                }

                case "black": {
                    this.controller.displayWaitScreen();
                    break;
                }
            }
        }
        catch (IOException e) {
            this.controller.displayMessage("I/O error");
            this.controller.closeApplication(1);
        }
    }



    public void getMessage() {
        try{
            String message = this.input.readLine();
            
            if (message.startsWith("cmd:")) {
                this.controller.activateMoveButtons();
            }
            else if (message.startsWith("(white)") || message.startsWith("(black)")) {
                this.controller.displayMessage(message);
            }
            else if (message.startsWith("board:")) {
                this.controller.renderBoard(message);
            }
            else {
                this.controller.displayErrorMessage(message);
            }
        }
        catch (IOException e) {
            this.controller.displayMessage("IOError: " + e.getMessage());
            e.printStackTrace();
        }
    }



    public void sendMessage(String message) {
        this.output.println(message);   
    }
}
