package com.CheckersGame.Client.View.Menus;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;



public class WaitSelectionMenu extends BorderPane {

    private VBox menu;

    public WaitSelectionMenu () {
        super();
        System.out.println("WaitSelectionMenu: checkpoint 1");
        this.menu = null;
        System.out.println("WaitSelectionMenu: checkpoint 2");
    }

    public  void render(){
        System.out.println("WaitSelectionMenu: checkpoint 3");
        this.menu = new VBox();
        this.menu.setBackground(new Background(new BackgroundFill(Color.CORAL, null, null)));
        this.menu.setPadding(new Insets(10, 10, 10, 10));
        this.menu.setSpacing(10);
        this.menu.setAlignment(Pos.CENTER);
        this.menu.setMinWidth(250);
        this.menu.setMaxWidth(250);
        this.menu.getChildren().addAll(new WelcomeText("Please wait! "), new WelcomeText("Your opponent is choosing a game type..."));
        this.setCenter(this.menu);
        System.out.println("WaitSelectionMenu: checkpoint 4");
    }

    class WelcomeText extends Label {
        public WelcomeText (String text) {
            super(text);
            CornerRadii corner = new CornerRadii(3);
            this.setBackground(new Background(new BackgroundFill(Color.WHITE, corner, null)));
            this.setMinWidth(230);
            this.setMaxWidth(230);
            this.setMinHeight(30);
            this.setMaxHeight(30);
            this.setAlignment(Pos.CENTER);
        }
    }

}