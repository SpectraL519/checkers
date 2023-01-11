package com.CheckersGame.Client.View.GameViewComponents;

import com.CheckersGame.Client.GameController;
import com.CheckersGame.Client.View.GameView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;





public class GameInfo extends VBox {
    private int horizontalSpace;

    private String player;
    private GameController gameController;
    private GameView gameView;

    private PlayerInfo playerInfo;
    private GameLog log;
    private MoveButtonFrame moveFrame;



    public GameInfo (String player, GameController gameController, GameView gameView) {
        super();

        this.horizontalSpace = 374;

        this.player = player;
        this.gameController = gameController;
        this.gameView = gameView;
    }



    public void render () {
        this.setBackground(new Background(new BackgroundFill(Color.CORNSILK, null, getInsets())));

        this.playerInfo = new PlayerInfo(horizontalSpace, player);
        this.log = new GameLog(horizontalSpace);
        this.moveFrame = new MoveButtonFrame(horizontalSpace, this.gameController, this.gameView);

        this.getChildren().addAll(this.playerInfo, this.log, this.moveFrame);
    }



    public void setPlayer (String player) {
        this.player = player;
    }



    public void updateLog (String newLog) {
        this.log.setText(this.log.getText() + "\n" + newLog);
    }



    class PlayerInfo extends Label {
        public PlayerInfo (int horizontalSpace, String player) {
            super("You're playing as: " + player);
            this.setFont(Font.font("Monospace", 20));
            this.setMinWidth(horizontalSpace);
            this.setMaxWidth(horizontalSpace);
            this.setMinHeight(50);
            this.setMaxHeight(50);
            this.setAlignment(Pos.CENTER);
            this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.DOTTED, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        }
    }



    class GameLog extends Label {
        public GameLog (int horizontalSpace) {
            super("Game log:");
            this.setFont(Font.font("Monospace", 20));
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
