package br.ol.g2d.editor.animation.timeline;

import br.ol.g2d.AnimationObjectFrame;
import br.ol.g2d.AnimationObjectInterface;
import br.ol.g2d.AnimationSpriteObject;
import br.ol.g2d.G2DContext;
import br.ol.g2d.Sprite;
import br.ol.ui.model.ActionPanel;
import java.awt.event.KeyEvent;

    
/**
 * CopyPasteOneFrameAction class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class CopyPasteOneFrameAction extends ActionPanel<AnimationTimeLinePanel>  {

    protected G2DContext context;
    protected AnimationObjectFrame copiedFrame = new AnimationObjectFrame();
    protected boolean copiedFrameAvailable;
    protected Sprite copiedSprite;
    
    public CopyPasteOneFrameAction(AnimationTimeLinePanel panel, G2DContext context) {
        super(panel);
        this.context = context;
    }

    @Override
    public boolean isActivated() {
        return context != null && context.getSelectedAnimationObject() != null && context.getSelectedAnimationObject() instanceof AnimationSpriteObject && !context.getSelectedAnimation().playing;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_C && e.isControlDown()) {
            copyFrame();
        }
        else if (e.getKeyCode() == KeyEvent.VK_V && e.isControlDown()) {
            pasteFrame();
        }
    }

    private void copyFrame() {
        AnimationObjectInterface selectedAnimationObject = context.getSelectedAnimationObject();
        AnimationSpriteObject animationSpriteObject = (AnimationSpriteObject) selectedAnimationObject;
        int currentFrameIndex = context.getSelectedAnimation().currentFrameIndex;
        
        AnimationObjectFrame frame = animationSpriteObject.getFrame(currentFrameIndex);
        if (frame != null) {
            copiedFrame.set(frame);
            copiedFrameAvailable = true;
        }
        else {
            copiedFrameAvailable = false;
        }
        copiedSprite = animationSpriteObject.getSprite(currentFrameIndex);
    }
    
    private void pasteFrame() {
        AnimationObjectInterface selectedAnimationObject = context.getSelectedAnimationObject();
        AnimationSpriteObject animationSpriteObject = (AnimationSpriteObject) selectedAnimationObject;
        int currentFrameIndex = context.getSelectedAnimation().currentFrameIndex;

        if (copiedFrameAvailable && copiedFrame != null) {
            AnimationObjectFrame newFrame = new AnimationObjectFrame();
            newFrame.set(copiedFrame);
            animationSpriteObject.addKeyframe(currentFrameIndex, newFrame);
        }
        
        if (copiedSprite != null) {
            animationSpriteObject.addDrawableKeyframe(currentFrameIndex, copiedSprite);
        }
        context.update();
    }
    
}
