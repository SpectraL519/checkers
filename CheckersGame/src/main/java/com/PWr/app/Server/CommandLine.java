package com.PWr.app.Server;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;





final class CommandLine {
    private BufferedReader input;
    private PrintWriter output;
    private Game game;

    private String[] args;
    private String message;



    CommandLine (Game model, BufferedReader input, PrintWriter output) {
        this.game = model;
        this.input = input;
        this.output = output;
    }



    public void getCommand () throws IOException {
        // TODO: this.args = this.game.getMessage(); 
        // getting a message from a player whose turn it currently is
        // instead of having a commandLine with specific input and output streams

        this.output.println("cmd: ");

        this.args = this.input.readLine().split(" ");
        this.message = null;
        
        switch (this.args[0]) {
            case "help": {
                this.message = "Checkers console app commands:"
                    + "\n\t- newGame <version> : Starts a new game in the <version> version\n\t\t<version> can be: 'russian', 'polish' or 'canadian'"
                    + "\n\t- movePawn <current_row> <current_column> <new_row> <new_column> : Moves a pawn from the current to the new position (if it's possible)"
                    + "\n\t- restartGame : Restarts the game in the current versio"
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
                    this.message = "Cannot start a new game - the game has already started!";
                    break;
                }

                this.game.newGame(this.args[1]);
                if (this.game.getVersion() == null) {
                    this.message = "Error: Could not start a new game!";
                    break;
                }

                this.game.initBoard();
                this.game.displayBoard();
                break;
            }

            case "movePawn": {
                if (this.game.getVersion() == null) {
                    this.message = "Cannot move a pawn - the game hasn't started yet!";
                    break;
                }

                int rCurr = Integer.parseInt(this.args[1]);
                int cCurr = Integer.parseInt(this.args[2]);
                int rMov = Integer.parseInt(this.args[3]); 
                int cMov = Integer.parseInt(this.args[4]);

                int status = this.game.movePawn(rCurr, cCurr, rMov, cMov);

                if (status > 0 && status / 10 == 0) {
                    this.game.displayBoard();
                }
                break;
            }

            case "restartGame": {
                if (this.game.getVersion() == null) {
                    this.message = "Cannot restart a game - the game hasn't started yet!";
                    break;
                }

                this.game.restartGame();
                this.message = "Game restarted!";
                this.game.displayBoard();
                break;
            }

            case "endGame": {
                if (this.game.getVersion() == null) {
                    this.message = "Cannot end a game - the game hasn't started yet!";
                    break;
                }

                this.game.endGame();
                this.message = "Game ended!";
                break;
            }

            case "mockEndgame": {
                if (this.game.getVersion() == null) {
                    this.message = "Cannot mock an endgame situation - the game hasn't started yet!";
                    break;
                }

                this.message = "Mocking an endgame situation...";
                this.game.mockEndgame(this.args[1]);
                this.game.displayBoard();
                this.output.println();
                break;
            }

            case "mockQueenEndgame": {
                if (this.game.getVersion() == null) {
                    this.message = "Cannot mock a queen endgame situation - the game hasn't started yet!";
                    break;
                }
                
                this.message = "Mocking a queen endgame situation...";
                this.game.mockQueenEndgame(this.args[1]);
                this.game.displayBoard();
                break;
            }

            case "mockPawnToQueen": {
                if (this.game.getVersion() == null) {
                    this.message = "Cannot mock a pawn to queen situation - the game hasn't started yet!";
                    break;
                }
                
                this.message = "Mocking a pawn queen situation...";
                this.game.mockPawnToQueen(this.args[1]);
                this.game.displayBoard();
                break;
            }

            case "longestMove": {
                if (this.game.getVersion() == null) {
                    this.message = "Cannot calculate the longest move - the game hasn't started yet!";
                    break;
                }

                this.message = "Longest move: " + this.game.longestMove(Integer.parseInt(this.args[1]), Integer.parseInt(this.args[2])) + "\n";
                break;
            }
            
            case "exit": {
                System.exit(0);
            }

            default: {
                this.message = "Invalid command!\nTo get a commands' overview type 'help'";
            }
        }

        if (this.message == null) {
            this.message = "";
        }
        this.output.println(message);

        // TODO: this.game.sendMessage(boolean bothPlayers); ???
    }
}
