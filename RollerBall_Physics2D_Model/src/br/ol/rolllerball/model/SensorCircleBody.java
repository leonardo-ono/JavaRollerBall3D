package br.ol.rolllerball.model;

import br.ol.rollerball.physics2d.Circle;
import br.ol.rollerball.physics2d.Contact;
import br.ol.rollerball.physics2d.RigidBody;
import br.ol.rollerball.physics2d.PhysicsVec2;

/**
 * SensorCircleBody class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class SensorCircleBody extends RigidBody {
    
    protected RollerBallGameModel model;
    
    public SensorCircleBody(String name, RollerBallGameModel model, double x, double y, double radius) {
        super(name, model.getWorld(), new Circle(radius), false);
        this.model = model;
        getVelocity().set(0, 0);
        getPosition().set(x, y);
        setMass(99999999);
        setSensor(true);
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
        model.addScore(ScoreTable.SENSOR_CIRCLE);
        super.onCollisionEnter(rb, contact);
    }
    
}
