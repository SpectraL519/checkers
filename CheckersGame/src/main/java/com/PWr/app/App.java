package com.PWr.app;

import com.PWr.app.View.GameView;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;





public class App extends Application {
    public static void main (String args[]) {
        Application.launch(args);
    }

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
