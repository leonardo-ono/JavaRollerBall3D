package br.ol.g2d;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * TextBitmapScreens class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class TextBitmapScreens implements Serializable {

    private transient G2DContext context;
    public List<TextBitmapScreen> screens = new ArrayList<TextBitmapScreen>();
    public TextBitmapScreen selectedScreen;
    
    public TextBitmapScreens() {
    }

    public G2DContext getContext() {
        return context;
    }
    
    public void updateContext(G2DContext context) {
        this.context = context;
        for (TextBitmapScreen screen : screens) {
            screen.updateContext(context);
        }
    }

    public TextBitmapScreen getSelectedScreen() {
        return selectedScreen;
    }

    public void setSelectedScreen(TextBitmapScreen selectedScreen) {
        this.selectedScreen = selectedScreen;
    }

    public List<TextBitmapScreen> getScreens() {
        return screens;
    }

    public TextBitmapScreen get(String screenName) {
        for (TextBitmapScreen screen : screens) {
            if (screen.getName().equals(screenName)) {
                return screen;
            }
        }
        return null;
    }
    
    public void create(String name, TextBitmapFont font, int width, int height) {
        TextBitmapScreen newScreen = new TextBitmapScreen(name, font, width, height);
        screens.add(newScreen);
    }

    public void remove(String name) {
        TextBitmapScreen screen = get(name);
        if (screen != null) {
            screens.remove(screen);
        }
    }
    
}
