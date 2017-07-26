package br.ol.g2d;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * G2DContext class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class G2DContext {

    private G2DData data;
    private List<Object> messageReceivers = new ArrayList<Object>();

    public G2DContext() {
    }

    public G2DData getData() {
        return data;
    }
    
    public SpriteSheet getSpriteSheet() {
        if (data == null) {
            return null;
        }
        return data.getSpriteSheet();
    }

    public List<Sprite> getSprites() {
        if (getSpriteSheet() == null) {
            return null;
        }
        return getSpriteSheet().getSprites();
    }

    public Sprite getSelectedSprite() {
        if (getSpriteSheet() == null) {
            return null;
        }
        return getSpriteSheet().getSelectedSprite();
    }

    public Palette getPalette() {
        if (data == null) {
            return null;
        }
        return data.getPalette();
    }
    
    public PaletteColor getSelectedPaletteColor() {
        if (getPalette() == null) {
            return null;
        }
        return getPalette().getSelectedColor();
    }

    public Animations getAnimations() {
        if (data == null) {
            return null;
        }
        return data.getAnimations();
    }

    public Animation getSelectedAnimation() {
        if (getAnimations() == null) {
            return null;
        }
        return getAnimations().getSelectedAnimation();
    }
    
    public AnimationObjectInterface getSelectedAnimationObject() {
        if (getSelectedAnimation() == null) {
            return null;
        }
        return getSelectedAnimation().getSelectedObject();
    }

    public TextBitmapFonts getFonts() {
        if (data == null) {
            return null;
        }
        return data.getFonts();
    }

    public TextBitmapFont getSelectedFont() {
        if (getFonts() == null) {
            return null;
        }
        return getFonts().getSelectedFont();
    }

    public TextBitmapScreens getTextScreens() {
        if (data == null) {
            return null;
        }
        return data.getTextScreens();
    }
    
    public TextBitmapScreen getSelectedTextScreen() {
        if (getTextScreens() == null) {
            return null;
        }
        return getTextScreens().getSelectedScreen();
    }
    
    public void newGridSpriteSheet(int cols, int rows, int spriteWidth, int spriteHeight, int defaultScreenWidth, int defaultScreenHeight) {
        data = new G2DData(cols, rows, spriteWidth, spriteHeight, defaultScreenWidth, defaultScreenHeight);
        data.updateContext(this);

        //data.getAnimations().create("animation 1");
        //data.getAnimations().create("animation 2");
        //data.getAnimations().create("animation 3");
        
        update();
    }

    public void newSpriteSheetFromImage(String filename, int defaultScreenWidth, int defaultScreenHeight) throws Exception {
        data = new G2DData(filename, defaultScreenWidth, defaultScreenHeight);
        data.updateContext(this);
        update();
    }
    
    public void load(String filename) throws Exception {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
        data = (G2DData) ois.readObject();
        data.updateContext(this);
        data.setFilename(filename);
        ois.close();
        update();
    }

    public void loadFromResource(String resource) throws Exception {
        ObjectInputStream ois = new ObjectInputStream(getClass().getResourceAsStream(resource));
        data = (G2DData) ois.readObject();
        data.updateContext(this);
        ois.close();
        update();
    }

    public void save(String filename) throws Exception {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
        oos.writeObject(data);
        oos.close();
        data.setFilename(filename);
    }

    public void close() {
        data = null;
        update();
    }

    public void registerAsMessageReceiver(Object messageReceiver) {
        messageReceivers.add(messageReceiver);
    }
    
    public void update() {
        for (Object messageReceiver : messageReceivers) {
            try {
                Method method = messageReceiver.getClass().getMethod("update");
                if (method != null) {
                    method.invoke(messageReceiver);
                }
            } catch (Exception ex) {
            }
        }
    }

}
