package com.CheckersGame.Client.View.GameViewComponents.Fields;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;




/**
 * @author Krzysztof Dobrucki
 * @version 1.0
 * Field class
 */
public class Field extends Pane {

    public Pawn whitePawn;
    public Pawn blackPawn;
    public Pawn whitePawnQueen;
    public Pawn blackPawnQueen;



    /**
     * Pawn class constructor
     * @param color
     * @param pixelSizeV
     * @param pixelSizeH
     */
    public Field (Color color, int pixelSizeV, int pixelSizeH) {
        super();
        this.setMinWidth(pixelSizeH);
        this.setMaxWidth(pixelSizeH);
        this.setMinHeight(pixelSizeV);
        this.setMaxHeight(pixelSizeV);
        this.setBackground(new Background(new BackgroundFill(color, null, null)));
    }

    public void showWhitePawn(boolean b){}

    public void showBlackPawn(boolean b){}

    public void showWhitePawnQueen(boolean b){}

    public void showBlackPawnQueen(boolean b){}

}
