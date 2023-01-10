package com.CheckersGame.Client.View;

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

    public GameInfo (String colorOfPawns) {
        super();
        this.horizontalSpace = 374;
        this.verticalSpace = 650;
        this.colorOfPawns = colorOfPawns;
    }

    public void render() {
        this.getChildren().addAll(new PlayerLabel(horizontalSpace, colorOfPawns), new GameHistoryLabel(horizontalSpace), new GameLogLabel(horizontalSpace));
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
        public GameHistoryLabel (int horizontalSpace) {
            super("1.  23-45   12-65\n2.  41-45   34-28\n3.  45x14");
            this.setMinWidth(horizontalSpace);
            this.setMaxWidth(horizontalSpace);
            this.setMinHeight(500);
            this.setMaxHeight(500);
            this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.DOTTED, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        }
    }

    class GameLogLabel extends VBox {
        public GameLogLabel (int horizontalSpace) {
            super();
            this.setMinWidth(horizontalSpace);
            this.setMaxWidth(horizontalSpace);
            this.setMinHeight(100);
            this.setMaxHeight(100);
            this.setPadding(new Insets(5, 5, 5, 5));
            this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.DOTTED, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            this.getChildren().addAll(new MoveButton());
        }
    }

    class MoveButton extends Button {
        public MoveButton () {
            super("MOVE!");
            this.setMinHeight(88);
            this.setMaxHeight(88);
            this.setMinWidth(362);
            this.setMaxWidth(362);
            this.setStyle("-fx-font-size:40");
            this.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                  System.out.println("Move pawn");
                }
            });
        }
    }
}
