package br.ol.g2d.editor.spritesheet;

import br.ol.g2d.G2DContext;
import br.ol.ui.model.ActionPanel;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

    
/**
 * ChangeSpriteNameAction class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class ChangeSpriteNameAction extends ActionPanel<SpriteSheetPanel>  {

    protected G2DContext context;

    public ChangeSpriteNameAction(SpriteSheetPanel panel, G2DContext context) {
        super(panel);
        this.context = context;
    }
    
    @Override
    public boolean isActivated() {
        return context != null && context.getSelectedSprite()!= null;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_S && !e.isActionKey() && !e.isAltDown() && !e.isAltGraphDown() && !e.isControlDown() && !e.isMetaDown() && !e.isShiftDown()) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    Object newSpriteName = JOptionPane.showInputDialog(JOptionPane.getFrameForComponent(panel)
                            , "Enter new sprite name:", "Change sprite name"
                            , JOptionPane.INFORMATION_MESSAGE, null, null, context.getSelectedSprite().getName());
                    
                    if (newSpriteName != null) {
                        if (newSpriteName.toString().trim().isEmpty()) {
                            panel.showError("Invalid sprite name !", null);
                        }
                        else if (context.getSpriteSheet().checkSpriteNameAlreadyExists(newSpriteName.toString().trim())) {
                            panel.showError("Sprite \'" + newSpriteName + "' already exists !", null);
                        }
                        else {
                            context.getSelectedSprite().setName(newSpriteName.toString().trim());
                            context.update();
                        }
                    }
                }
            });
        }
    }
    
}
