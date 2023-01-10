package com.CheckersGame.Client.View;

import com.CheckersGame.Client.View.Menus.StartMenu;
import com.CheckersGame.Client.View.Menus.WaitPlayerMenu;
import com.CheckersGame.Client.View.Menus.WaitSelectionMenu;
import com.CheckersGame.Client.View.Menus.WelcomeMenu;

import javafx.scene.layout.BorderPane;



public class AppView extends BorderPane {

    private GameView gameView;
    private StartMenu startMenu;
    private WelcomeMenu welcomeMenu;
    private WaitPlayerMenu waitPlayerMenu;
    private WaitSelectionMenu waitSelectionMenu;
    public String colorOfPawns = "White";

    public AppView(int boardSize, int rowOfPawns) {
        this.setPrefSize(1024, 720);
        this.gameView = new GameView(boardSize, rowOfPawns, colorOfPawns);
        this.startMenu = new StartMenu();
        this.welcomeMenu = new WelcomeMenu();
        this.waitPlayerMenu = new WaitPlayerMenu();
        this.waitSelectionMenu = new WaitSelectionMenu();
    }

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
}
