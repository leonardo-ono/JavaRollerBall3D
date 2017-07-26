package br.ol.g2d;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.imageio.ImageIO;

/**
 * SpriteSheet class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class SpriteSheet implements Serializable {
    
    private transient G2DContext context;
    private Color backgroundColor = Color.MAGENTA;
    private transient BufferedImage image;
    private int width, height;
    private int[] imageData;
    private List<Sprite> sprites = new ArrayList<Sprite>();
    private Sprite selectedSprite;
    private String spriteBaseName = "sprite";
    
    // new grid sprite sheet
    public SpriteSheet(int cols, int rows, int spriteWidth, int spriteHeight) {
        this.width = cols * spriteWidth;
        this.height = rows * spriteHeight;
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        imageData = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        Graphics g = image.getGraphics();
        g.setColor(Color.MAGENTA);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        createSpritesInGrid(cols, rows, spriteWidth, spriteHeight);
    }
    
    // new sprite sheet from image file 
    public SpriteSheet(String imageFilename) throws Exception {
        BufferedImage imageProv = ImageIO.read(new File(imageFilename));
        this.width = imageProv.getWidth();
        this.height = imageProv.getHeight();
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        g.drawImage(imageProv, 0, 0, null);
        imageData = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    }
    
    public G2DContext getContext() {
        return context;
    }
    
    public void updateContext(G2DContext context) {
        this.context = context;
        for (Sprite sprite : sprites) {
            sprite.updateContext(context);
        }
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        if (this.backgroundColor != backgroundColor) {
            this.backgroundColor = backgroundColor;
            context.update();
        }
    }

    public BufferedImage getImage() {
        if (image == null) {
            image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            int[] pImageData = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
            System.arraycopy(imageData, 0, pImageData, 0, pImageData.length);
            imageData = pImageData;
            
        }
        return image;
    }

    public int[] getImageData() {
        return imageData;
    }

    public List<Sprite> getSprites() {
        return sprites;
    }
    
    public void addSprite(Sprite sprite) {
        sprites.add(sprite);
        context.update();
    }

    public Sprite getSprite(String spriteName) {
        for (Sprite sprite : sprites) {
            if (sprite.getName().equals(spriteName)) {
                return sprite;
            }
        }
        return null;
    }

    public Sprite getSprite(int x, int y) {
        for (Sprite sprite : sprites) {
            if (sprite.getRectangle().contains(x, y)) {
                return sprite;
            }
        }
        return null;
    }

    public Sprite getSelectedSprite() {
        return selectedSprite;
    }

    public void setSelectedSprite(Sprite selectedSprite) {
        if (this.selectedSprite != selectedSprite) {
            this.selectedSprite = selectedSprite;
            context.update();
        }
    }

    public String getSpriteBaseName() {
        return spriteBaseName;
    }

    public void setSpriteBaseName(String spriteBaseName) {
        this.spriteBaseName = spriteBaseName;
        context.update();
    }
    
    public String getNextAvailableSpriteName() {
        if (spriteBaseName == null || spriteBaseName.trim().isEmpty()) {
            spriteBaseName = "sprite_" + System.nanoTime();
        }
        String baseName = spriteBaseName + "_";
        int sequence = -1;
        for (Sprite sprite : sprites) {
            if (sprite.getName().startsWith(baseName)) {
                try {
                    String seqStr = sprite.getName().substring(baseName.length(), sprite.getName().length());
                    int spriteSequence = Integer.parseInt(seqStr);
                    if (spriteSequence > sequence) {
                        sequence = spriteSequence;
                    }
                }
                catch (NumberFormatException e) {}
            }
        }
        return baseName + (++sequence); 
    }
    
    public void defineSpriteGridCell(String spriteName, int col, int row, int spriteWidth, int spriteHeight) {
        Sprite sprite = new Sprite(spriteName, this);
        sprite.setRectangle(col * spriteWidth, row * spriteHeight, spriteWidth, spriteHeight);
        sprites.add(sprite);
    }
    
    private void createSpritesInGrid(int cols, int rows, int spriteWidth, int spriteHeight) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                defineSpriteGridCell("sprite_" + col + "_" + row, col, row, spriteWidth, spriteHeight);
            }
        }
    }
    
    public void setPixel(int x, int y, int c) {
        if (image == null || x < 0 || y < 0 
                || x > image.getWidth() - 1 || y > image.getHeight() - 1) {
        }
        image.setRGB(x, y, c);
    }

    public int getPixel(int x, int y) {
        if (image == null || x < 0 || y < 0 
                || x > image.getWidth() - 1 || y > image.getHeight() - 1) {
            return 0;
        }
        return image.getRGB(x, y);
    }
    
    public boolean intersectsExistingSprites(int x, int y, int width, int height) {
        for (Sprite sprite : sprites) {
            if (sprite.getRectangle().intersects(x, y, width, height)) {
                return true;
            }
        }
        return false;
    }

    public boolean intersectsExistingSprites(Rectangle rectangle) {
        for (Sprite sprite : sprites) {
            if (sprite.getRectangle().intersects(rectangle)) {
                return true;
            }
        }
        return false;
    }

    public List<PaletteColor> getUsedPaletteColors() {
        List<PaletteColor> usedPaletteColors = new ArrayList<PaletteColor>();
        if (image != null) {
            Set<Integer> usedIntColors = new HashSet<Integer>();
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    usedIntColors.add(getPixel(x, y));
                }
            }
            int order = 0;
            for (Integer intColor : usedIntColors) {
                Color objColor = new Color(intColor);
                PaletteColor paletteColor = new PaletteColor(objColor, order++);
                usedPaletteColors.add(paletteColor);
            }
        }
        Collections.sort(usedPaletteColors);
        return usedPaletteColors;
    }
    
    @Override
    public String toString() {
        return "SpriteSheet{" + "image=" + image + ", sprites=" + sprites + '}';
    }

    public boolean checkSpriteNameAlreadyExists(Object newSpriteName) {
        for (Sprite sprite : sprites) {
            if (sprite.getName().equals(newSpriteName)) {
                return true;
            }
        }
        return false;
    }

    public void replaceImage(BufferedImage imageProv) {
        this.width = imageProv.getWidth();
        this.height = imageProv.getHeight();
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        g.drawImage(imageProv, 0, 0, null);
        imageData = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    }

}
