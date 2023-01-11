package com.CheckersGame.Client.View;

import com.CheckersGame.Client.View.Menus.StartMenu;
import com.CheckersGame.Client.View.Menus.WaitPlayerMenu;
import com.CheckersGame.Client.View.Menus.WaitSelectionMenu;
import com.CheckersGame.Client.View.Menus.WelcomeMenu;
import com.CheckersGame.Client.GameController;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;



public class AppView extends BorderPane {

    private GameView gameView;
    private StartMenu startMenu;
    private WelcomeMenu welcomeMenu;
    private WaitPlayerMenu waitPlayerMenu;
    private WaitSelectionMenu waitSelectionMenu;
    public String colorOfPawns;
    public GameController gameController;
    private String gameLog;

    public AppView(GameController gameController) {
        this.setPrefSize(1024, 720);
        this.gameController = gameController;
        this.gameLog = "";
    }

    /*

    ###OLD RENDER###

    public void render(){
        welcomeMenu.render();
        waitPlayerMenu.render();
        waitSelectionMenu.render();
        startMenu.render();
        gameView.render();
        
        
        this.setCenter(welcomeMenu);
        this.setCenter(waitPlayerMenu);
        this.setCenter(waitSelectionMenu);
        this.setCenter(startMenu);
        this.setCenter(gameView);
    }
    */

    public void renderWelcomeMenu() {
        this.welcomeMenu = new WelcomeMenu(this.gameController);
        this.welcomeMenu.render();
        this.setCenter(this.welcomeMenu);
    }

    public void renderWaitPlayerMenu() {
        this.waitPlayerMenu = new WaitPlayerMenu();
        this.waitPlayerMenu.render();
        this.setCenter(this.waitPlayerMenu);
    }

    public void renderWaitSelectionMenu() {
        this.waitSelectionMenu = new WaitSelectionMenu();
        this.waitSelectionMenu.render();
        this.setCenter(this.waitSelectionMenu);
    }

    public void renderStartMenu() {
        this.startMenu = new StartMenu(this.gameController);
        this.startMenu.render();
        this.setCenter(this.startMenu);
    }

    public void renderGameView(String description) {
        this.gameView = new GameView(description, colorOfPawns, this.gameController, this.gameLog);
        this.gameView.render();
        this.setCenter(this.gameView);
    }

    public void setPlayer(String color) {
        this.colorOfPawns = color;
    }

    public Node getCenterNode () {
        return this.getCenter();
    }

    public void updateLog (String message) {
        if(this.getCenter() instanceof GameView) {
            this.gameLog = this.gameLog + "\n" + message;
        }
    }
}
