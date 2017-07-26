package br.ol.g2d.editor.animation.preview;

import br.ol.g2d.AnimationObjectInterface;
import br.ol.g2d.G2DContext;
import br.ol.ui.model.ActionPanel;
import java.awt.event.MouseEvent;

/**
 * SelectAnimationObjectAction class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class SelectAnimationObjectAction extends ActionPanel<AnimationPreviewPanel>  {

    protected G2DContext context;

    public SelectAnimationObjectAction(AnimationPreviewPanel panel, G2DContext context) {
        super(panel);
        this.context = context;
    }
    
    @Override
    public boolean isActivated() {
        return context != null && context.getSelectedAnimation() != null;
    }

    @Override
    public void mousePressed(MouseEvent e, int px, int py) {
        if ((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) == 0) {
            return;
        }
        int ax = px - panel.getMarginWidth();
        int ay = py - panel.getMarginHeight();
        AnimationObjectInterface selectedAnimationObject = context.getSelectedAnimation().getAnimationObjectAtPosition(ax, ay);
        if (selectedAnimationObject != null) {
            context.getSelectedAnimation().setSelectedObject(selectedAnimationObject);
            context.update();
        }
    }
    
}
