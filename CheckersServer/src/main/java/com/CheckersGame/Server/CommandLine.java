package com.CheckersGame.Server;

import java.io.*;





/**
 * @author Jakub Musiał
 * @version 1.0
 * Class handling the communication with clients
 */
final class CommandLine {
    private Game game; /** An instance of the Game class by which the CommandLine has been instantiaded */

    private BufferedReader input; /** The server's input stream handling masseges sent from a client */
    private PrintWriter output; /** The server's output stream handling sending messages to a client */


    /**
     * CommandLine class constructor
     * @param game
     * @param input
     * @param output
     */
    public CommandLine (Game game, BufferedReader input, PrintWriter output) {
        this.game = game;
        this.input = input;
        this.output = output;
    }



    
    /** 
     * Reads an input line sent by a client, from which it extracs the command to execute and tries to execute it.
     * If the command execution fails, sends an error message to the client.
     * @return String
     * @throws IOException
     */
    public String execCommand () throws IOException {
        this.output.println("cmd: ");

        String command =  this.input.readLine();
        System.out.println("Recieved: " + command);
        String[] args = command.trim().replaceAll(" +", " ").split(" ");
        String message = null;
        
        switch (args[0]) {
            case "newGame": {
                if (this.game.getBoard() != null) {
                    message = "Error: Cannot start a new game - the game has already started!";
                    break;
                }

                try {
                    this.game.newGame(args[1]);
                    if (this.game.getVersion() == null) {
                        message = "Error: Could not start a new game!";
                        break;
                    }
    
                    message = "New game started: " + args[1];
                }
                catch (IndexOutOfBoundsException e) {
                    message = "Error: Invalid number of arguments!";
                }

                break;
            }

            case "movePawn": {
                if (this.game.getBoard() == null) {
                    message = "Error: Cannot move a pawn - the game hasn't started yet!";
                    break;
                }

                try {
                    int rCurr = Integer.parseInt(args[1]);
                    int cCurr = Integer.parseInt(args[2]);
                    int rMov = Integer.parseInt(args[3]); 
                    int cMov = Integer.parseInt(args[4]);
    
                    int status = this.game.movePawn(rCurr, cCurr, rMov, cMov);

                    if (status > 0) {
                        message = this.game.getMoveMessage(status) + String.format(" ==> Pawn moved: (%d,%d) -> (%d,%d)", rCurr, cCurr, rMov, cMov);
                    }
                    else {
                        message = this.game.getMoveMessage(status);
                    }
                }
                catch (IndexOutOfBoundsException e) {
                    message = "Error: Invalid number of arguments!";
                }

                break;
            }

            case "restartGame": {
                if (this.game.getVersion() == null) {
                    message = "Error: Cannot restart a game - game mode not specified!";
                    break;
                }

                this.game.restart();
                message = "Game restarted!";

                break;
            }

            case "endGame": {
                if (this.game.getBoard() == null) {
                    message = "Error: Cannot end a game - the game hasn't started yet!";
                    break;
                }

                this.game.end();
                message = "Game ended!";

                break;
            }

            case "mockEndgame": {
                if (this.game.getBoard() == null) {
                    message = "Error: Cannot mock an endgame situation - the game hasn't started yet!";
                    break;
                }

                try {
                    this.game.mockEndgame(args[1]);
                    message = "Mocking an endgame situation for player " + args[1] + "...";
                }
                catch (IndexOutOfBoundsException e) {
                    message = "Error: Invalid number of arguments!";
                }

                break;
            }

            case "mockQueenEndgame": {
                if (this.game.getBoard() == null) {
                    message = "Error: Cannot mock a queen endgame situation - the game hasn't started yet!";
                    break;
                }
                
                try {
                    this.game.mockQueenEndgame(args[1]);
                    message = "Mocking a queen endgame situation for player " + args[1] + "...";
                }
                catch (IndexOutOfBoundsException e) {
                    message = "Error: Invalid number of arguments!";
                }

                break;
            }

            case "mockPawnToQueen": {
                if (this.game.getBoard() == null) {
                    message = "Error: Cannot mock a pawn to queen situation - the game hasn't started yet!";
                    break;
                }
                
                try {
                    this.game.mockPawnToQueen(args[1]);
                    message = "Mocking a pawn to queen situation for player " + args[1] + "...";
                }
                catch (IndexOutOfBoundsException e) {
                    message = "Error: Invalid number of arguments!";
                }

                break;
            }

            case "longestMove": {
                if (this.game.getBoard() == null) {
                    message = "Error: Cannot calculate the longest move - the game hasn't started yet!";
                    break;
                }

                try {
                    message = "Longest move: " + this.game.longestMove(Integer.parseInt(args[1]), Integer.parseInt(args[2])) + "\n";
                }
                catch (IndexOutOfBoundsException e) {
                    message = "Error: Invalid number of arguments!";
                }

                break;
            }

            default: {
                message = "Error: Invalid command - To get a commands' overview type 'help'";
            }
        }

        if (message == null) {
            message = "";
        }

        return message;
    }


    
    /** 
     * Sends a initial message to a client.
     * @param player
     * @throws IOException
     */
    public void sendInit (String player) throws IOException {
        System.out.println("Sending: " + player);
        this.output.println("init: " + player);
    }



    /** 
     * Sends a message and a current game board description (if the board has been initialized) to a client.
     * @param message
     * @throws IOException
     */
    public void sendMessage (String message) throws IOException {
        System.out.println("Sending: " + this.game.getBoardDescription());
        this.output.println(this.game.getBoardDescription());
        System.out.println("Sending: " + message);
        this.output.println(message);
    }
}
