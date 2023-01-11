package com.CheckersGame.Client.View.Menus;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import com.CheckersGame.Client.View.AppView;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;



public class WaitPlayerMenu extends BorderPane {

    private VBox menu;

    public WaitPlayerMenu () {
        super();
        System.out.println("WaitPlayerMenu: checkpoint 1");
        this.menu = null;
        System.out.println("WaitPlayerMenu: checkpoint 2");
    }
    
    public void render() {
        System.out.println("WaitPlayerMenu: checkpoint 3");
        // this.setPrefSize(AppView.WIDTH, AppView.HEIGHT);
        this.menu = new VBox();
        this.menu.setBackground(new Background(new BackgroundFill(Color.CORAL, null, null)));
        this.menu.setPadding(new Insets(10, 10, 10, 10));
        this.menu.setSpacing(10);
        this.menu.setAlignment(Pos.CENTER);
        this.menu.setMinWidth(250);
        this.menu.setMaxWidth(250);
        this.menu.getChildren().addAll(new WelcomeText());
        this.setCenter(this.menu);
        System.out.println("WaitPlayerMenu: checkpoint 4");
    }

    class WelcomeText extends Label {
        public WelcomeText () {
            super("Waiting for Your oponent...");
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
