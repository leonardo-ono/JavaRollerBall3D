package br.ol.g2d;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.Serializable;

/**
 * PaletteColor class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class PaletteColor implements Serializable, Comparable<PaletteColor> {

    private Color color;
    private final Rectangle rectangle = new Rectangle(0, 0, 0, 0);
    private int order;

    public PaletteColor(Color color, int order) {
        this.color = color;
        this.order = order;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Rectangle getRectangle() { 
        return rectangle;
    }
    
    public void setRectangle(int x, int y, int width, int height) {
        rectangle.setBounds(x, y, width, height);
    }
    
    public boolean isInsideRectangle(int x, int y) {
        return rectangle.contains(x, y);
    }
    
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.fill(rectangle);
    }
    
    @Override
    public int compareTo(PaletteColor o) {
        return getColor().getRGB() - o.getColor().getRGB();
    }
    
    @Override
    public String toString() {
        return "PaletteColor{" + "color=" + color + ", order=" + order + '}';
    }

    
}
