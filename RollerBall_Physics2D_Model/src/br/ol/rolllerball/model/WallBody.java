package br.ol.rolllerball.model;

import br.ol.rollerball.physics2d.RigidBody;
import br.ol.rollerball.physics2d.StaticLine;
import br.ol.rollerball.physics2d.PhysicsVec2;

/**
 * WallBody class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class WallBody extends RigidBody {
    
    protected RollerBallGameModel model;
    
    public WallBody(String name, RollerBallGameModel model, double x1, double y1, double x2, double y2) {
        super(name, model.getWorld(), new StaticLine(x1, y1, x2, y2), false);
        this.model = model;
        getVelocity().set(0, 0);
        getPosition().set(0, 0);
        setMass(99999999);
    }

    @Override
    public void applyImpulse(PhysicsVec2 impulse) {
        // ensure not move
    }

    @Override
    public void addForce(PhysicsVec2 force) {
        // ensure not move
    }
    
}
