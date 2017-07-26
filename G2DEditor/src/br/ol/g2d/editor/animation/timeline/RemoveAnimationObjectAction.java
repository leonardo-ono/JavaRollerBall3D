package br.ol.g2d.editor.animation.timeline;

import br.ol.g2d.Animation;
import br.ol.g2d.AnimationObjectInterface;
import br.ol.g2d.G2DContext;
import br.ol.ui.model.ActionPanel;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

    
/**
 * RemoveAnimationObjectAction class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class RemoveAnimationObjectAction extends ActionPanel<AnimationTimeLinePanel>  {

    protected G2DContext context;
    
    public RemoveAnimationObjectAction(AnimationTimeLinePanel panel, G2DContext context) {
        super(panel);
        this.context = context;
    }

    @Override
    public boolean isActivated() {
        return context != null && context.getSelectedAnimationObject() != null;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DELETE) {
            removeAnimationToAnimation();
        }
        
    }

    private void removeAnimationToAnimation() {
        AnimationObjectInterface selectedAnimationObject = context.getSelectedAnimationObject();
        context.getSelectedAnimation().removeObject(selectedAnimationObject);
        selectedAnimationObject.setAnimation(null);
        context.update();
    }
    
}
