package com.CheckersGame.Client.View.Menus;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import com.CheckersGame.Client.GameController;
import com.CheckersGame.Client.View.AppView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;



public class WelcomeMenu extends BorderPane {

    private AppView view;
    private GameController gameController;
    private VBox menu;

    public WelcomeMenu (AppView view, GameController gameController) {
        super();
        System.out.println("WelcomeMenu: checkpoint 1");
        this.view = view;
        this.gameController = gameController;
        this.menu = null;
        System.out.println("WelcomeMenu: checkpoint 2");
    }
    
    public void render () {
        System.out.println("WelcomeMenu: checkpoint 3");
        // this.setPrefSize(AppView.WIDTH, AppView.HEIGHT);
        this.menu = new VBox();
        this.menu.setBackground(new Background(new BackgroundFill(Color.CORAL, null, null)));
        this.menu.setPadding(new Insets(10, 10, 10, 10));
        this.menu.setSpacing(10);
        this.menu.setAlignment(Pos.CENTER);
        this.menu.setMinWidth(250);
        this.menu.setMaxWidth(250);
        this.menu.getChildren().addAll(new WelcomeText(), new StartButton(this.view, this.gameController));
        // this.getChildren().addAll(this.menu);
        this.setCenter(this.menu);
        System.out.println("WelcomeMenu: checkpoint 4");
    }

    //TEST_ROZPACZY
    public void changeColor (Color color) {
        this.menu.setBackground(new Background(new BackgroundFill(color, null, null)));
    }

    class WelcomeText extends Label {
        public WelcomeText () {
            super("Welcome in Checkers Game!");
            CornerRadii corner = new CornerRadii(3);
            this.setBackground(new Background(new BackgroundFill(Color.WHITE, corner, null)));
            this.setMinWidth(230);
            this.setMaxWidth(230);
            this.setMinHeight(70);
            this.setMaxHeight(70);
            this.setAlignment(Pos.CENTER);
            this.setContentDisplay(ContentDisplay.CENTER);
        }
    }

    class StartButton extends Button {
        public StartButton (AppView view, GameController gameController) {
            super("START!");
            this.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    // view.renderWaitPlayerMenu();
                    gameController.startModel();
                }
            });
        }
    }

}
