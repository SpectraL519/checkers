package com.CheckersGame.Client.View.GameViewComponents;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;




/**
 * @author Krzysztof Dobrucki
 * @version 1.0
 * Creditsbar class
 */
public class Creditsbar extends HBox {
    


    /**
     * Creditsbar class constructor
     */
    public Creditsbar () {
        super();
    }




    /**
     * Shows creditsbar
     */
    public void render() {
        this.setPadding(new Insets(7, 7, 7, 7));
        this.setSpacing(10);
        this.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY,null,null)));

        this.getChildren().addAll(new CreditsLabel());
    }



    /**
     * CreditsLabel class contains footer
     */
    class CreditsLabel extends Label {
        public CreditsLabel () {
            super("CHECKERS 2022 (C)");
        }
    }

}
