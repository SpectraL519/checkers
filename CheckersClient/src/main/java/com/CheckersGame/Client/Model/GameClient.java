package com.CheckersGame.Client.Model;

import java.io.*;
import java.net.*;

import com.CheckersGame.Client.GameController;

import javafx.application.Platform;





/**
 * @author Jakub Musiał
 * @version 1.0
 * Class handling the client thread
 */
public abstract class GameClient implements Runnable {
    protected int port;

    private Socket socket; /** A socket for the client to connect to the server */

    private BufferedReader input; /** The client's input stream handling masseges sent from a server */
    private PrintWriter output; /** The clients's output stream handling sending messages to a server */

    protected GameController controller; /** MVC::Controller class instance */



    /**
     * GameClient class constructor
     */
    public GameClient (GameController controller) {
        this.controller = controller;
    }



    /**
     * Starts the client thread
     */
    @Override
    public void run () {
        this.listen();

        while (true) {
            this.getMessage();
        }
    }



    /**
     * Tries to connect to the server
     */
    private void listen () {
        try {
            System.out.println("Connecting to server...");
            this.socket = new Socket("localhost", this.port);
            this.input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.output = new PrintWriter(this.socket.getOutputStream(), true);
            System.out.println("Success!");
            Platform.runLater(() -> {
                this.controller.updateGameLog("Server connection successful!");
            });
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
     * Gets messeages from server
     */
    private void getMessage() {
        System.out.println("Trying to recieve a message from the server...");
        try {
            String message = this.input.readLine();

            if (message.startsWith("init:")) {
                System.out.println("Recieved: init");
                if (message.endsWith("white")) {
                    Platform.runLater(() -> {
                        this.controller.setPlayer("white");
                    });
                }
                else if (message.endsWith("black")) {
                    Platform.runLater(() -> {
                        this.controller.setPlayer("black");
                    });
                }
                else {
                    System.err.println("Error: invalid init message");
                    this.controller.closeApplication(1);
                }
            }
            else if (message.startsWith("cmd:")) {
                System.out.println("Recieved: prompt");
                this.controller.setActive(true);
            }
            else if (message.startsWith("board:")) {
                System.out.println("Recieved: board");
                Platform.runLater(() -> {
                    this.controller.renderBoard(message);
                });
            }
            else {
                Platform.runLater(() -> {
                    this.controller.updateGameLog(message);
                });
            }
        }
        catch (IOException e) {
            this.controller.updateGameLog("IOError: " + e.getMessage());
            e.printStackTrace();
        }
    }


    
    /**
     * Shows messages
     */
    public void sendMessage(String message) {
        System.out.println("Sending: " + message);
        this.output.println(message);   
    }
}
