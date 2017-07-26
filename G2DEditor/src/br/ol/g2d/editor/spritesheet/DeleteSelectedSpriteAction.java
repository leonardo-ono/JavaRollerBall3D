package br.ol.g2d.editor.spritesheet;

import br.ol.g2d.G2DContext;
import br.ol.g2d.Sprite;
import br.ol.ui.model.ActionPanel;
import java.awt.event.KeyEvent;

    
/**
 * DeleteSelectedSpriteAction class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class DeleteSelectedSpriteAction extends ActionPanel<SpriteSheetPanel>  {

    protected G2DContext context;

    public DeleteSelectedSpriteAction(SpriteSheetPanel panel, G2DContext context) {
        super(panel);
        this.context = context;
    }
    
    @Override
    public boolean isActivated() {
        return context != null && context.getSelectedSprite() != null;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DELETE && !e.isActionKey() && !e.isAltDown() && !e.isAltGraphDown() && !e.isControlDown() && !e.isMetaDown() && !e.isShiftDown()) {
            Sprite selectedSprite = context.getSelectedSprite();
            context.getData().getSpriteSheet().getSprites().remove(selectedSprite);
            context.getData().getSpriteSheet().setSelectedSprite(null);
            context.update();
        }
    }

}
