package br.ol.g2d.editor.animation.timeline;

import br.ol.g2d.AnimationObjectInterface;
import br.ol.g2d.G2DContext;
import br.ol.ui.model.ActionPanel;
import java.awt.event.KeyEvent;

    
/**
 * BringAnimationObjectToFrontOrBackAction class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class BringAnimationObjectToFrontOrBackAction extends ActionPanel<AnimationTimeLinePanel>  {

    protected G2DContext context;
    
    public BringAnimationObjectToFrontOrBackAction(AnimationTimeLinePanel panel, G2DContext context) {
        super(panel);
        this.context = context;
    }

    @Override
    public boolean isActivated() {
        return context != null && context.getSelectedAnimationObject() != null;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        AnimationObjectInterface selectedAnimationObject = context.getSelectedAnimationObject();
        int currentIndex = context.getSelectedAnimation().objects.indexOf(selectedAnimationObject);
        if (e.getKeyCode() == KeyEvent.VK_HOME && currentIndex < context.getSelectedAnimation().objects.size() - 1) {
            context.getSelectedAnimation().removeObject(selectedAnimationObject);
            context.getSelectedAnimation().objects.add(currentIndex + 1, selectedAnimationObject);
            context.update();
        }
        else if (e.getKeyCode() == KeyEvent.VK_END && currentIndex > 0) {
            context.getSelectedAnimation().removeObject(selectedAnimationObject);
            context.getSelectedAnimation().objects.add(currentIndex - 1, selectedAnimationObject);
            context.update();
        }
    }
    
}
