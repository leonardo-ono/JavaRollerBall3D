package br.ol.g2d.editor.spritesheet;

import br.ol.g2d.G2DContext;
import br.ol.g2d.Sprite;
import br.ol.g2d.SpriteSheet;
import br.ol.ui.model.Tool;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * ExtractSpriteByFloodFillMethodTool class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class ExtractSpriteByFloodFillMethodTool extends Tool<SpriteSheetPanel> {

    private G2DContext context;
    private BufferedImage image;
    private BufferedImage image2;

    public ExtractSpriteByFloodFillMethodTool(SpriteSheetPanel panel, G2DContext context) {
        super(panel);
        this.context = context;
        this.name = "extract sprite by flood fill method";
    }

    @Override
    public int getActivationKeycode() {
        return KeyEvent.VK_E;
    }

    @Override
    public boolean isActivated() {
        return context.getData() != null && context.getData().getSpriteSheet() != null;
    }
    
    @Override
    public void mousePressed(MouseEvent e, int mx, int my) {
        if ((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) == 0) {
            return;
        }
        Sprite selectedSprite = context.getData().getSpriteSheet().getSprite(mx, my);        
        if (selectedSprite != null) {
            panel.toolUseFinished();
            return;
        }
        
        SpriteSheet spriteSheet = context.getData().getSpriteSheet();

        image = spriteSheet.getImage();
        if (image2 == null || image2.getWidth() != image.getWidth() || image2.getHeight() != image.getHeight()) {
            image2 = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        }
        Graphics i2g = image2.getGraphics();
        i2g.setColor(Color.BLACK);
        i2g.fillRect(0, 0, image2.getWidth(), image2.getHeight());

        String availableSpriteName = spriteSheet.getNextAvailableSpriteName();
        Sprite sprite = new Sprite(availableSpriteName, spriteSheet);
        sprite.getRectangle().setBounds(mx, my, 0, 0);
        
        try {
            extractSprite(sprite.getRectangle(), mx, my);
        }
        catch (Throwable ex) {
            panel.showError("It was not possible to extract sprite at this position !", ex);
            return;
        }
        
        sprite.getRectangle().width -= sprite.getRectangle().x - 1;
        sprite.getRectangle().height -= sprite.getRectangle().y - 1;

        if (spriteSheet.intersectsExistingSprites(sprite.getRectangle())) {
            panel.showError("Extracted sprite intersects with already existing sprites !", null);
        }

        if (sprite.getRectangle().getWidth() > 0 && sprite.getRectangle().getHeight() > 0) {
            spriteSheet.addSprite(sprite);
        }
        else {
            panel.showError("It was not possible to extract sprite at this position !", null);
        }
    }

    public void extractSprite(Rectangle r, int x, int y) {
        Color backgroundColor = context.getData().getSpriteSheet().getBackgroundColor();
        if (getImageRGB(x, y) == backgroundColor.getRGB() || getImage2RGB(x, y) == 0xFFFFFFFF) {
            return;
        }
        image2.setRGB(x, y, 0xFFFFFFFF);
        r.x = x < r.x ? x : r.x;
        r.width = x > r.width ? x : r.width;
        r.y = y < r.y ? y : r.y;
        r.height = y > r.height ? y : r.height;
        extractSprite(r, x, y - 1);
        extractSprite(r, x, y + 1);

        extractSprite(r, x - 1, y);
        extractSprite(r, x + 1, y);
    }

    public int getImageRGB(int x, int y)  {
        if (x < 0 || y < 0 || x > image.getWidth() - 1 || y > image.getHeight() - 1) {
            return 0xFF000000;
        }
        return image.getRGB(x, y);
    }

    public int getImage2RGB(int x, int y)  {
        if (x < 0 || y < 0 || x > image2.getWidth() - 1 || y > image2.getHeight() - 1) {
            return 0xFFFFFFFF;
        }
        return image2.getRGB(x, y);
    }
    
}
