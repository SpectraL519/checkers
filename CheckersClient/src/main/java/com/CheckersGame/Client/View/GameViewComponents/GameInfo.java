package com.CheckersGame.Client.View.GameViewComponents;

import com.CheckersGame.Client.GameController;
import com.CheckersGame.Client.View.GameView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


//VBox or GridPane?


public class GameInfo extends VBox {

    private int horizontalSpace;
    private int verticalSpace;
    private String colorOfPawns;
    private GameController gameController;
    private GameView gameView;
    private String gameLog;

    public GameInfo (String colorOfPawns, GameController gameController, GameView gameView, String gameLog) {
        super();
        this.horizontalSpace = 374;
        this.verticalSpace = 650;
        this.colorOfPawns = colorOfPawns;
        this.gameController = gameController;
        this.gameView = gameView;
        this.gameLog = gameLog;
    }

    public void render() {
        this.getChildren().addAll(new PlayerLabel(horizontalSpace, colorOfPawns), new GameHistoryLabel(horizontalSpace, gameLog), new MoveButtonFrame(horizontalSpace, this.gameController, this.gameView));
    }


    class PlayerLabel extends Label {
        public PlayerLabel (int horizontalSpace, String colorOfPawns) {
            super("You're playing as: " + colorOfPawns);
            this.setMinWidth(horizontalSpace);
            this.setMaxWidth(horizontalSpace);
            this.setMinHeight(50);
            this.setMaxHeight(50);
            this.setAlignment(Pos.CENTER);
            this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.DOTTED, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        }
    }

    class GameHistoryLabel extends Label {
        public GameHistoryLabel (int horizontalSpace, String gameLog) {
            super(gameLog);
            this.setMinWidth(horizontalSpace);
            this.setMaxWidth(horizontalSpace);
            this.setMinHeight(500);
            this.setMaxHeight(500);
            this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.DOTTED, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        }
    }

    class MoveButtonFrame extends VBox {
        public MoveButtonFrame (int horizontalSpace, GameController gameController, GameView gameView) {
            super();
            this.setMinWidth(horizontalSpace);
            this.setMaxWidth(horizontalSpace);
            this.setMinHeight(100);
            this.setMaxHeight(100);
            this.setPadding(new Insets(5, 5, 5, 5));
            this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.DOTTED, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            this.getChildren().addAll(new MoveButton(gameController, gameView));
        }
    }

    class MoveButton extends Button {
        public MoveButton (GameController gameController, GameView gameView) {
            super("MOVE!");
            this.setMinHeight(88);
            this.setMaxHeight(88);
            this.setMinWidth(362);
            this.setMaxWidth(362);
            this.setStyle("-fx-font-size:40");
            this.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if(gameController.isAstive()) {
                        int[] coordinatres = gameView.board.controller.makeMove();
                        gameController.movePawn(coordinatres[0], coordinatres[1], coordinatres[2], coordinatres[3]);
                    }
                }
            });
        }
    }
}
