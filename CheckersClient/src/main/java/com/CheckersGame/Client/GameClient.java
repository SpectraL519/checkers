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
    private Scanner stdInScanner; /** Handles client's input from the terminal */



    /**
     * GameClient class constructor
     */
    public GameClient () {
        this.stdInScanner = new Scanner(System.in);
    }



    /**
     * Starts the client thread
     */
    public void start () {
        this.listen();
        this.getInit();

        while (true) {
            try{
                String line = this.input.readLine();
                
                if (line.startsWith("cmd:")) {
                    System.out.print(line);

                    String command = this.stdInScanner.nextLine();

                    if (command.equals("exit")) {
                        System.exit(0);
                    }
                    
                    this.output.println(command); // send command (args)

                    String message = this.input.readLine();
                    if (command.equals("endGame") || message.startsWith("Error")) {
                        System.out.println(message);
                        this.input.readLine(); // board description
                    }
                    else {
                        System.out.println(message);
                        this.displayBoard(this.input.readLine());
                    }
                }
                else if (line.startsWith("(white)") || line.startsWith("(black)")) {
                    System.out.println(line);
                }
                else if (line.startsWith("board:")) {
                    this.displayBoard(line);
                }
                else {
                    System.out.println();
                }
            }
            catch (IOException e) {
                System.err.println("IOError: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }



    /**
     * Tries to connect to the server
     */
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



    /**
     * Handles the initial message recieved from the client
     */
    private void getInit () {
        try {
            System.out.println(this.input.readLine());
            System.out.println(this.input.readLine());
        }
        catch (IOException e) {
            System.out.println("I/O error");
            System.exit(1);
        }
    }



    
    /** 
     * Displays the board on the client's terminal
     * @param boardDescription
     */
    private void displayBoard (String boardDescription) {
        if (!boardDescription.startsWith("board:")) {
            System.out.println("Error: Invalid board description");
            return;
        }

        boardDescription = boardDescription.replace("board:", "");

        String[] descriptionArray = boardDescription.split(";");
        int length = descriptionArray.length;

        int boardSize = Integer.parseInt(descriptionArray[0]);
        int board[][] = new int[boardSize][boardSize];

        for (int i = 1; i < length; i++) {
            String pawnDescription[] = descriptionArray[i].split(",");

            int row = Integer.parseInt(pawnDescription[0]);
            int column = Integer.parseInt(pawnDescription[1]);
            int pawnType = Integer.parseInt(pawnDescription[2]);

            board[row][column] = pawnType;
        }

        for (int r = 0; r < boardSize; r++) {
            System.out.printf("%d: ", r);
            for (int c = 0; c < boardSize; c++) {
                System.out.printf("%d ", board[r][c]);
            }
            System.out.printf("\n");
        }
        System.out.printf("\n  ");
        for (int c = 0; c < boardSize; c++) {
            System.out.printf(" %d", c);
        }
        System.out.println("\n");
    }
}
