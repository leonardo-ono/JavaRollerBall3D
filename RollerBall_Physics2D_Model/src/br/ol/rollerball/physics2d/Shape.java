package br.ol.rollerball.physics2d;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Shape class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class Shape {
    
    protected Color debugColor = Color.BLACK;

    public Color getDebugColor() {
        return debugColor;
    }

    public void setDebugColor(Color debugColor) {
        this.debugColor = debugColor;
    }
    
    public void drawDebug(Graphics2D g) {
    }
    
}
