package com.CheckersGame.Client.View;

import javafx.geometry.Pos;
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

    public GameInfo () {
        super();
        this.horizontalSpace = 374;
        this.verticalSpace = 650;
    }

    public void render() {
        this.getChildren().addAll(new PlayersLabel(horizontalSpace), new GameHistoryLabel(horizontalSpace), new GameLogLabel(horizontalSpace));
    }


    class PlayersLabel extends Label {
        public PlayersLabel (int horizontalSpace) {
            super("Krzys 2.5 : 1.5 Kubus");
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

    class GameLogLabel extends Label {
        public GameLogLabel (int horizontalSpace) {
            super("Here will be some messeges from server:\nCorrect moive!");
            this.setMinWidth(horizontalSpace);
            this.setMaxWidth(horizontalSpace);
            this.setMinHeight(100);
            this.setMaxHeight(100);
            this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.DOTTED, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        }
    }
}
