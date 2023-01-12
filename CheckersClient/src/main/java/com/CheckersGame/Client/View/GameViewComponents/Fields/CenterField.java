package com.CheckersGame.Client.View.GameViewComponents.Fields;

import javafx.scene.paint.Color;

import com.CheckersGame.Client.View.GameViewComponents.GameBoard;

import javafx.event.Event;
import javafx.event.EventHandler;





/**
 * @author Krzysztof Dobrucki
 * @version 1.0
 * CenterField class
 */
public class CenterField extends Field {

    public final int row;
    public final int column;
    public final Color color;



    /**
     * CenterField class constructor
     * @param color
     * @param pixelSizeV
     * @param pixelSizeH
     * @param row
     * @param column
     * @param board
     */
    public CenterField (final Color color, int pixelSizeV, int pixelSizeH, final int row, final int column, final GameBoard board) {

        super(color, pixelSizeV, pixelSizeH);

        this.row = row;
        this.column = column;
        this.color = color;
        
        this.whitePawn = new Pawn((pixelSizeH - 20) / 2, Color.WHITESMOKE, null);
        whitePawn.setLayoutX(this.getLayoutX() + (pixelSizeH / 2));
        whitePawn.setLayoutY(this.getLayoutY() + (pixelSizeV / 2));
        this.getChildren().add(whitePawn);

        this.blackPawn = new Pawn((pixelSizeH - 20) / 2, Color.BLACK, null);
        blackPawn.setLayoutX(this.getLayoutX() + (pixelSizeH / 2));
        blackPawn.setLayoutY(this.getLayoutY() + (pixelSizeV / 2));
        this.getChildren().add(blackPawn);

        this.whitePawnQueen = new Pawn((pixelSizeH - 20) / 2, Color.WHITESMOKE, Color.BLACK);
        whitePawnQueen.setLayoutX(this.getLayoutX() + (pixelSizeH / 2));
        whitePawnQueen.setLayoutY(this.getLayoutY() + (pixelSizeV / 2));
        this.getChildren().add(whitePawnQueen);

        this.blackPawnQueen = new Pawn((pixelSizeH - 20) / 2, Color.BLACK, Color.WHITESMOKE);
        blackPawnQueen.setLayoutX(this.getLayoutX() + (pixelSizeH / 2));
        blackPawnQueen.setLayoutY(this.getLayoutY() + (pixelSizeV / 2));
        this.getChildren().add(blackPawnQueen);

        whitePawn.setVisible(false);
        blackPawn.setVisible(false);
        whitePawnQueen.setVisible(false);
        blackPawnQueen.setVisible(false);

        this.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event e) {
                System.out.println(String.format("(row,column): (%d,%d)", row, column));
                board.controller.askController(row, column);
            }
        });
     

    }

    /**
     * Shows White Pawn
     */
    public void showWhitePawn(boolean b){
        this.whitePawn.setVisible(b);
    }

    /**
     * Shows Black Pawn
     */
    public void showBlackPawn(boolean b){
        this.blackPawn.setVisible(b);
    }

    /**
     * Shows White Pawn Queen
     */
    public void showWhitePawnQueen(boolean b){
        this.whitePawnQueen.setVisible(b);
    }

    /**
     * Shows Black Pawn Queen
     */
    public void showBlackPawnQueen(boolean b){
        this.blackPawnQueen.setVisible(b);
    }

}
