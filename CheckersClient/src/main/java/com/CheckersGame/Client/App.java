package com.CheckersGame.Client;

import com.CheckersGame.Client.View.GameView;

import javafx.application.Application;
import javafx.stage.Stage;





/**
 * @author Jakub Musiał
 * @author Krzysztof Dobrucki
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
        GameView view = new GameView(primaryStage, controller);

        controller.setView(view);
        controller.startView();
    }
}
