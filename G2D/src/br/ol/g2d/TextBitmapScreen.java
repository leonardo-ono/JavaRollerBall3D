package br.ol.g2d;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * TextBitmapScreen class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class TextBitmapScreen implements Serializable {
    
    private transient G2DContext context;
    public String name;
    public TextBitmapFont font;
    public int width;
    public int height;
    public int offsetX;
    public int offsetY;
    public int spacingX;
    public int spacingY;
    public Map<Point, Character> charsScreen = new HashMap<Point, Character>();
    private final Point auxPoint = new Point();
    public boolean showGridEditor; // just for editor
    
    public TextBitmapScreen(String name, TextBitmapFont font, int width, int height) {
        this.name = name;
        this.font = font;
        this.width = width;
        this.height = height;
    }

    public G2DContext getContext() {
        return context;
    }
    
    public void updateContext(G2DContext context) {
        this.context = context;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }

    public int getSpacingX() {
        return spacingX;
    }

    public void setSpacingX(int spacingX) {
        this.spacingX = spacingX;
    }

    public int getSpacingY() {
        return spacingY;
    }

    public void setSpacingY(int spacingY) {
        this.spacingY = spacingY;
    }

    public TextBitmapFont getFont() {
        return font;
    }

    public void setFont(TextBitmapFont font) {
        this.font = font;
    }
    
    public void clearScreen() {
        charsScreen.clear();
    }
    
    public void print(int col, int row, String text) {
        for (int x = 0; x < text.length(); x++) {
            Character c = text.charAt(x);
            Point newPoint = new Point(col + x, row);
            charsScreen.put(newPoint, c);
        }
    }
    
    public Character getCharAtPosition(int col, int row) {
        auxPoint.setLocation(col, row);
        return charsScreen.get(auxPoint);
    }

    public void removeChar(int col, int row) {
        auxPoint.setLocation(col, row);
        charsScreen.remove(auxPoint);
    }
        
    public void draw(Graphics2D g) {
        for (Entry<Point, Character> entry : charsScreen.entrySet()) {
            Point position = entry.getKey();
            Character c = entry.getValue();
            if (c != null) {
                int cx = offsetX + position.x * (font.width + spacingX);
                int cy = offsetY + position.y * (font.height + spacingY);
                font.drawChar(g, c, cx, cy);
            }
        }
    }
    
    public void drawGrid(Graphics2D g) {
        int cols = width / font.getWidth();
        int rows = height / font.getHeight();
        g.setColor(Color.DARK_GRAY);
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int cx = offsetX + col * (font.width + spacingX);
                int cy = offsetY + row * (font.height + spacingY);
                g.drawRect(cx, cy, font.getWidth(), font.getHeight());
            }
        }
    }

    public void drawCursor(Graphics2D g, int col, int row) {
        int cx = offsetX + col * (font.width + spacingX);
        int cy = offsetY + row * (font.height + spacingY);
        g.setXORMode(Color.WHITE);
        g.fillRect(cx, cy, font.getWidth(), font.getHeight());
        g.setPaintMode();
    }

    public Point getCridCellPosition(int x, int y) {
        int col = (x - offsetX) / (font.width + spacingX);
        int row = (y - offsetY) / (font.height + spacingY);
        auxPoint.setLocation(col, row);
        return auxPoint;
    }

}
