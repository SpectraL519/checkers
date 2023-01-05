package com.CheckersGame.Client.View;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;



public class Field extends Pane {

    public Pawn whitePawn;
    public Pawn blackPawn;

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

}
