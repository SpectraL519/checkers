package com.CheckersGame.Client.View;

import com.CheckersGame.Client.View.Menus.StartMenu;
import com.CheckersGame.Client.View.Menus.WaitPlayerMenu;
import com.CheckersGame.Client.View.Menus.WaitSelectionMenu;
import com.CheckersGame.Client.View.Menus.WelcomeMenu;
import com.CheckersGame.Client.GameController;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.application.Platform;



public class AppView {
    private Stage stage;
    public final static int WIDTH = 1024;
    public final static int HEIGHT = 720;


    private GameView gameView;
    private StartMenu startMenu;
    public WelcomeMenu welcomeMenu;
    private WaitPlayerMenu waitPlayerMenu;
    private WaitSelectionMenu waitSelectionMenu;
    public String colorOfPawns;
    public GameController gameController;
    private String gameLog;

    public AppView(Stage stage, GameController gameController) {
        this.stage = stage;
        this.gameController = gameController;

        System.out.println("AppView: checkpoint 1");

        final Scene scene = new Scene(new BorderPane(), AppView.WIDTH, AppView.HEIGHT);
        this.stage.setOnCloseRequest(e -> Platform.exit());
        this.stage.setTitle("Checkers client");
        this.stage.setScene(scene);
        this.stage.show();

        System.out.println("AppView: checkpoint 2");

        this.waitPlayerMenu = null;
        this.waitSelectionMenu = null;
        this.gameView = null;
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
        System.out.println("AppView: checkpoint 3");
        this.welcomeMenu = new WelcomeMenu(this, this.gameController);
        this.welcomeMenu.render();
        // Scene scene = this.stage.getScene();
        // scene.setRoot(this.welcomeMenu);
        this.stage.setScene(new Scene(this.welcomeMenu));
        System.out.println("AppView: checkpoint 4");
    }

    public void renderWaitPlayerMenu() {
        System.out.println("AppView: checkpoint 5");
        this.waitPlayerMenu = new WaitPlayerMenu();
        this.waitPlayerMenu.render();
        this.stage.setScene(new Scene(this.waitPlayerMenu));
        System.out.println("AppView: checkpoint 6");
    }

    public void renderWaitSelectionMenu() {
        System.out.println("AppView: checkpoint 7");
        this.waitSelectionMenu = new WaitSelectionMenu();
        this.waitSelectionMenu.render();
        this.stage.setScene(new Scene(this.waitSelectionMenu));
        System.out.println("AppView: checkpoint 8");
    }

    public void renderStartMenu() {
        System.out.println("AppView: checkpoint 9");
        this.startMenu = new StartMenu(this.gameController);
        this.startMenu.render();
        this.stage.setScene(new Scene(this.startMenu));
        System.out.println("AppView: checkpoint 10");
    }

    public void renderGameView(String description) {
        System.out.println("AppView: checkpoint 11");
        this.gameView = new GameView(description, colorOfPawns, this.gameController, this.gameLog);
        this.gameView.render();
        this.stage.setScene(new Scene(this.gameView));
        System.out.println("AppView: checkpoint 12");
    }

    public void setPlayer(String color) {
        this.colorOfPawns = color;
    }

    public void updateLog (String message) {
        if(this.stage.getScene().getRoot() instanceof GameView) {
            this.gameLog = this.gameLog + "\n" + message;
        }
    }
}
