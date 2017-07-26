package br.ol.g2d.editor.animation.list;

import br.ol.g2d.Animation;
import br.ol.g2d.G2DContext;
import br.ol.ui.model.ActionPanel;
import java.awt.event.KeyEvent;

    
/**
 * DeleteAnimationAction class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class DeleteAnimationAction extends ActionPanel<AnimationsListPanel>  {

    protected G2DContext context;

    public DeleteAnimationAction(AnimationsListPanel panel, G2DContext context) {
        super(panel);
        this.context = context;
    }
    
    @Override
    public boolean isActivated() {
        return context != null && context.getSelectedAnimation()!= null;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DELETE) {
            Animation selectedAnimation = context.getSelectedAnimation();
            context.getAnimations().delete(selectedAnimation.name);
            context.getAnimations().setSelectedAnimation(null);
            context.update();
        }
    }
    
}
