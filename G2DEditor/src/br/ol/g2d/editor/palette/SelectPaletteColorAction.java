package br.ol.g2d.editor.palette;

import br.ol.g2d.G2DContext;
import br.ol.g2d.Palette;
import br.ol.g2d.PaletteColor;
import br.ol.g2d.editor.sprite.*;
import br.ol.ui.model.ActionPanel;
import java.awt.event.MouseEvent;

    
/**
 * SelectPaletteColorAction class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class SelectPaletteColorAction extends ActionPanel<PalettePanel>  {

    protected G2DContext context;

    public SelectPaletteColorAction(PalettePanel panel, G2DContext context) {
        super(panel);
        this.context = context;
    }
    
    @Override
    public boolean isActivated() {
        return context != null && context.getPalette()!= null;
    }

    @Override
    public void mousePressed(MouseEvent e, int px, int py) {
        if ((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) == 0) {
            return;
        }
        Palette palette = context.getPalette();
        PaletteColor selectedColor = palette.getColorByPosition(px, py);
        if (selectedColor != null) {
            palette.setSelectedColor(selectedColor);
        }
        context.update();
    }
    
}
