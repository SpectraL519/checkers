package com.CheckersGame.Client;

import com.CheckersGame.Client.View.GameView;

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
        // GameModel model = new GameModel();
        // GameController controller = new GameController();
        GameView view = new GameView(8);
        view.render();

        Scene scene = new Scene(view);
        primaryStage.setTitle("Checkers Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
