package br.ol.g2d.editor.animation.timeline;

import br.ol.g2d.Animation;
import br.ol.g2d.G2DContext;
import br.ol.ui.model.ActionPanel;
import java.awt.event.KeyEvent;

    
/**
 * ChangeLastFrameIndexAction class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class ChangeLastFrameIndexAction extends ActionPanel<AnimationTimeLinePanel>  {

    protected G2DContext context;

    public ChangeLastFrameIndexAction(AnimationTimeLinePanel panel, G2DContext context) {
        super(panel);
        this.context = context;
    }
    
    @Override
    public boolean isActivated() {
        return context != null && context.getSelectedAnimation()!= null;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Animation selectedAnimation = context.getSelectedAnimation();
        if (e.getKeyCode() == KeyEvent.VK_LEFT && e.isShiftDown()) {
            selectedAnimation.lastFrameIndex--;
            if (selectedAnimation.lastFrameIndex < 0) {
                selectedAnimation.lastFrameIndex = 0;
            }
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT && e.isShiftDown()) {
            selectedAnimation.lastFrameIndex++;
        }
        panel.update();
    }

    
}
