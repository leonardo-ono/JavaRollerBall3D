package br.ol.g2d;

import java.io.Serializable;

/**
 * G2DData class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class G2DData implements Serializable {
    
    private transient G2DContext context;
    private String filename;
    private SpriteSheet spriteSheet;
    private Palette palette;
    private Animations animations;
    private TextBitmapFonts fonts;
    private TextBitmapScreens textScreens;
    
    // new grid sprite sheet
    public G2DData(int cols, int rows, int spriteWidth, int spriteHeight, int defaultScreenWidth, int defaultScreenHeight) {
        filename = null;
        spriteSheet = new SpriteSheet(cols, rows, spriteWidth, spriteHeight);
        palette = new Palette();
        animations = new Animations(defaultScreenWidth, defaultScreenHeight);
        fonts = new TextBitmapFonts();
        textScreens = new TextBitmapScreens();
    }

    // new sprite sheet from image file
    public G2DData(String imageFilename, int defaultScreenWidth, int defaultScreenHeight) throws Exception {
        filename = null;
        spriteSheet = new SpriteSheet(imageFilename);
        palette = new Palette(spriteSheet.getUsedPaletteColors());
        animations = new Animations(defaultScreenWidth, defaultScreenHeight);
        fonts = new TextBitmapFonts();
        textScreens = new TextBitmapScreens();
    }

    public G2DContext getContext() {
        return context;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public SpriteSheet getSpriteSheet() {
        return spriteSheet;
    }

    public void setSpriteSheet(SpriteSheet spriteSheet) {
        this.spriteSheet = spriteSheet;
    }

    public Palette getPalette() {
        return palette;
    }

    public void setPalette(Palette palette) {
        this.palette = palette;
    }

    public Animations getAnimations() {
        return animations;
    }

    public void setAnimations(Animations animations) {
        this.animations = animations;
    }

    public TextBitmapFonts getFonts() {
        return fonts;
    }

    public void setFonts(TextBitmapFonts fonts) {
        this.fonts = fonts;
    }

    public TextBitmapScreens getTextScreens() {
        return textScreens;
    }

    public void setTextScreens(TextBitmapScreens textScreens) {
        this.textScreens = textScreens;
    }

    public void updateContext(G2DContext context) {
        this.context = context;
        spriteSheet.updateContext(context);
        palette.updateContext(context);
        animations.updateContext(context);
        fonts.updateContext(context);
        textScreens.updateContext(context);
    }

}
