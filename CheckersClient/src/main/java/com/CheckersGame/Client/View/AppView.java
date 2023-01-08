package com.CheckersGame.Client.View;

import javafx.scene.layout.BorderPane;



public class AppView extends BorderPane {

    private GameView gameView;
    private StartMenu startMenu;
    private WelcomeMenu welcomeMenu;
    private WaitMenu waitMenu;

    public AppView(int boardSize, int rowOfPawns) {
        this.setPrefSize(1024, 720);
        this.gameView = new GameView(boardSize, rowOfPawns);
        this.startMenu = new StartMenu();
        this.welcomeMenu = new WelcomeMenu();
        this.waitMenu = new WaitMenu();
    }

    public void render(){
        //gameView.render();
        welcomeMenu.render();
        //startMenu.render();
        //waitMenu.render();
        
        //this.setCenter(gameView);
        this.setCenter(welcomeMenu);
        //this.setCenter(startMenu);
        //this.setCenter(waitMenu);
    }
}
