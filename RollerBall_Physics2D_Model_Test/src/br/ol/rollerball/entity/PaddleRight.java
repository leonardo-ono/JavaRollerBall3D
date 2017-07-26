package br.ol.rollerball.entity;

import br.ol.ge.Entity;
import br.ol.ge.Keyboard;
import br.ol.rollerball.scene.RBScene;
import br.ol.rolllerball.model.PaddleLeftBody;
import br.ol.rolllerball.model.PaddleRightBody;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

/**
 *
 * @author leonardo
 */
public class PaddleRight extends Entity<RBScene> {
    
    private final PaddleRightBody model;
    
    public PaddleRight(RBScene scene, String name) {
        super(scene);
        model = (PaddleRightBody) getScene().getModel().getBodyByName(name);
    }

    @Override
    public void update() {
        if (Keyboard.isKeyPressed(KeyEvent.VK_X)) {
            getScene().getModel().getTable().rightSensorGroup();
        }
        getScene().getModel().getTable().activateRightPaddles(Keyboard.isKeyDown(KeyEvent.VK_X));
    }

    @Override
    public void draw(Graphics2D g) {
    }

}
