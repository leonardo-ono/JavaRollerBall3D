package br.ol.g2d;

import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * TextBitmapFont class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class TextBitmapFont implements Serializable {
    
    private transient G2DContext context;
    public String name;
    public Map<Character, Sprite> characters = new HashMap<Character, Sprite>();
    public int width;
    public int height;

    public TextBitmapFont(String name, int width, int height) {
        this.name = name;
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
    
    public void addChar(char c, Sprite charSprite) {
        characters.put(c, charSprite);
    }

    public void removeChar(char c) {
        characters.remove(c);
    }
    
    public boolean isCharAvailable(char c) {
        return characters.get(c) != null;
    }
    
    public void drawChar(Graphics2D g, char c, int x, int y) {
        Sprite charSprite = characters.get(c);
        if (charSprite != null) {
            charSprite.draw(g, x, y);
        }
    }
    
}
