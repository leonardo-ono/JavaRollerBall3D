package br.ol.g2d.editor.animation.list;

import br.ol.g2d.Animation;
import br.ol.g2d.G2DContext;
import br.ol.ui.model.ActionPanel;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

    
/**
 * RenameAnimationAction class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class RenameAnimationAction extends ActionPanel<AnimationsListPanel>  {

    protected G2DContext context;

    public RenameAnimationAction(AnimationsListPanel panel, G2DContext context) {
        super(panel);
        this.context = context;
    }
    
    @Override
    public boolean isActivated() {
        return context != null && context.getSelectedAnimation()!= null;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_R) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    Animation selectedAnimation = context.getSelectedAnimation();
                    Object newAnimationName = JOptionPane.showInputDialog(JOptionPane.getFrameForComponent(panel)
                            , "Enter new animation name:", "Rename animation"
                            , JOptionPane.INFORMATION_MESSAGE, null, null, selectedAnimation.name);
                    
                    if (newAnimationName != null && !selectedAnimation.name.trim().equals(newAnimationName.toString().trim())) {
                        if (newAnimationName.toString().trim().isEmpty()) {
                            panel.showError("Invalid animation name !", null);
                        }
                        else if (context.getAnimations().checkAnimatinoNameAlreadyExists(newAnimationName.toString().trim())) {
                            panel.showError("Animation \'" + newAnimationName + "' already exists !", null);
                        }
                        else {
                            context.getAnimations().delete(selectedAnimation.name);
                            selectedAnimation.name = newAnimationName.toString().trim();
                            context.getAnimations().getMap().put(selectedAnimation.name, selectedAnimation);
                            context.update();
                        }
                    }
                }
            });
        }
    }
    
}
