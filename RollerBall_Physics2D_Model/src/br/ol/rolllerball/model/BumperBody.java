package br.ol.rolllerball.model;

import br.ol.rollerball.physics2d.Circle;
import br.ol.rollerball.physics2d.Contact;
import br.ol.rollerball.physics2d.RigidBody;
import br.ol.rollerball.physics2d.PhysicsVec2;

/**
 * BumperBody class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class BumperBody extends RigidBody {
    
    protected final RollerBallGameModel model;
    
    public BumperBody(String name, RollerBallGameModel model, double x, double y, double radius) {
        super(name, model.getWorld(), new Circle(radius), false);
        getPosition().set(x, y);
        setMass(99999999);
        this.model = model;
    }

    @Override
    public void applyImpulse(PhysicsVec2 impulse) {
        // ensure not move
    }

    @Override
    public void addForce(PhysicsVec2 force) {
        // ensure not move
    }
    
    private final PhysicsVec2 velocityTmp = new PhysicsVec2();
    
    @Override
    public PhysicsVec2 getVelocity(Contact contact) {
        velocityTmp.set(contact.getNormal());
        velocityTmp.scale(-300);
        return velocityTmp;
    }

    @Override
    public void onCollisionEnter(RigidBody rb, Contact contact) {
        model.addScore(ScoreTable.BUMPER);
        super.onCollisionEnter(rb, contact);
    }
    
}
