package br.ol.g2d.editor.sprite;

import br.ol.g2d.G2DContext;
import br.ol.g2d.PaletteColor;
import br.ol.ui.model.ActionPanel;
import java.awt.event.MouseEvent;

    
/**
 * PutPixelAction class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class PutPixelAction extends ActionPanel<SpritePanel>  {

    protected G2DContext context;

    public PutPixelAction(SpritePanel panel, G2DContext context) {
        super(panel);
        this.context = context;
    }
    
    @Override
    public boolean isActivated() {
        return context != null && context.getSelectedSprite() != null;
    }

    @Override
    public void mousePressed(MouseEvent e, int px, int py) {
        if ((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) == 0) {
            return;
        }
        putPixel(px, py);
    }

    @Override
    public void mouseDragged(MouseEvent e, int px, int py) {
        if ((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) == 0) {
            return;
        }
        putPixel(px, py);
    }

    public void putPixel(int px, int py) {
        int color = 0;
        PaletteColor selectedPaletteColor = context.getSelectedPaletteColor();
        if (selectedPaletteColor != null) {
            color = selectedPaletteColor.getColor().getRGB();
        }
        context.getSelectedSprite().setPixel(px, py, color);
    }
    
}
