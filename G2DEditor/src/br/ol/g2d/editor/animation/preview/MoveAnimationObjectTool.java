package br.ol.g2d.editor.animation.preview;

import br.ol.g2d.AnimationSpriteObject;
import br.ol.g2d.AnimationObjectFrame;
import br.ol.g2d.AnimationObjectInterface;
import br.ol.g2d.G2DContext;
import br.ol.ui.model.Tool;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

    
/**
 * MoveAnimationObjectTool class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class MoveAnimationObjectTool extends Tool<AnimationPreviewPanel>  {

    protected G2DContext context;
    protected Point startPosition = new Point();
    protected boolean moving = false;
    protected AnimationObjectFrame movingFrame;
    protected boolean newFrameCreated;
    
    public MoveAnimationObjectTool(AnimationPreviewPanel panel, G2DContext context) {
        super(panel);
        this.context = context;
        this.name = "move animation object";        
    }

    @Override
    public int getActivationKeycode() {
        return KeyEvent.VK_M;
    }
        
    @Override
    public boolean isActivated() {
        return context != null && context.getSelectedAnimation() != null;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // move
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            moveSelectedAnimationObject(-1, 0);
        } 
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            moveSelectedAnimationObject(1, 0);
        }
        else if (e.getKeyCode() == KeyEvent.VK_UP) {
            moveSelectedAnimationObject(0, -1);
        } 
        else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            moveSelectedAnimationObject(0, 1);
        }
        else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            moveSelectedAnimationObject(0, 0);
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e, int px, int py) {
        if ((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) == 0) {
            return;
        }
        if (context.getSelectedAnimation().playing) {
            context.getSelectedAnimation().pause();
        }
        int ax = px - panel.getMarginWidth();
        int ay = py - panel.getMarginHeight();
        AnimationObjectInterface selectedAnimationObject = context.getSelectedAnimation().getAnimationObjectAtPosition(ax, ay);
        moving = false;
        persistent = false;
        if (selectedAnimationObject != null && selectedAnimationObject instanceof AnimationSpriteObject) {
            AnimationSpriteObject animationSpriteObject = (AnimationSpriteObject) selectedAnimationObject;
            context.getSelectedAnimation().setSelectedObject(selectedAnimationObject);

            int currentFrameIndex = context.getSelectedAnimation().currentFrameIndex;
            if (animationSpriteObject.isKeyFrame(currentFrameIndex)) {
                newFrameCreated = false;
                movingFrame = animationSpriteObject.getKeyFrame(currentFrameIndex);
            }
            else {
                newFrameCreated = true;
                AnimationObjectFrame interFrame = animationSpriteObject.getFrame(currentFrameIndex);
                movingFrame = new AnimationObjectFrame();
                movingFrame.set(interFrame);
                animationSpriteObject.addKeyframe(currentFrameIndex, movingFrame);
            }
            
            startPosition.setLocation(ax, ay);
            this.name = "move animation object / position: (" + ax + ", " + ay + ")";
            moving = true;
            persistent = true;
            context.update();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e, int px, int py) {
        if (!moving) {
            return;
        }
        int ax = px - panel.getMarginWidth();
        int ay = py - panel.getMarginHeight();
        movingFrame.x = ax;
        movingFrame.y = ay;
        this.name = "move animation object / position: (" + ax + ", " + ay + ")";
        panel.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e, int px, int py) {
        movingFrame = null;
        persistent = false;
        panel.repaint();
    }

    @Override
    public void cancel() {
        AnimationObjectInterface selectedAnimationObject = context.getSelectedAnimationObject();
        if (selectedAnimationObject != null && selectedAnimationObject instanceof AnimationSpriteObject) {
            AnimationSpriteObject animationSpriteObject = (AnimationSpriteObject) selectedAnimationObject;
            if (newFrameCreated) {
                int currentFrameIndex = context.getSelectedAnimation().currentFrameIndex;
                animationSpriteObject.removeKeyframe(currentFrameIndex);
            }
            movingFrame = null;
            persistent = false;
            context.update();
        }
    }

    private void moveSelectedAnimationObject(int dx, int dy) {
        if (context.getSelectedAnimation().playing) {
            context.getSelectedAnimation().pause();
        }
        AnimationObjectInterface selectedAnimationObject = context.getSelectedAnimationObject();
        if (selectedAnimationObject != null && selectedAnimationObject instanceof AnimationSpriteObject) {
            AnimationSpriteObject animationSpriteObject = (AnimationSpriteObject) selectedAnimationObject;
            
            context.getSelectedAnimation().setSelectedObject(selectedAnimationObject);

            int currentFrameIndex = context.getSelectedAnimation().currentFrameIndex;
            if (animationSpriteObject.isKeyFrame(currentFrameIndex)) {
                movingFrame = animationSpriteObject.getKeyFrame(currentFrameIndex);
            }
            else {
                AnimationObjectFrame interFrame = animationSpriteObject.getFrame(currentFrameIndex);
                movingFrame = new AnimationObjectFrame();
                movingFrame.set(interFrame);
                animationSpriteObject.addKeyframe(currentFrameIndex, movingFrame);
            }
            int ax = (int) (movingFrame.x + dx);
            int ay = (int) (movingFrame.y + dy);
            movingFrame.x = ax;
            movingFrame.y = ay;
            this.name = "move animation object / position: (" + ax + ", " + ay + ")";
            context.update();
        }
    }
    
}
