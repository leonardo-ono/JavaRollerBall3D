package br.ol.g2d.editor.palette;

import br.ol.g2d.G2DContext;
import br.ol.g2d.Palette;
import br.ol.g2d.PaletteColor;
import br.ol.ui.model.ActionPanel;
import java.awt.event.KeyEvent;

    
/**
 * DeletePaletteColorAction class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class DeletePaletteColorAction extends ActionPanel<PalettePanel>  {

    protected G2DContext context;

    public DeletePaletteColorAction(PalettePanel panel, G2DContext context) {
        super(panel);
        this.context = context;
    }
    
    @Override
    public boolean isActivated() {
        return context != null && context.getSelectedPaletteColor() != null;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DELETE) {
            PaletteColor selectedColor = context.getSelectedPaletteColor();
            if (selectedColor != null) {
                Palette palette = context.getPalette();
                palette.removeColor(selectedColor);
                palette.setSelectedColor(null);
                context.update();
            }
        }
    }
    
}
