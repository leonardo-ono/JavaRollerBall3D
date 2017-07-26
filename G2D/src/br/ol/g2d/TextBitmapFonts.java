package br.ol.g2d;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * TextBitmapFonts class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class TextBitmapFonts implements Serializable {
    
    private transient G2DContext context;
    public List<TextBitmapFont> fonts = new ArrayList<TextBitmapFont>();
    public TextBitmapFont selectedFont;
    
    public TextBitmapFonts() {
    }

    public G2DContext getContext() {
        return context;
    }
    
    public void updateContext(G2DContext context) {
        this.context = context;
        for (TextBitmapFont font : fonts) {
            font.updateContext(context);
        }
    }
    
    public TextBitmapFont getSelectedFont() {
        return selectedFont;
    }

    public void setSelectedFont(TextBitmapFont selectedFont) {
        this.selectedFont = selectedFont;
    }

    public List<TextBitmapFont> getFonts() {
        return fonts;
    }

    public TextBitmapFont get(String fontName) {
        for (TextBitmapFont font : fonts) {
            if (font.getName().equals(fontName)) {
                return font;
            }
        }
        return null;
    }
    
    public void create(String name, int width, int height) {
        TextBitmapFont newFont = new TextBitmapFont(name, width, height);
        fonts.add(newFont);
    }

    public void remove(String name) {
        TextBitmapFont font = get(name);
        if (font != null) {
            fonts.remove(font);
        }
    }
    
}
