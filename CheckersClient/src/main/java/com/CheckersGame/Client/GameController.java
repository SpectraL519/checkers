package com.CheckersGame.Client;

import com.CheckersGame.Client.View.AppView;

import java.io.*;





public class GameController {
    private GameClient model;
    private AppView view;



    public GameController () {
        this.model = null;
        this.view = null;
    }



    public void setModel (GameClient model) {
        this.model = model;
    }



    public void setView (AppView view) {
        this.view = view;
    }



    public void start () {
        this.model.start();
        this.view.render();

        while (true) {
            this.model.getMessage();
        }
    }


    /**
     * Model -> view
     * @param message
     */
    public void displayMessage (String message) {
        this.view.displayMessage(message);
    }



    /**
     * Model -> view
     * @param message
     */
    public void displayErrorMessage (String message) {
        this.view.displayErrorMessage(message);
    }



    /**
     * Model -> view
     * @param player
     */
    public void setPlayer (String player) {
        this.view.setPlayer(player);
    }



    /**
     * Model -> view
     */
    public void chooseGameMode () {
        this.view.chooseGameMode();
    }



    /**
     * Model -> view
     */
    public void displayWaitScreen () {
        this.view.displayWaitScreen();
    }



    public void renderBoard (String boardDescription) {
        this.view.renderBoard(boardDescription);
    }



    /**
     * Model -> view
     */
    public void activateMoveButtons () {
        this.view.activateMoveButtons();
    }



    /**
     * View -> model
     * @param version
     */
    public void newGame (String version) {
        this.model.sendMessage("newGame " + version);
        this.model.getMessage(); // Game start status
        this.model.getMessage(); // Board description
    }



    /**
     * View -> model
     */
    public void restartGame () {
        this.model.sendMessage("restartGame");
        this.model.getMessage(); // Game restart status
        this.model.getMessage(); // Board description
    }



    public void endGame () {
        this.model.sendMessage("endGame");
        this.model.getMessage(); // Game end status
        this.chooseGameMode();
    }



    /**
     * View -> model
     * @param rCurr
     * @param cCurr
     * @param rMov
     * @param cMov
     */
    public void movePawn (int rCurr, int cCurr, int rMov, int cMov) {
        this.model.sendMessage(String.format("movePawn %d %d %d %d", rCurr, cCurr, rMov, cMov));
        this.model.getMessage(); // Move status
        this.model.getMessage(); // Board description
    }



    /**
     * View -> model
     * @param type
     * @param player
     */
    public void mock (String type, String player) {
        switch (type) {
            case "simple": {
                this.model.sendMessage("mockEndgame " + player);
                break;
            }

            case "queen": {
                this.model.sendMessage("mockQueenEndgame" + player);
                break;
            }

            case "convert": {
                this.model.sendMessage("mockPawnToQueen " + player);
            }

            default: {
                this.view.displayErrorMessage("Error: Invalid mock");
                return;
            }
        }

        this.model.getMessage(); // Move status
        this.model.getMessage(); // Board description
    }




    public void closeApplication (int status) {
        System.exit(status);
    }
}
