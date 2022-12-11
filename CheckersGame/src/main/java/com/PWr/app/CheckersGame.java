package com.PWr.app;

import javafx.application.Application;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

// import javafx.scene.Node;
// import javafx.geometry.Pos;





/**
 * Welcome!
 */
public class CheckersGame extends Application {
    @Override
    public void start (Stage PrimaryStage) {
        /** Application Pane */
        BorderPane MainPane = new BorderPane();
        MainPane.setPrefSize(1024, 628);

        Label WelcomeLabel = new Label("Welcome to the Checkers Game!");
        MainPane.setCenter(WelcomeLabel);

        /** Application Scene */
        Scene MainScene = new Scene(MainPane);

        PrimaryStage.setTitle("Checkers Game");
        PrimaryStage.setScene(MainScene);
        PrimaryStage.show();
    }

    public static void main (String args[]) {
        Application.launch(args);
    }
}
