package br.ol.g2d.editor.animation.preview;

import br.ol.g2d.AnimationSpriteObject;
import br.ol.g2d.AnimationObjectFrame;
import br.ol.g2d.G2DContext;
import br.ol.ui.model.ActionPanel;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

    
/**
 * NewAnimationObjectAction class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class NewAnimationObjectAction extends ActionPanel<AnimationPreviewPanel>  {

    protected G2DContext context;
    protected Point newPosition = new Point();
    
    public NewAnimationObjectAction(AnimationPreviewPanel panel, G2DContext context) {
        super(panel);
        this.context = context;
    }

    @Override
    public boolean isActivated() {
        return context != null && context.getSelectedAnimation() != null && context.getSelectedSprite() != null;
    }

    @Override
    public void mouseMoved(MouseEvent e, int px, int py) {
        int ax = px - panel.getMarginWidth();
        int ay = py - panel.getMarginHeight();
        newPosition.setLocation(ax, ay);
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() != KeyEvent.VK_N) {
            return;
        }
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Object newAnimationObjectName = JOptionPane.showInputDialog(JOptionPane.getFrameForComponent(panel), "Enter new animation object name:", "New animation object", JOptionPane.INFORMATION_MESSAGE, null, null, "new_animation_object");
                if (newAnimationObjectName != null) {
                    if (newAnimationObjectName.toString().trim().isEmpty()) {
                        panel.showError("Invalid animation object name !", null);
                    }
                    // todo check name already in use
                    else {
                        AnimationSpriteObject newAnimationObject = new AnimationSpriteObject(newAnimationObjectName.toString().trim(), context.getSelectedAnimation());
                        newAnimationObject.addDrawableKeyframe(0, context.getSelectedSprite());
                        
                        AnimationObjectFrame firstFrame = new AnimationObjectFrame();
                        firstFrame.tween = true;
                        firstFrame.x = newPosition.x;
                        firstFrame.y = newPosition.y;
                        firstFrame.sx = 1;
                        firstFrame.sy = 1;
                        firstFrame.px = 0;
                        firstFrame.py = 0;
                        firstFrame.angle = 0;
                        newAnimationObject.addKeyframe(0, firstFrame);

                        context.getSelectedAnimation().addObject(newAnimationObject);
                        context.update();
                    }
                }
            }
        });
    }
    
}
