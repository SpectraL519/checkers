package com.CheckersGame.Client;

import com.CheckersGame.Client.View.AppView;
import com.CheckersGame.Client.View.GameView;





public class GameController {
    private GameClient model;
    private AppView view;
    private Boolean active;



    public GameController () {
        this.model = null;
        this.view = null;
        this.active = false;
    }



    public void setModel (GameClient model) {
        this.model = model;
    }



    public void setView (AppView view) {
        this.view = view;
    }



    public void setActive (boolean active) {
        this.active = active;
    }



    public boolean isAstive () {
        return this.active;
    }



    public void startView () {
        this.view.renderGameView();
    }



    public void startModel () {
        this.model.start();
        // this.model.getMessage();
    }


    /**
     * Model -> view
     * @param message
     */
    public void updateLog (String message) {
        //this.view.updateLog(message);
    }



    /**
     * Model -> view
     * @param player
     */
    public void setPlayer (String player) {
        //this.view.setPlayer(player);
    }



    /**
     * Model -> view
     */
    public void chooseGameMode () {
        //this.view.renderStartMenu();
    }



    /**
     * Model -> view
     */
    public void displayWaitScreen (String type) {
        System.out.println("displayWaitScreen " + type + " -> connector");
        switch (type) {
            case "opponentAwaiting": {
                //this.view.renderWaitPlayerMenu();
                break;
            }

            case "modeSelection": {
                //this.view.renderWaitSelectionMenu();
                break;
            }

            default: {
                System.err.println("Invalid waitscreen type info");
                this.closeApplication(1);
                break;
            }
        }
    }



    public void renderBoard (String boardDescription) {
        if (!boardDescription.endsWith("null")) {
            //this.view.renderGameView(boardDescription);
        }
    }



    /**
     * View -> model
     * @param version
     */
    public void newGame (String version) {
        this.model.sendMessage("newGame " + version);
        this.setActive(false);
        // this.model.getMessage(); // Game start status
        // this.model.getMessage(); // Board description
        // this.model.getMessage();
    }



    /**
     * View -> model
     */
    public void restartGame () {
        this.model.sendMessage("restartGame");
        this.setActive(false);
        // this.model.getMessage(); // Game restart status
        // this.model.getMessage(); // Board description
        // this.model.getMessage();
    }



    public void endGame () {
        this.model.sendMessage("endGame");
        this.setActive(false);
        // this.model.getMessage(); // Game end status
        this.chooseGameMode();
        // this.model.getMessage();
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
        // this.model.getMessage(); // Move status
        // this.model.getMessage(); // Board description
        // this.model.getMessage();
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
                this.setActive(false);
                break;
            }

            case "queen": {
                this.model.sendMessage("mockQueenEndgame" + player);
                this.setActive(false);               
                break;
            }

            case "convert": {
                this.model.sendMessage("mockPawnToQueen " + player);
                this.setActive(false);                
                break;
            }

            default: {
                //this.view.updateLog("Error: Invalid mock");
                this.setActive(false);
                return;
            }
        }

        // this.model.getMessage(); // Move status
        // this.model.getMessage(); // Board description
        // this.model.getMessage();
    }




    public void closeApplication (int status) {
        System.exit(status);
    }
}
