package br.ol.g2d.editor.spritesheet;

import br.ol.g2d.G2DContext;
import br.ol.g2d.Sprite;
import br.ol.g2d.SpriteSheet;
import br.ol.ui.model.Tool;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

/**
 * NewSpriteTool class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class NewSpriteTool extends Tool<SpriteSheetPanel> {

    private G2DContext context;
    private boolean newSpriteStarted;
    private Sprite provisorySprite;
    private final Rectangle2D auxiliarRectangle = new Rectangle2D.Double();
    
    public NewSpriteTool(SpriteSheetPanel panel, G2DContext context) {
        super(panel);
        this.context = context;
    }

    @Override
    public int getActivationKeycode() {
        return KeyEvent.VK_N;
    }

    @Override
    public boolean isActivated() {
        return context.getData() != null && context.getSpriteSheet() != null;
    }
    
    @Override
    public void start() {
        if (provisorySprite == null) {
            provisorySprite = new Sprite("<new>", context.getSpriteSheet());
        }
        provisorySprite.setSheet(context.getSpriteSheet());
        name = "new sprite";
    }
    
    @Override
    public void mousePressed(MouseEvent e, int px, int py) {
        if ((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) == 0) {
            return;
        }
        Sprite selectedSprite = context.getData().getSpriteSheet().getSprite(px, py);        
        if (selectedSprite != null) {
            panel.toolUseFinished();
            return;
        }
        provisorySprite.getRectangle().setBounds(px, py, 1, 1);
        newSpriteStarted = true;
        name = "new sprite / mouse: (" + px + ", " + py + ")";
        context.getSpriteSheet().setSelectedSprite(provisorySprite);
        context.update();
    }

    @Override
    public void mouseMoved(MouseEvent e, int px, int py) {
        name = "new sprite / mouse: (" + px + ", " + py + ")";
        panel.repaint();
    }
    
    @Override
    public void mouseDragged(MouseEvent e, int px, int py) {
        if ((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) == 0) {
            return;
        }
        provisorySprite.getRectangle().width = px - provisorySprite.getRectangle().x + 1;
        provisorySprite.getRectangle().height = py - provisorySprite.getRectangle().y + 1;
        name = "new sprite / mouse: (" + px + ", " + py + ")";
        context.update();
    }
    
    @Override
    public void mouseReleased(MouseEvent e, int px, int py) {
        if (!newSpriteStarted) {
            return;
        }
        newSpriteStarted = false;
        
        SpriteSheet spriteSheet = context.getData().getSpriteSheet();
        provisorySprite.getRectangle().width = px - provisorySprite.getRectangle().x + 1;
        provisorySprite.getRectangle().height = py - provisorySprite.getRectangle().y + 1;
        
        if (provisorySprite.getRectangle().width < 0 || provisorySprite.getRectangle().height < 0) {
            return;
        }
        
        if (spriteSheet.intersectsExistingSprites(provisorySprite.getRectangle())) {
            panel.showError("New sprite cannot intersect with already existing sprites !", null);
            return;
        }

        String availableSpriteName = spriteSheet.getNextAvailableSpriteName();
        Sprite sprite = new Sprite(availableSpriteName, spriteSheet);
        sprite.getRectangle().setBounds(provisorySprite.getRectangle());
        if (sprite.getRectangle().getWidth() > 0 && sprite.getRectangle().getHeight() > 0) {
            spriteSheet.addSprite(sprite);
            spriteSheet.setSelectedSprite(sprite);
        }
        else {
            context.getSpriteSheet().setSelectedSprite(null);
        }
        name = "new sprite";
        context.update();
    }

    @Override
    public void drawScaled(Graphics2D g) {
        if (newSpriteStarted) {
            g.setXORMode(Color.GREEN);
            Rectangle r = provisorySprite.getRectangle();
            auxiliarRectangle.setRect(r.x + 0.5, r.y + 0.5, r.width - 1, r.height - 1);
            g.draw(auxiliarRectangle);
            g.setPaintMode();
        }
    }

    @Override
    public void cancel() {
        context.getSpriteSheet().setSelectedSprite(null);
        newSpriteStarted = false;
    }
    
}
