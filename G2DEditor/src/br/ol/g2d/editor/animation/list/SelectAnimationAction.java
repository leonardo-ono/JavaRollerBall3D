package br.ol.g2d.editor.animation.list;

import br.ol.g2d.Animation;
import br.ol.g2d.G2DContext;
import br.ol.ui.model.ActionPanel;
import java.awt.event.MouseEvent;

    
/**
 * SelectAnimationAction class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class SelectAnimationAction extends ActionPanel<AnimationsListPanel>  {

    protected G2DContext context;

    public SelectAnimationAction(AnimationsListPanel panel, G2DContext context) {
        super(panel);
        this.context = context;
    }
    
    @Override
    public boolean isActivated() {
        return context != null && context.getAnimations()!= null;
    }

    @Override
    public void mousePressed(MouseEvent e, int px, int py) {
        if ((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) == 0) {
            return;
        }
        Animation selectedAnimation = panel.getAnimationAtPosition(px, py);
        if (selectedAnimation != null) {
            context.getAnimations().setSelectedAnimation(selectedAnimation);
            context.update();
        }
    }
    
}
