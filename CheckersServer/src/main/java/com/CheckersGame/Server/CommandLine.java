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
        // TODO: add playAgain funcionality
        this.output.println("cmd: ");

        String command =  this.input.readLine();
        String[] args = command.trim().replaceAll(" +", " ").split(" ");
        String message = null;
        
        switch (args[0]) {
            case "help": {
                message = "Checkers console app commands:"
                    + "\n\t- newGame <version> : Starts a new game in the <version> version\n\t\t<version> can be: 'russian', 'polish' or 'canadian'"
                    + "\n\t- movePawn <current_row> <current_column> <new_row> <new_column> : Moves a pawn from the current to the new position (if it's possible)"
                    + "\n\t- restartGame : Rargsestarts the game in the current versio"
                    + "\n\t- endGame : Ends the current game"
                    + "\n\t- mockEndgame <player> : Mocks an endgame situation with the next move of the <player> player\n\t\t<player> can be: 'white' or 'black'"
                    + "\n\t- mockQueenEndgame <player> : Mocks a queen endgame situation with the next move of the <player> player\n\t\t<player> can be: 'white' or 'black'"
                    + "\n\t- mockPawnToQueen <player> : Mocks a pawn to queen situation with the next move of the <player> player\n\t\t<player> can be: 'white' or 'black'"
                    + "\n\t- longestMove <row> <column> : Calculates the longest move that can be performed from a position (<row>,<column>)"
                    + "\n\t- exit : Exits the program";

                break;
            }

            case "newGame": {
                if (this.game.getVersion() != null) {
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
                if (this.game.getVersion() == null) {
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
                    message = "Error: Cannot restart a game - the game hasn't started yet!";
                    break;
                }

                this.game.restart();
                message = "Game restarted!";

                break;
            }

            case "endGame": {
                if (this.game.getVersion() == null) {
                    message = "Error: Cannot end a game - the game hasn't started yet!";
                    break;
                }

                this.game.end();
                message = "Game ended!";

                break;
            }

            case "mockEndgame": {
                if (this.game.getVersion() == null) {
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
                if (this.game.getVersion() == null) {
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
                if (this.game.getVersion() == null) {
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
                if (this.game.getVersion() == null) {
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
     * @param message
     * @throws IOException
     */
    public void sendInit (String message) throws IOException {
        this.output.println(message);
    }



    /** 
     * Sends a message and a current game board description (if the board has been initialized) to a client.
     * @param message
     * @throws IOException
     */
    public void sendMessage (String message) throws IOException {
        this.output.println(message);

        if (this.game.getBoard() == null) {
            System.out.println("Null board");
            this.output.println("");
            return;
        }
        
        this.output.println(this.game.getBoardDescription());
    }
}
