package br.ol.g2d.editor.animation.timeline;

import br.ol.g2d.Animation;
import br.ol.g2d.G2DContext;
import br.ol.ui.model.ActionPanel;
import java.awt.event.KeyEvent;

    
/**
 * PlayPauseStopAnimationAction class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class PlayPauseStopAnimationAction extends ActionPanel<AnimationTimeLinePanel>  {

    protected G2DContext context;

    public PlayPauseStopAnimationAction(AnimationTimeLinePanel panel, G2DContext context) {
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
        if (e.getKeyCode() == KeyEvent.VK_P) {
            if (!selectedAnimation.playing) {
                panel.playSelectedAnimation();
            }
            else {
                panel.pauseSelectedAnimation();
            }
            context.update();
        }
        else if (e.getKeyCode() == KeyEvent.VK_S) {
            if (selectedAnimation.playing) {
                panel.stopSelectedAnimation();
                context.update();
            }
        }
    }
    
}
