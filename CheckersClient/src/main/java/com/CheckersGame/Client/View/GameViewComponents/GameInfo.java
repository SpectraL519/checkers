package com.CheckersGame.Client.View.GameViewComponents;

import com.CheckersGame.Client.GameController;
import com.CheckersGame.Client.View.GameView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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




/**
 * @author Krzysztof Dobrucki
 * @author Jakub Musiał
 * @version 1.0
 * GameInfo class
 */
public class GameInfo extends VBox {
    private int horizontalSpace;

    private GameController gameController;
    private GameView gameView;

    private PlayerInfo playerInfo;
    private GameLog gameLog;
    private MoveButtonFrame moveFrame;




    /**
     * GameInfo class constructor
     * @param gameController
     * @param gameView
     */
    public GameInfo (GameController gameController, GameView gameView) {
        super();

        this.horizontalSpace = 374;

        this.gameController = gameController;
        this.gameView = gameView;
    }



    /**
     * Shows Game info
     */
    public void render () {
        this.setBackground(new Background(new BackgroundFill(Color.CORNSILK, null, null)));

        this.playerInfo = new PlayerInfo(horizontalSpace, null);
        this.gameLog = new GameLog(horizontalSpace);
        this.moveFrame = new MoveButtonFrame(horizontalSpace, this.gameController, this.gameView);

        this.getChildren().addAll(this.playerInfo, this.gameLog, this.moveFrame);
    }


    /**
     * Sets player
     * @param player
     */
    public void setPlayer (String player) {
        this.playerInfo.setPlayer(player);
    }



    /**
     * Updates Log
     * @param newLog
     */
    public void updateLog (String newLog) {
        this.gameLog.updateLog(newLog);
    }



    /**
     * PlayerInfo class contains player info
     */
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

        public void setPlayer (String player) {
            this.setText("You're playing as: " + player);
        }
    }


    /**
     * GameLog class contains game log
     */
    class GameLog extends ScrollPane {
        private Label log;

        public GameLog (int horizontalSpace) {
            super();

            this.log = new Label("Game log:");
            this.log.setFont(Font.font("Monospace", 12));
            this.log.setWrapText(true);

            this.setMinWidth(horizontalSpace);
            this.setMaxWidth(horizontalSpace);
            this.setMinHeight(500);
            this.setMaxHeight(500);
            this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.DOTTED, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            this.setContent(this.log);
        }

        public void updateLog (String newLog) {
            this.log.setText(this.log.getText() + "\n" + newLog);
        }
    }



    /**
     * MoveButtonFrame class is a frame for move button
     */
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



    /**
     * MoveButton class contains move button
     */
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
