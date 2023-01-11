package com.CheckersGame.Client;

import com.CheckersGame.Client.View.AppView;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;





/**
 * @author Jakub Musiał
 * @version 1.0
 * Checkers client application main class
 */
public class App extends Application {
    /** 
     * @param args[]
     */
    public static void main (String args[]) {
        Application.launch(args);
    }
    

    /** 
     * Starts the game window
     * @param primaryStage
     */
    @Override
    public void start (Stage primaryStage) {
        GameController controller = new GameController();

        GameClient model = new GameClient(controller);

        // SceneController sceneController = new SceneController(primaryStage, "Checkers Game");
        AppView view = new AppView(primaryStage, controller);
        
        controller.setModel(model);
        controller.setView(view);
        controller.startView();
    }
}
