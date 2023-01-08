package com.CheckersGame.Client.View;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import javax.swing.plaf.basic.BasicDesktopIconUI.MouseInputHandler;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;




public class CenterField extends Field {

    public final int row;
    public final int column;
    public final Color color;

    public CenterField (final Color color, int pixelSizeV, int pixelSizeH, final int row, final int column, final GameBoard board) {

        super(color, pixelSizeV, pixelSizeH);

        this.row = row;
        this.column = column;
        this.color = color;
        
        this.whitePawn = new Pawn((pixelSizeH - 20) / 2, Color.WHITESMOKE);
        whitePawn.setLayoutX(this.getLayoutX() + (pixelSizeH / 2));
        whitePawn.setLayoutY(this.getLayoutY() + (pixelSizeV / 2));
        this.getChildren().add(whitePawn);

        this.blackPawn = new Pawn((pixelSizeH - 20) / 2, Color.BLACK);
        blackPawn.setLayoutX(this.getLayoutX() + (pixelSizeH / 2));
        blackPawn.setLayoutY(this.getLayoutY() + (pixelSizeV / 2));
        this.getChildren().add(blackPawn);

        whitePawn.setVisible(false);
        blackPawn.setVisible(false);

        this.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event e) {
                System.out.println("row: " + row + "column: " + column);
                board.controller.askController(row, column);
            }
        });
     

    }

    public void showWhitePawn(boolean b){
        this.whitePawn.setVisible(b);
    }

    public void showBlackPawn(boolean b){
        this.blackPawn.setVisible(b);
    }

}
