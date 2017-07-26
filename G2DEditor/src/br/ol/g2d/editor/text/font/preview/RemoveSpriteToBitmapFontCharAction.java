package br.ol.g2d.editor.text.font.preview;

import br.ol.g2d.editor.text.font.list.*;
import br.ol.g2d.G2DContext;
import br.ol.g2d.TextBitmapFont;
import br.ol.ui.model.ActionPanel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

    
/**
 * RemoveSpriteToBitmapFontCharAction class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class RemoveSpriteToBitmapFontCharAction extends ActionPanel<BitmapFontPreviewPanel>  {

    protected G2DContext context;
    private final Point mousePosition = new Point();
    
    public RemoveSpriteToBitmapFontCharAction(BitmapFontPreviewPanel panel, G2DContext context) {
        super(panel);
        this.context = context;
    }

    @Override
    public boolean isActivated() {
        return context != null && context.getSelectedFont() != null && context.getSelectedSprite() != null;
    }

    @Override
    public void mouseMoved(MouseEvent e, int px, int py) {
        mousePosition.setLocation(px, py);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() != KeyEvent.VK_DELETE) {
            return;
        }
        Character c = panel.getCharAtPosition(mousePosition.x, mousePosition.y);
        if (c != null) {
            context.getSelectedFont().removeChar(c);
            context.update();
        }
    }
    
}
