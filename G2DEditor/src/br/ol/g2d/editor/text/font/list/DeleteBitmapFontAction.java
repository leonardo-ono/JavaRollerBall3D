package br.ol.g2d.editor.text.font.list;

import br.ol.g2d.G2DContext;
import br.ol.g2d.TextBitmapFont;
import br.ol.ui.model.ActionPanel;
import java.awt.event.KeyEvent;

    
/**
 * DeleteBitmapFontAction class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class DeleteBitmapFontAction extends ActionPanel<BitmapFontsListPanel>  {

    protected G2DContext context;

    public DeleteBitmapFontAction(BitmapFontsListPanel panel, G2DContext context) {
        super(panel);
        this.context = context;
    }
    
    @Override
    public boolean isActivated() {
        return context != null && context.getSelectedFont()!= null;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DELETE) {
            TextBitmapFont selectedFont = context.getSelectedFont();
            context.getFonts().remove(selectedFont.getName());
            context.getFonts().setSelectedFont(null);
            context.update();
        }
    }
    
}
