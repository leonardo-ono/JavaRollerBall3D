package br.ol.rollerball.entity;

import br.ol.ge.Entity;
import br.ol.ge.Mouse;
import br.ol.rollerball.scene.RBScene;
import br.ol.rolllerball.model.BallBody;
import br.ol.rollerball.physics2d.Contact;
import br.ol.rollerball.physics2d.ContactListener;
import br.ol.rollerball.physics2d.RigidBody;
import java.awt.Graphics2D;

/**
 *
 * @author leonardo
 */
public class Ball extends Entity<RBScene> implements ContactListener {
    
    private BallBody model;
    
    public Ball(RBScene scene, String name) {
        super(scene);
        model = (BallBody) getScene().getModel().getBodyByName(name);
        model.addListener(this);
    }

    @Override
    public void update() {
        if (Mouse.pressed && !Mouse.pressedConsumed) {
            Mouse.pressedConsumed = true;
            model.getPosition().set(Mouse.x, Mouse.y);
            model.getVelocity().set(0, 0);
        }
    }

    @Override
    public void draw(Graphics2D g) {
    }

    // --- ContactListener implementation ---

    @Override
    public void onCollisionEnter(RigidBody rb, Contact contact) {
        // System.out.println("colidiu com " + rb + " !");
    }

    @Override
    public void onCollision(RigidBody rb, Contact contact) {
    }

    @Override
    public void onCollisionOut(RigidBody rb, Contact contact) {
    }
    
}
