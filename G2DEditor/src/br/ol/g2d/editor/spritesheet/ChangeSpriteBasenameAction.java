package br.ol.g2d.editor.spritesheet;

import br.ol.g2d.G2DContext;
import br.ol.ui.model.ActionPanel;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

    
/**
 * ChangeSpriteBasenameAction class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class ChangeSpriteBasenameAction extends ActionPanel<SpriteSheetPanel>  {

    protected G2DContext context;

    public ChangeSpriteBasenameAction(SpriteSheetPanel panel, G2DContext context) {
        super(panel);
        this.context = context;
    }
    
    @Override
    public boolean isActivated() {
        return context != null && context.getSpriteSheet() != null;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_G && !e.isActionKey() && !e.isAltDown() && !e.isAltGraphDown() && !e.isControlDown() && !e.isMetaDown() && !e.isShiftDown()) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    Object newSpriteBaseName = JOptionPane.showInputDialog(JOptionPane.getFrameForComponent(panel), "Enter new sprite base name:", "Change sprite base name", JOptionPane.INFORMATION_MESSAGE, null, null, context.getSpriteSheet().getSpriteBaseName());
                    if (newSpriteBaseName != null) {
                        if (newSpriteBaseName.toString().trim().isEmpty()) {
                            panel.showError("Invalid sprite base name !", null);
                        }
                        else {
                            context.getSpriteSheet().setSpriteBaseName(newSpriteBaseName.toString().trim());
                            context.update();
                        }
                    }
                }
            });
        }
    }
    
}
