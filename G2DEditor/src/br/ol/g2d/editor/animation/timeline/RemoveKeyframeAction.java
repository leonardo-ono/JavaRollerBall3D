package br.ol.g2d.editor.animation.timeline;

import br.ol.g2d.AnimationObjectInterface;
import br.ol.g2d.AnimationSpriteObject;
import br.ol.g2d.G2DContext;
import br.ol.ui.model.ActionPanel;
import java.awt.Point;
import java.awt.event.KeyEvent;

    
/**
 * RemoveSpriteAtCurrentFrameAction class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class RemoveKeyframeAction extends ActionPanel<AnimationTimeLinePanel>  {

    protected G2DContext context;
    protected Point newPosition = new Point();
    
    public RemoveKeyframeAction(AnimationTimeLinePanel panel, G2DContext context) {
        super(panel);
        this.context = context;
    }

    @Override
    public boolean isActivated() {
        return context != null && context.getSelectedAnimationObject() != null;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() != KeyEvent.VK_R) {
            return;
        }
        
        AnimationObjectInterface selectedAnimationObject = context.getSelectedAnimationObject();
        if (selectedAnimationObject instanceof AnimationSpriteObject) {
            AnimationSpriteObject animationSpriteObject = (AnimationSpriteObject) selectedAnimationObject;        
            animationSpriteObject.removeKeyframe(context.getSelectedAnimation().currentFrameIndex);
            context.update();
        }
    }
    
}
