package br.ol.g2d.editor.animation.preview;

import br.ol.g2d.AnimationObjectInterface;
import br.ol.g2d.G2DContext;
import br.ol.ui.model.ActionPanel;
import java.awt.event.KeyEvent;

    
/**
 * DeleteAnimationObjectAction class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class DeleteAnimationObjectAction extends ActionPanel<AnimationPreviewPanel>  {

    protected G2DContext context;
    
    public DeleteAnimationObjectAction(AnimationPreviewPanel panel, G2DContext context) {
        super(panel);
        this.context = context;
    }

    @Override
    public boolean isActivated() {
        return context != null && context.getSelectedAnimationObject()!= null;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() != KeyEvent.VK_DELETE) {
            return;
        }
        AnimationObjectInterface selectedAnimationObject = context.getSelectedAnimationObject();
        context.getSelectedAnimation().removeObject(selectedAnimationObject);
        context.update();
    }
    
}
