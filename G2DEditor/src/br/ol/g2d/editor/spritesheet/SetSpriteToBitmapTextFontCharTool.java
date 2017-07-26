package br.ol.g2d.editor.spritesheet;

import br.ol.g2d.G2DContext;
import br.ol.g2d.Sprite;
import br.ol.ui.model.Tool;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * SetSpriteToBitmapTextFontCharTool class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class SetSpriteToBitmapTextFontCharTool extends Tool<SpriteSheetPanel> {

    private G2DContext context;
    private boolean started;
    
    public SetSpriteToBitmapTextFontCharTool(SpriteSheetPanel panel, G2DContext context) {
        super(panel);
        this.context = context;
        name = "set sprite to bitmap text character";
    }

    @Override
    public int getActivationKeycode() {
        return KeyEvent.VK_T;
    }

    @Override
    public boolean isActivated() {
        return context.getData() != null && context.getSpriteSheet() != null && context.getSelectedFont() != null;
    }
    
    @Override
    public void start() {
        persistent = true;
        started = true;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (!isPrintableChar(e.getKeyChar()) || !e.isAltDown()) {
            return;
        }
        Sprite selectedSprite = context.getSelectedSprite();
        if (selectedSprite == null) {
            return;
        }
        context.getSelectedFont().addChar(e.getKeyChar(), selectedSprite);
        context.update();
    }

    public boolean isPrintableChar( char c ) {
        Character.UnicodeBlock block = Character.UnicodeBlock.of( c );
        return (!Character.isISOControl(c)) &&
                c != KeyEvent.CHAR_UNDEFINED &&
                block != null &&
                block != Character.UnicodeBlock.SPECIALS;
    }

    @Override
    public void mouseMoved(MouseEvent e, int px, int py) {
        Sprite selectedSprite = context.getSelectedSprite();
        for (Sprite sprite : context.getSpriteSheet().getSprites()) {
            if (sprite != selectedSprite && sprite.getRectangle().contains(px, py)) {
                context.getSpriteSheet().setSelectedSprite(sprite);
                panel.repaint();
                return;
            }
        }
    }

    @Override
    public void cancel() {
        context.getSpriteSheet().setSelectedSprite(null);
        started = false;
    }
    
}
