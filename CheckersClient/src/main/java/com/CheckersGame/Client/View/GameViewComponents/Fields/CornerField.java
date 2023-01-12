package com.CheckersGame.Client.View.GameViewComponents.Fields;

import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;




/**
 * @author Krzysztof Dobrucki
 * @version 1.0
 * CornerField class
 */
public class CornerField extends Field {


    /**
     * CornerField class constructor
     * @param color
     * @param pixelSizeV
     * @param pixelSizeH
     */
    public CornerField(Color color, int pixelSizeV, int pixelSizeH) {
        super(color, pixelSizeV, pixelSizeH);
        this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.DOTTED, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }
    
}
