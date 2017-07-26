package br.ol.g2d.editor.animation.timeline;

import br.ol.g2d.Animation;
import br.ol.g2d.AnimationObjectInterface;
import br.ol.g2d.G2DContext;
import br.ol.ui.model.ActionPanel;
import java.awt.event.MouseEvent;

    
/**
 * SelectAnimationFrameAction class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class SelectAnimationFrameAction extends ActionPanel<AnimationTimeLinePanel>  {

    protected G2DContext context;

    public SelectAnimationFrameAction(AnimationTimeLinePanel panel, G2DContext context) {
        super(panel);
        this.context = context;
    }
    
    @Override
    public boolean isActivated() {
        return context != null && context.getSelectedAnimation()!= null;
    }

    @Override
    public void mousePressed(MouseEvent e, int px, int py) {
        if ((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) == 0) {
            return;
        }
        if (context.getSelectedAnimation().playing) {
            context.getSelectedAnimation().pause();
        }
        Animation selectedAnimation = context.getSelectedAnimation();
        int frame = px / panel.getFrameRectangleSize();
        selectedAnimation.currentFrameIndex = frame;
        
        int selectedAnimationObjectIndex = py / panel.getFrameRectangleSize();
        if (selectedAnimationObjectIndex < selectedAnimation.objects.size()) {
            AnimationObjectInterface selectedAnimationObject = selectedAnimation.objects.get(selectedAnimationObjectIndex);
            selectedAnimation.setSelectedObject(selectedAnimationObject);
        }
        
        context.update();
    }
    
    @Override
    public void mouseDragged(MouseEvent e, int px, int py) {
        if ((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) == 0) {
            return;
        }
        Animation selectedAnimation = context.getSelectedAnimation();
        int frame = px / panel.getFrameRectangleSize();
        if (frame < 0) {
            frame = 0;
        }
        else if (frame > selectedAnimation.lastFrameIndex) {
            frame = selectedAnimation.lastFrameIndex;
        }
        selectedAnimation.currentFrameIndex = frame;
        context.update();
    }
    
}
