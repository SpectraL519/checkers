package com.PWr.app.Server;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;





final class CommandLine {
    private BufferedReader input;
    private PrintWriter output;
    private Game game;



    CommandLine (Game model, BufferedReader input, PrintWriter output) {
        this.game = model;
        this.input = input;
        this.output = output;
    }



    public String execCommand () throws IOException {
        // TODO: displaying the board in the clients window
        this.output.println("cmd: ");

        String command = this.input.readLine().trim().replaceAll(" +", " ");
        String[] args = command.split(" ");
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
                    message = "Cannot start a new game - the game has already started!";
                    break;
                }

                this.game.newGame(args[1]);
                if (this.game.getVersion() == null) {
                    message = "Error: Could not start a new game!";
                    break;
                }

                this.game.initBoard();
                this.game.displayBoard();

                message = "New game started: " + args[1];
                break;
            }

            case "movePawn": {
                if (this.game.getVersion() == null) {
                    message = "Cannot move a pawn - the game hasn't started yet!";
                    break;
                }

                int rCurr = Integer.parseInt(args[1]);
                int cCurr = Integer.parseInt(args[2]);
                int rMov = Integer.parseInt(args[3]); 
                int cMov = Integer.parseInt(args[4]);

                int status = this.game.movePawn(rCurr, cCurr, rMov, cMov);

                if (status > 0) {
                    this.game.displayBoard();
                    message = String.format("Pawn moved: (%d,%d) -> (%d,%d)", rCurr, cCurr, rMov, cMov);
                }
                else {
                    // TODO:
                    // message = this.game.getErrorMessage(status);
                }

                break;
            }

            case "restartGame": {
                if (this.game.getVersion() == null) {
                    message = "Cannot restart a game - the game hasn't started yet!";
                    break;
                }

                this.game.restartGame();
                this.game.displayBoard();

                message = "Game restarted!";
                break;
            }

            case "endGame": {
                if (this.game.getVersion() == null) {
                    message = "Cannot end a game - the game hasn't started yet!";
                    break;
                }

                this.game.endGame();

                message = "Game ended!";
                break;
            }

            case "mockEndgame": {
                if (this.game.getVersion() == null) {
                    message = "Cannot mock an endgame situation - the game hasn't started yet!";
                    break;
                }

                this.game.mockEndgame(args[1]);
                this.game.displayBoard();

                message = "Mocking an endgame situation for player " + args[1] + "...";
                break;
            }

            case "mockQueenEndgame": {
                if (this.game.getVersion() == null) {
                    message = "Cannot mock a queen endgame situation - the game hasn't started yet!";
                    break;
                }
                
                this.game.mockQueenEndgame(args[1]);
                this.game.displayBoard();

                message = "Mocking a queen endgame situation for player " + args[1] + "...";
                break;
            }

            case "mockPawnToQueen": {
                if (this.game.getVersion() == null) {
                    message = "Cannot mock a pawn to queen situation - the game hasn't started yet!";
                    break;
                }
                
                this.game.mockPawnToQueen(args[1]);
                this.game.displayBoard();

                message = "Mocking a pawn to queen situation for player " + args[1] + "...";
                break;
            }

            case "longestMove": {
                if (this.game.getVersion() == null) {
                    message = "Cannot calculate the longest move - the game hasn't started yet!";
                    break;
                }

                message = "Longest move: " + this.game.longestMove(Integer.parseInt(args[1]), Integer.parseInt(args[2])) + "\n";
                break;
            }
            
            // TODO: move exit command handling to the GameClient class
            // case "exit": {
            //     System.exit(0);
            // }

            default: {
                message = "Invalid command!\nTo get a commands' overview type 'help'";
            }
        }

        if (message == null) {
            message = "";
        }

        return message;
    }



    public void sendMessage (String message) {
        this.output.println(message);
    }
}
