package com.CheckersGame.Client.View.GameViewComponents.Fields;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;




/**
 * @author Krzysztof Dobrucki
 * @version 1.0
 * Pawn class
 */
public class Pawn extends Circle {

    /**
     * Pawn class constructor
     * @param r
     * @param color
     * @param stroke
     */
    public Pawn(int r, Color color, Color stroke) {
        super(r, color);
        this.setStrokeWidth(3);
        this.setStrokeMiterLimit(10);
        this.setStrokeType(StrokeType.CENTERED);
        this.setStroke(stroke);
    }

}
