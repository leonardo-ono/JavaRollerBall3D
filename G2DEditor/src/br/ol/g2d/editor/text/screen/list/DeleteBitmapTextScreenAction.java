package br.ol.g2d.editor.text.screen.list;

import br.ol.g2d.G2DContext;
import br.ol.g2d.TextBitmapFont;
import br.ol.g2d.TextBitmapScreen;
import br.ol.ui.model.ActionPanel;
import java.awt.event.KeyEvent;

    
/**
 * DeleteBitmapTextScreenAction class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class DeleteBitmapTextScreenAction extends ActionPanel<BitmapFontScreensListPanel>  {

    protected G2DContext context;

    public DeleteBitmapTextScreenAction(BitmapFontScreensListPanel panel, G2DContext context) {
        super(panel);
        this.context = context;
    }
    
    @Override
    public boolean isActivated() {
        return context != null && context.getSelectedTextScreen() != null;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DELETE) {
            TextBitmapScreen selectedScreen = context.getSelectedTextScreen();
            context.getTextScreens().remove(selectedScreen.getName());
            context.getTextScreens().setSelectedScreen(null);
            context.update();
        }
    }
    
}
