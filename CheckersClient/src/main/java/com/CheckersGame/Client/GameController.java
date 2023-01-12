package com.CheckersGame.Client;

import com.CheckersGame.Client.View.GameView;

import javafx.application.Platform;





public class GameController {
    private Thread gameThread;
    private GameClient model;
    private GameView view;
    private Boolean active;



    public GameController () {
        this.model = null;
        this.view = null;
        this.active = false;
    }



    public void setModel (GameClient model) {
        this.model = model;
    }



    public void setView (GameView view) {
        this.view = view;
    }



    public void setActive (boolean active) {
        this.active = active;
    }



    public boolean isAstive () {
        return this.active;
    }



    public void startView () {
        this.view.render();
    }



    public void startModel () {
        this.gameThread = new Thread(this.model);
        this.gameThread.start();
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




    public void closeApplication (int status) {
        // System.exit(status);
        System.out.println("Program ended with return status: " + status);
        this.gameThread.interrupt();
        Platform.exit();
        System.exit(status);
    }
}
