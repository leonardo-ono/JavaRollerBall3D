package br.ol.rolllerball.model;

import br.ol.rollerball.physics2d.Contact;
import br.ol.rollerball.physics2d.RigidBody;
import br.ol.rollerball.physics2d.StaticLine;
import br.ol.rollerball.physics2d.PhysicsVec2;

/**
 * ButtonBody class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class ButtonBody extends RigidBody {
    
    protected RollerBallGameModel model;
    protected double pivotX;
    protected double pivotY;
    
    public ButtonBody(String name, RollerBallGameModel model, double x1, double y1, double x2, double y2) {
        super(name, model.getWorld(), new StaticLine(x1, y1, x2, y2), false);
        this.model = model;
        this.pivotX = (x1 + x2) / 2;
        this.pivotY = (y1 + y2) / 2;
        getVelocity().set(0, 0);
        getPosition().set(0, 0);
        setMass(99999999);
    }

    public double getPivotX() {
        return pivotX;
    }

    public double getPivotY() {
        return pivotY;
    }

    @Override
    public void applyImpulse(PhysicsVec2 impulse) {
        // ensure not move
    }

    @Override
    public void addForce(PhysicsVec2 force) {
        // ensure not move
    }

    @Override
    public void onCollisionEnter(RigidBody rb, Contact contact) {
        model.addScore(ScoreTable.BUTTON);
        super.onCollisionEnter(rb, contact);
    }
    
}
