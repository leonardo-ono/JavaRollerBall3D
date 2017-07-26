package br.ol.g2d.editor.spritesheet;

import br.ol.g2d.G2DContext;
import br.ol.g2d.Sprite;
import br.ol.ui.model.Tool;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

/**
 * MoveResizeSpriteTool class.
 *
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class MoveResizeSpriteTool extends Tool<SpriteSheetPanel> {
    
    protected G2DContext context;
    
    public MoveResizeSpriteTool(SpriteSheetPanel panel, G2DContext context) {
        super(panel);
        this.context = context;
        this.name = "move/resize sprite rectangle";
    }

    @Override
    public int getActivationKeycode() {
        return KeyEvent.VK_R;
    }
    
    @Override
    public boolean isActivated() {
        return context != null && context.getSelectedSprite() != null;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        // move
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (e.isAltDown()) {
                resizeSpriteRectangle(-1, 0);
            }
            else if (e.isShiftDown()) {
                resizeFromTopLeftSpriteRectangle(-1, 0);                
            }
            else {
                moveSpriteRectangle(-1, 0);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (e.isAltDown()) {
                resizeSpriteRectangle(1, 0);
            }
            else if (e.isShiftDown()) {
                resizeFromTopLeftSpriteRectangle(1, 0);                
            }
            else {
                moveSpriteRectangle(1, 0);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            if (e.isAltDown()) {
                resizeSpriteRectangle(0, -1);
            }
            else if (e.isShiftDown()) {
                resizeFromTopLeftSpriteRectangle(0, -1);
            }
            else {
                moveSpriteRectangle(0, -1);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (e.isAltDown()) {
                resizeSpriteRectangle(0, 1);
            }
            else if (e.isShiftDown()) {
                resizeFromTopLeftSpriteRectangle(0, 1);
            }
            else {
                moveSpriteRectangle(0, 1);
            }
        }
    }

    private void moveSpriteRectangle(int dx, int dy) {
        Sprite selectedSprite = context.getData().getSpriteSheet().getSelectedSprite();
        if (selectedSprite != null) {
            Rectangle spriteRectangle = selectedSprite.getRectangle();
            spriteRectangle.x += dx;
            spriteRectangle.y += dy;
            context.update();
        }
    }
    
    private void resizeSpriteRectangle(int dw, int dh) {
        Sprite selectedSprite = context.getSelectedSprite();
        Rectangle spriteRectangle = selectedSprite.getRectangle();
        spriteRectangle.width += dw;
        spriteRectangle.height += dh;
        context.update();
    }

    private void resizeFromTopLeftSpriteRectangle(int dw, int dh) {
        Sprite selectedSprite = context.getSelectedSprite();
        Rectangle spriteRectangle = selectedSprite.getRectangle();
        spriteRectangle.width -= dw;
        spriteRectangle.height -= dh;
        spriteRectangle.x += dw;
        spriteRectangle.y += dh;
        context.update();
    }
    
}
