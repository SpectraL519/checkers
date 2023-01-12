package com.CheckersGame.Client.View.GameViewComponents.Fields;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * @author Krzysztof Dobrucki
 * @version 1.0
 * PositionField class
 */
public class PositionField extends Field {

    /**
     * PositionField class constructor
     * @param color
     * @param pixelSizeV
     * @param pixelSizeH
     * @param position
     */
    public PositionField(Color color, int pixelSizeV, int pixelSizeH, int position) {
        super(color, pixelSizeV, pixelSizeH);
        this.getChildren().addAll(new PositionLabel(position, pixelSizeV, pixelSizeH));
        this.setBorder(new Border(
                new BorderStroke(Color.BLACK, BorderStrokeStyle.DOTTED, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }



    /**
     * PositionLabel class contains position label
     */
    class PositionLabel extends Label {
        public PositionLabel(int position, int pixelSizeV, int pixelSizeH) {
            super(String.valueOf(position));
            this.setMinWidth(pixelSizeH);
            this.setMaxWidth(pixelSizeH);
            this.setMinHeight(pixelSizeV);
            this.setMaxHeight(pixelSizeV);
            this.setAlignment(Pos.CENTER);
            setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        }
    }
}
