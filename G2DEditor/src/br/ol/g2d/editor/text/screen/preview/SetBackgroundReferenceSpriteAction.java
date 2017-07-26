package br.ol.g2d.editor.text.screen.preview;

import br.ol.g2d.G2DContext;
import br.ol.ui.model.ActionPanel;
import java.awt.event.KeyEvent;

/**
 * SetBackgroundReferenceSpriteAction class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class SetBackgroundReferenceSpriteAction extends ActionPanel<BitmapTextScreenPreviewPanel> {

    protected G2DContext context;

    public SetBackgroundReferenceSpriteAction(BitmapTextScreenPreviewPanel panel, G2DContext context) {
        super(panel);
        this.context = context;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_E && e.isAltDown()) {
            panel.setBackgroundReferenceSprite(null);
        }
        else if (e.getKeyCode() == KeyEvent.VK_R && e.isAltDown()) {
            panel.setBackgroundReferenceSprite(context.getSelectedSprite());
        }
    }

}
