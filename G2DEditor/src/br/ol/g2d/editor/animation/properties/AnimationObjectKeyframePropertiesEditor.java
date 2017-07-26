package br.ol.g2d.editor.animation.properties;

import br.ol.g2d.Animation;
import br.ol.g2d.AnimationSpriteObject;
import br.ol.g2d.AnimationObjectFrame;
import br.ol.g2d.AnimationObjectInterface;
import br.ol.g2d.G2DContext;
import br.ol.ui.view.PropertiesEditor;
import java.awt.Color;

/**
 * AnimationObjectKeyframePropertiesEditor class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class AnimationObjectKeyframePropertiesEditor extends PropertiesEditor {
    
    private G2DContext context;
    private final AnimationObjectFrame frame = new AnimationObjectFrame();
    private final Color originalBackgroundColor;
            
    public AnimationObjectKeyframePropertiesEditor() {
        setObject(null);
        originalBackgroundColor = getBackground();
    }

    public G2DContext getContext() {
        return context;
    }

    public void setContext(G2DContext context) {
        this.context = context;
    }

    public void init() {
    }

    public void update() {
        if (context != null && context.getSelectedAnimationObject() != null) {
            Animation selectedAnimation = context.getSelectedAnimation();
            AnimationObjectInterface selectedAnimationObject = context.getSelectedAnimationObject();
            if (!(selectedAnimationObject instanceof AnimationSpriteObject)) {
                return;
            }
            AnimationSpriteObject animationSpriteObject = (AnimationSpriteObject) selectedAnimationObject;        
            
            if (animationSpriteObject.isKeyFrame(selectedAnimation.currentFrameIndex)) {
                AnimationObjectFrame keyframe = animationSpriteObject.getKeyFrame(selectedAnimation.currentFrameIndex);
                if (model == null || model.object == null) {
                    setObject(keyframe);
                }
                else if (model.object != keyframe) {
                    model.object = keyframe;
                }
                setEnabled(true);
                setBackground(originalBackgroundColor);
            }
            else {
                frame.set(animationSpriteObject.getFrame(selectedAnimation.currentFrameIndex));
                if (model == null || model.object == null) {
                    setObject(frame);
                }
                else if (model.object != frame) {
                    model.object = frame;
                }
                setEnabled(false);
                setBackground(Color.GRAY);
            }
        }
        else {
            if (model != null && model.object != null) {
                setObject(null);
            }
            setEnabled(false);
            setBackground(Color.GRAY);
        }
        repaint();
    }    

    @Override
    public void propertyValueChanged() {
        context.update();
    }
    
}
