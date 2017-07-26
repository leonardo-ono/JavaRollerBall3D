package br.ol.g2d.editor.sprite;

import br.ol.g2d.G2DContext;
import br.ol.g2d.PaletteColor;
import br.ol.g2d.Sprite;
import br.ol.g2d.SpriteSheet;
import br.ol.ui.model.ActionPanel;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

    
/**
 * FloodFillAction class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class FloodFillAction extends ActionPanel<SpritePanel>  {

    protected G2DContext context;
    protected Point fillPosition = new Point();
    
    public FloodFillAction(SpritePanel panel, G2DContext context) {
        super(panel);
        this.context = context;
    }
    
    @Override
    public boolean isActivated() {
        return context != null && context.getSpriteSheet() != null 
                && context.getSelectedSprite() != null && context.getSelectedPaletteColor() != null;
    }

    @Override
    public void mouseMoved(MouseEvent e, int px, int py) {
        fillPosition.setLocation(px, py);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_F) {
            PaletteColor paletteColor = context.getSelectedPaletteColor();
            int origColor = context.getSelectedSprite().getPixel(fillPosition.x, fillPosition.y);
            floodFill(fillPosition.x, fillPosition.y, paletteColor.getColor().getRGB(), origColor);
            context.update();
        }
    }

    private void floodFill(int x, int y, int color, int origColor) {
        Sprite selectedSprite = context.getSelectedSprite();
        int dstColor = selectedSprite.getPixel(x, y);
        if (dstColor == color || dstColor != origColor || selectedSprite.isOutOfRectangle(x, y)) {
            return;
        }
        SpriteSheet spriteSheet = context.getSpriteSheet();
        spriteSheet.setPixel(x + selectedSprite.getRectangle().x, y + selectedSprite.getRectangle().y, color);
        floodFill(x - 1, y, color, origColor);
        floodFill(x + 1, y, color, origColor);
        floodFill(x, y - 1, color, origColor);
        floodFill(x, y + 1, color, origColor);
    }
    
}
