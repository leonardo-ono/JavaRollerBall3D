package br.ol.g2d.editor.animation.timeline;

import br.ol.g2d.AnimationSpriteObject;
import br.ol.g2d.AnimationObjectFrame;
import br.ol.g2d.AnimationObjectInterface;
import br.ol.g2d.G2DContext;
import br.ol.ui.model.ActionPanel;
import java.awt.event.KeyEvent;

    
/**
 * InsertKeyframeAction class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class InsertKeyframeAction extends ActionPanel<AnimationTimeLinePanel>  {

    protected G2DContext context;
    protected AnimationObjectFrame movingFrame;
    
    public InsertKeyframeAction(AnimationTimeLinePanel panel, G2DContext context) {
        super(panel);
        this.context = context;
    }

    @Override
    public boolean isActivated() {
        return context != null && context.getSelectedAnimationObject() != null;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // move
        if (e.getKeyCode() == KeyEvent.VK_K) {
            insertAnimationObjectKeyframe();
        }
    }

    private void insertAnimationObjectKeyframe() {
        if (context.getSelectedAnimation().playing) {
            context.getSelectedAnimation().pause();
        }
        AnimationObjectInterface selectedAnimationObject = context.getSelectedAnimationObject();
        if (selectedAnimationObject != null && selectedAnimationObject instanceof AnimationSpriteObject) {
            AnimationSpriteObject spriteObject = (AnimationSpriteObject) selectedAnimationObject;
            
            context.getSelectedAnimation().setSelectedObject(selectedAnimationObject);

            int currentFrameIndex = context.getSelectedAnimation().currentFrameIndex;
            if (spriteObject.isKeyFrame(currentFrameIndex)) {
                movingFrame = spriteObject.getKeyFrame(currentFrameIndex);
            }
            else {
                AnimationObjectFrame interFrame = spriteObject.getFrame(currentFrameIndex);
                movingFrame = new AnimationObjectFrame();
                movingFrame.set(interFrame);
                spriteObject.addKeyframe(currentFrameIndex, movingFrame);
            }
            context.update();
        }
    }
    
}
