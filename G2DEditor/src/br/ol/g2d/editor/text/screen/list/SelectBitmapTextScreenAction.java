package br.ol.g2d.editor.text.screen.list;

import br.ol.g2d.editor.text.font.list.*;
import br.ol.g2d.G2DContext;
import br.ol.g2d.TextBitmapFont;
import br.ol.g2d.TextBitmapScreen;
import br.ol.ui.model.ActionPanel;
import java.awt.event.MouseEvent;

    
/**
 * SelectBitmapTextScreenAction class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class SelectBitmapTextScreenAction extends ActionPanel<BitmapFontScreensListPanel>  {

    protected G2DContext context;

    public SelectBitmapTextScreenAction(BitmapFontScreensListPanel panel, G2DContext context) {
        super(panel);
        this.context = context;
    }
    
    @Override
    public boolean isActivated() {
        return context != null && context.getTextScreens() != null;
    }

    @Override
    public void mousePressed(MouseEvent e, int px, int py) {
        if ((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) == 0) {
            return;
        }
        TextBitmapScreen selectedScreen = panel.getTextScreenAtPosition(px, py);
        if (selectedScreen != null) {
            context.getTextScreens().setSelectedScreen(selectedScreen);
            context.update();
        }
    }
    
}
