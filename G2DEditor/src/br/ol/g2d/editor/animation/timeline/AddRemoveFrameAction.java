package br.ol.g2d.editor.animation.timeline;

import br.ol.g2d.AnimationObjectFrame;
import br.ol.g2d.AnimationObjectInterface;
import br.ol.g2d.AnimationSpriteObject;
import br.ol.g2d.G2DContext;
import br.ol.g2d.Sprite;
import br.ol.ui.model.ActionPanel;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

    
/**
 * AddRemoveFrameAction class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class AddRemoveFrameAction extends ActionPanel<AnimationTimeLinePanel>  {

    protected G2DContext context;
    
    public AddRemoveFrameAction(AnimationTimeLinePanel panel, G2DContext context) {
        super(panel);
        this.context = context;
    }

    @Override
    public boolean isActivated() {
        return context != null && context.getSelectedAnimationObject() != null;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_I) {
            addFrame();
        }
        else if (e.getKeyCode() == KeyEvent.VK_O) {
            removeFrame();
        }
    }

    private void addFrame() {
        if (context.getSelectedAnimation().playing) {
            return;
        }
        
        AnimationObjectInterface selectedAnimationObject = context.getSelectedAnimationObject();
        
        if (!(selectedAnimationObject instanceof AnimationSpriteObject)) {
            return;
        }
        AnimationSpriteObject animationSpriteObject = (AnimationSpriteObject) selectedAnimationObject;
            
        int currentFrameIndex = context.getSelectedAnimation().currentFrameIndex;
        
        Map<Integer, AnimationObjectFrame> framesCopy = new HashMap<Integer, AnimationObjectFrame>(animationSpriteObject.keyframes);
        animationSpriteObject.keyframes.clear();
        for (Integer key : framesCopy.keySet()) {
            if (key >= currentFrameIndex) {
                animationSpriteObject.addKeyframe(key + 1, framesCopy.get(key));
            }
            else {
                animationSpriteObject.addKeyframe(key, framesCopy.get(key));
            }
        }

        Map<Integer, Sprite> drawablesCopy = new HashMap<Integer, Sprite>(animationSpriteObject.drawableKeyframes);
        animationSpriteObject.drawableKeyframes.clear();
        for (Integer key : drawablesCopy.keySet()) {
            if (key >= currentFrameIndex) {
                animationSpriteObject.addDrawableKeyframe(key + 1, drawablesCopy.get(key));
            }
            else {
                animationSpriteObject.addDrawableKeyframe(key, drawablesCopy.get(key));
            }
        }
                
        context.getSelectedAnimation().lastFrameIndex++;
        context.update();
    }
    
    private void removeFrame() {
        if (context.getSelectedAnimation().playing) {
            return;
        }
        
        AnimationObjectInterface selectedAnimationObject = context.getSelectedAnimationObject();
        
        if (!(selectedAnimationObject instanceof AnimationSpriteObject)) {
            return;
        }
        AnimationSpriteObject animationSpriteObject = (AnimationSpriteObject) selectedAnimationObject;
        
        int currentFrameIndex = context.getSelectedAnimation().currentFrameIndex;
        
        Map<Integer, AnimationObjectFrame> framesCopy = new HashMap<Integer, AnimationObjectFrame>(animationSpriteObject.keyframes);
        animationSpriteObject.keyframes.clear();
        for (Integer key : framesCopy.keySet()) {
            if (key == currentFrameIndex) {
                // remove frame
            }
            else if (key > currentFrameIndex) {
                animationSpriteObject.addKeyframe(key - 1, framesCopy.get(key));
            }
            else {
                animationSpriteObject.addKeyframe(key, framesCopy.get(key));
            }
        }

        Map<Integer, Sprite> drawablesCopy = new HashMap<Integer, Sprite>(animationSpriteObject.drawableKeyframes);
        animationSpriteObject.drawableKeyframes.clear();
        for (Integer key : drawablesCopy.keySet()) {
            if (key == currentFrameIndex) {
                // remove frame
            }
            else if (key > currentFrameIndex) {
                animationSpriteObject.addDrawableKeyframe(key - 1, drawablesCopy.get(key));
            }
            else {
                animationSpriteObject.addDrawableKeyframe(key, drawablesCopy.get(key));
            }
        }
                
//        context.getSelectedAnimation().lastFrameIndex--;
//        if (context.getSelectedAnimation().lastFrameIndex < 0) {
//            context.getSelectedAnimation().lastFrameIndex = 0;
//        }
        context.update();
    }
    
}
