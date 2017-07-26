package br.ol.rollerball.entity;

import br.ol.ge.Entity;
import br.ol.rollerball.scene.RBScene;
import br.ol.rolllerball.model.BumperBody;
import br.ol.rollerball.physics2d.Contact;
import br.ol.rollerball.physics2d.ContactListener;
import br.ol.rollerball.physics2d.RigidBody;
import java.awt.Graphics2D;

/**
 *
 * @author leonardo
 */
public class Bumper extends Entity<RBScene> {
    
    private final BumperBody model;
    
    public Bumper(RBScene scene, int x, int y) {
        super(scene);
        model = getScene().getModel().addBumper("bumpe", x, y);
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Graphics2D g) {
    }

}
