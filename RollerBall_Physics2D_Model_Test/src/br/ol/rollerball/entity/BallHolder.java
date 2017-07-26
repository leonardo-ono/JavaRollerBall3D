package br.ol.rollerball.entity;

import br.ol.ge.Entity;
import br.ol.ge.Keyboard;
import br.ol.rollerball.scene.RBScene;
import br.ol.rolllerball.model.BallHolderBody;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

/**
 *
 * @author leonardo
 */
public class BallHolder extends Entity<RBScene> {
    
    private final BallHolderBody model;
    
    public BallHolder(RBScene scene, int x, int y) {
        super(scene);
        model = getScene().getModel().addBallHolder("ball_holder", x, y);
    }

    @Override
    public void update() {
        if (Keyboard.isKeyDown(KeyEvent.VK_U)) {
            model.setDirection(-1);
            model.release();
        }
        else if (Keyboard.isKeyDown(KeyEvent.VK_R)) {
            model.setDirection(1);
            model.release();
        }        
    }

    @Override
    public void draw(Graphics2D g) {
    }

}
