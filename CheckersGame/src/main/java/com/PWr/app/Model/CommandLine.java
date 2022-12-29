package com.PWr.app.Model;

import java.util.Scanner;

import com.PWr.app.Model.GameClient;





final class CommandLine {
    private Scanner stdInScanner;
    private GameClient model;

    private String command;



    CommandLine (GameClient model) {
        this.stdInScanner = new Scanner(System.in);
        this.model = model;
    }



    public void getCommand () {
        System.out.print("cmd: ");

        this.command = stdInScanner.next();
        
        switch (this.command) {
            case "help": {
                System.out.println("\nCheckers console app commands:");
                System.out.println("\t- newGame <version> : Starts a new game in the <version> version\n\t\t<version> can be: 'russian', 'polish' or 'canadian'");
                System.out.println("\t- movePawn <current_row> <current_column> <new_row> <new_column> : Moves a pawn from the current to the new position (if it's possible)");
                System.out.println("\t- restartGame : Restarts the game in the current versio");
                System.out.println("\t- endGame : Ends the current game");
                System.out.println("\t- mockEndgame <player> : Mocks an endgame situation with the next move of the <player> player\n\t\t<player> can be: 'white' or 'black'");
                System.out.println("\t- mockQueenEndgame <player> : Mocks a queen endgame situation with the next move of the <player> player\n\t\t<player> can be: 'white' or 'black'");
                System.out.println("\t- mockPawnToQueen <player> : Mocks a pawn to queen situation with the next move of the <player> player\n\t\t<player> can be: 'white' or 'black'");
                System.out.println("\t- longestMove <row> <column> : Calculates the longest move that can be performed from a position (<row>,<column>)");
                System.out.println("\t- exit : Exits the program\n");
                System.out.println("\n\t");
                break;
            }

            case "newGame": {
                if (this.model.getVersion() != null) {
                    this.stdInScanner.nextLine();
                    System.out.println("Cannot start a new game - the game has already started!");
                    break;
                }

                this.model.newGame(this.stdInScanner.next());
                this.model.initBoard();
                this.model.displayBoard();
                System.out.println();
                break;
            }

            case "movePawn": {
                if (this.model.getVersion() == null) {
                    this.stdInScanner.nextLine();
                    System.out.println("Cannot move a pawn - select a game version first!");
                    break;
                }

                int rCurr = this.stdInScanner.nextInt();
                int cCurr = this.stdInScanner.nextInt();
                int rMov = this.stdInScanner.nextInt(); 
                int cMov = this.stdInScanner.nextInt();

                int status = this.model.movePawn(rCurr, cCurr, rMov, cMov);

                if (status > 0 && status / 10 == 0) {
                    this.model.displayBoard();
                    System.out.println();
                }
                break;
            }

            case "restartGame": {
                if (this.model.getVersion() == null) {
                    this.stdInScanner.nextLine();
                    System.out.println("Cannot restart a game - the game hasn't started yet!");
                    break;
                }

                this.model.restartGame();
                System.out.println("Game restarted!");
                this.model.displayBoard();
                System.out.println();
                break;
            }

            case "endGame": {
                if (this.model.getVersion() == null) {
                    this.stdInScanner.nextLine();
                    System.out.println("Cannot end a game - the game hasn't started yet!");
                    break;
                }

                System.out.println("Game ended!");
                this.model.endGame();
                break;
            }

            case "mockEndgame": {
                if (this.model.getVersion() == null) {
                    this.stdInScanner.nextLine();
                    System.out.println("Cannot mock an endgame situation - select a game version first!");
                    break;
                }

                System.out.println("Mocking an endgame situation...");
                this.model.mockEndgame(this.stdInScanner.next());
                this.model.displayBoard();
                System.out.println();
                break;
            }

            case "mockQueenEndgame": {
                if (this.model.getVersion() == null) {
                    this.stdInScanner.nextLine();
                    System.out.println("Cannot mock a queen endgame situation - select a game version first!");
                    break;
                }
                
                System.out.println("Mocking a queen endgame situation...");
                this.model.mockQueenEndgame(this.stdInScanner.next());
                this.model.displayBoard();
                System.out.println();
                break;
            }

            case "mockPawnToQueen": {
                if (this.model.getVersion() == null) {
                    this.stdInScanner.nextLine();
                    System.out.println("Cannot mock a pawn to queen situation - select a game version first!");
                    break;
                }
                
                System.out.println("Mocking a pawn queen situation...");
                this.model.mockPawnToQueen(this.stdInScanner.next());
                this.model.displayBoard();
                System.out.println();
                break;
            }

            case "longestMove": {
                if (this.model.getVersion() == null) {
                    this.stdInScanner.nextLine();
                    System.out.println("Cannot calculate the longest move - select a game version first!");
                    break;
                }

                System.out.println("Longest move: " + this.model.longestMove(this.stdInScanner.nextInt(), this.stdInScanner.nextInt()));
                System.out.println();
                break;
            }
            
            case "exit": {
                this.stdInScanner.close();
                System.exit(0);
            }

            default: {
                System.out.println("Invalid command!");
                this.stdInScanner.nextLine();
                System.out.println("To get a commands' overview type 'help'");
            }
        }
    }
}
