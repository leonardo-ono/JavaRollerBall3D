package br.ol.g2d.editor.animation.timeline;

import br.ol.g2d.Animation;
import br.ol.g2d.G2DContext;
import br.ol.ui.model.ActionPanel;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

    
/**
 * AddAnimationToAnotherAnimationObjectAction class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class AddAnimationToAnotherAnimationObjectAction extends ActionPanel<AnimationTimeLinePanel>  {

    protected G2DContext context;
    
    public AddAnimationToAnotherAnimationObjectAction(AnimationTimeLinePanel panel, G2DContext context) {
        super(panel);
        this.context = context;
    }

    @Override
    public boolean isActivated() {
        return context != null && context.getSelectedAnimation() != null;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A) {
            addAnimationToAnimation();
        }
    }
    
    private void addAnimationToAnimation() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Object animationObjectName = JOptionPane.showInputDialog(JOptionPane.getFrameForComponent(panel), "Enter animation's name to add:", "Add animation to another animation", JOptionPane.INFORMATION_MESSAGE, null, null, "animation_name");
                if (animationObjectName != null) {
                    String animationObjectNameAdd = animationObjectName.toString().trim();
                    if (animationObjectNameAdd.isEmpty()) {
                        panel.showError("Invalid animation object name !", null);
                    }
                    else if (context.getAnimations().getMap().get(animationObjectNameAdd) == null) {
                        panel.showError("Animation not found !", null);
                    }
                    else if (context.getAnimations().getMap().get(animationObjectNameAdd) == context.getSelectedAnimation()) {
                        panel.showError("Cannot add same animation !", null);
                    }
                    // todo check name already in use
                    else {
                        Animation animationAdd = context.getAnimations().getMap().get(animationObjectNameAdd);
                        context.getSelectedAnimation().addObject(animationAdd);
                        animationAdd.setAnimation(context.getSelectedAnimation());
                        context.update();
                    }
                }
            }
        });
    }

}
