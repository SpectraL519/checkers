package com.CheckersGame.Client.View;

import javafx.scene.layout.BorderPane;



public class AppView extends BorderPane {

    private GameView gameView;
    private StartMenu startMenu;

    public AppView(int boardSize, int rowOfPawns) {
        this.setPrefSize(1024, 720);
        this.gameView = new GameView(boardSize, rowOfPawns);
        this.startMenu = new StartMenu();
    }

    public void render(){
        gameView.render();
        //startMenu.render();
        
        this.setCenter(gameView);
        //this.setCenter(startMenu);
    }
}
