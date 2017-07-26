package br.ol.g2d.editor.animation.list;

import br.ol.g2d.Animation;
import br.ol.g2d.G2DContext;
import br.ol.ui.model.ActionPanel;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

    
/**
 * CreateNewAnimationAction class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class CreateNewAnimationAction extends ActionPanel<AnimationsListPanel>  {

    protected G2DContext context;

    public CreateNewAnimationAction(AnimationsListPanel panel, G2DContext context) {
        super(panel);
        this.context = context;
    }
    
    @Override
    public boolean isActivated() {
        return context != null && context.getAnimations()!= null;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_N) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    Object newAnimationName = JOptionPane.showInputDialog(JOptionPane.getFrameForComponent(panel)
                            , "Enter new animation name:", "Create new animation"
                            , JOptionPane.INFORMATION_MESSAGE, null, null, "new_animation");
                    
                    if (newAnimationName != null) {
                        if (newAnimationName.toString().trim().isEmpty()) {
                            panel.showError("Invalid animation name !", null);
                        }
                        else if (context.getAnimations().checkAnimatinoNameAlreadyExists(newAnimationName.toString().trim())) {
                            panel.showError("Animation \'" + newAnimationName + "' already exists !", null);
                        }
                        else {
                            context.getAnimations().create(newAnimationName.toString().trim());
                            Animation newAnimation = context.getAnimations().get(newAnimationName.toString().trim());
                            context.getAnimations().setSelectedAnimation(newAnimation);
                            context.update();
                        }
                    }
                }
            });
        }
    }
    
}
