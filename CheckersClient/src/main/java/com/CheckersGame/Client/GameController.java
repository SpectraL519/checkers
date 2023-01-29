package com.CheckersGame.Client;

import com.CheckersGame.Client.Model.*;
import com.CheckersGame.Client.View.GameView;

import javafx.application.Platform;




/**
 * @author Jakub Musiał
 * @version 1.0
 * Game Controller application main class
 */
public class GameController {
    private Thread gameThread;
    private GameClient model;
    private GameView view;
    private Boolean active;


    /**
     * GameController class constructor
     */
    public GameController () {
        this.model = null;
        this.view = null;
        this.active = false;
    }


    /**
     * View -> model
     * @param model
     */
    public void setModel (GameClient model) {
        this.model = model;
    }


    /**
     * View -> model
     * @param view
     */
    public void setView (GameView view) {
        this.view = view;
    }


    /**
     * View -> model
     * @param active
     */
    public void setActive (boolean active) {
        this.active = active;
    }


    /**
     * View -> model
     */
    public boolean isAstive () {
        return this.active;
    }


    /**
     * Model -> view
     */
    public void startView () {
        this.view.render();
    }


    /**
     * View -> model
     */
    public void startModel (String gameMode) {
        switch (gameMode) {
            case "singleplayer": {
                this.model = new SinglePlayerClient(this);
                this.gameThread = new Thread(this.model);
                this.gameThread.start();
                break;
            }

            case "multiplayer": {
                this.model = new MultiPlayerClient(this);
                this.gameThread = new Thread(this.model);
                this.gameThread.start();
                break;
            }

            default: {
                System.out.println("Error: Invalid game mode specified!");
                break;
            }
        }
    }


    /**
     * Model -> view
     * @param message
     */
    public void updateGameLog (String message) {
        this.view.updateGameLog(message);
    }



    /**
     * Model -> view
     * @param player
     */
    public void setPlayer (String player) {
        this.view.setPlayer(player);
    }




    /**
     * Renders the gam board view
     * @param boardDescription
     */
    public void renderBoard (String boardDescription) {
        if (!boardDescription.endsWith("null")) {
            this.view.renderBoard(boardDescription);
        }
    }



    /**
     * View -> model
     * @param version
     */
    public void newGame (String version) {
        this.model.sendMessage("newGame " + version);
        this.setActive(false);
    }



    /**
     * View -> model
     */
    public void restartGame () {
        this.model.sendMessage("restartGame");
        this.setActive(false);
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
        this.setActive(false);
    }



    /**
     * View -> model
     * @param type
     * @param player
     */
    public void mock (String type) {
        switch (type) {
            case "simple": {
                this.model.sendMessage("mockEndgame");
                this.setActive(false);
                break;
            }

            case "queen": {
                this.model.sendMessage("mockQueenEndgame");
                this.setActive(false);               
                break;
            }

            case "convert": {
                this.model.sendMessage("mockPawnToQueen");
                this.setActive(false);                
                break;
            }

            default: {
                this.view.updateGameLog("Error: Invalid mock");
                this.setActive(false);
                return;
            }
        }
    }



    /**
     * Closes the application
     * @param status
     */
    public void closeApplication (int status) {
        System.out.println("Program ended with return status: " + status);
        if (this.gameThread != null)
            this.gameThread.interrupt();
        Platform.exit();
        System.exit(status);
    }
}
