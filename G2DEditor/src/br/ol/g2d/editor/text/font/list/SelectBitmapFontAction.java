package br.ol.g2d.editor.text.font.list;

import br.ol.g2d.G2DContext;
import br.ol.g2d.TextBitmapFont;
import br.ol.ui.model.ActionPanel;
import java.awt.event.MouseEvent;

    
/**
 * SelectBitmapFontAction class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class SelectBitmapFontAction extends ActionPanel<BitmapFontsListPanel>  {

    protected G2DContext context;

    public SelectBitmapFontAction(BitmapFontsListPanel panel, G2DContext context) {
        super(panel);
        this.context = context;
    }
    
    @Override
    public boolean isActivated() {
        return context != null && context.getFonts() != null;
    }

    @Override
    public void mousePressed(MouseEvent e, int px, int py) {
        if ((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) == 0) {
            return;
        }
        TextBitmapFont selectedFont = panel.getFontAtPosition(px, py);
        if (selectedFont != null) {
            context.getFonts().setSelectedFont(selectedFont);
            context.update();
        }
    }
    
}
