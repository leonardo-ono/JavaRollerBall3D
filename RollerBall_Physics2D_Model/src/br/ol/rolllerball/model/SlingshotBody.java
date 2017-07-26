package br.ol.rolllerball.model;

import br.ol.rollerball.physics2d.Contact;
import br.ol.rollerball.physics2d.RigidBody;
import br.ol.rollerball.physics2d.StaticLine;
import br.ol.rollerball.physics2d.PhysicsVec2;

/**
 * SlingshotBody class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class SlingshotBody extends RigidBody {
    
    protected final RollerBallGameModel model;
    protected double pivotX;
    protected double pivotY;
    
    public SlingshotBody(String name, RollerBallGameModel model, double x1, double y1, double x2, double y2) {
        super(name, model.getWorld(), new StaticLine(x1, y1, x2, y2), false);
        getPosition().set(0, 0);
        setMass(99999999);
        this.model = model;
        this.pivotX = x1;
        this.pivotY = y1;
        
        // Shrink slightly to avoid collisions in the corners
        StaticLine sl = (StaticLine) getShape();
        PhysicsVec2 vTmp = new PhysicsVec2();
        vTmp.set(sl.getP2());
        vTmp.sub(sl.getP1());
        vTmp.normalize();
        vTmp.scale(3);
        sl.getP1().add(vTmp);
        sl.getP2().sub(vTmp);
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
    
    private final PhysicsVec2 velocityTmp = new PhysicsVec2();
    
    @Override
    public PhysicsVec2 getVelocity(Contact contact) {
        velocityTmp.set(contact.getNormal());
        velocityTmp.scale(-300);
        return velocityTmp;
    }

    @Override
    public void onCollisionEnter(RigidBody rb, Contact contact) {
        model.addScore(ScoreTable.SLINGSHOT);
        super.onCollisionEnter(rb, contact);
    }
    
    
}
