package br.ol.g2d.editor.animation.preview;

import br.ol.g2d.AnimationObjectInterface;
import br.ol.g2d.AnimationSpriteObject;
import br.ol.g2d.G2DContext;
import br.ol.g2d.Sprite;
import br.ol.ui.model.ActionPanel;
import java.awt.Point;
import java.awt.event.KeyEvent;

    
/**
 * SetSpriteAtCurrentFrameAction class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class SetSpriteAtCurrentFrameAction extends ActionPanel<AnimationPreviewPanel>  {

    protected G2DContext context;
    protected Point newPosition = new Point();
    
    public SetSpriteAtCurrentFrameAction(AnimationPreviewPanel panel, G2DContext context) {
        super(panel);
        this.context = context;
    }

    @Override
    public boolean isActivated() {
        return context != null && context.getSelectedAnimationObject() != null && context.getSelectedSprite() != null;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() != KeyEvent.VK_S) {
            return;
        }
        Sprite selectedSprite = context.getSelectedSprite();
        AnimationObjectInterface selectedAnimationObject = context.getSelectedAnimationObject();
        if (selectedAnimationObject instanceof AnimationSpriteObject) {
            AnimationSpriteObject animationSpriteObject = (AnimationSpriteObject) selectedAnimationObject;        
            animationSpriteObject.addDrawableKeyframe(context.getSelectedAnimation().currentFrameIndex, selectedSprite);
            context.update();
        }
    }
    
}
