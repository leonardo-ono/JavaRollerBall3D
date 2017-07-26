package br.ol.rolllerball.model;

import br.ol.rollerball.physics2d.Contact;
import br.ol.rollerball.physics2d.PhysicsTime;
import br.ol.rollerball.physics2d.RigidBody;
import br.ol.rollerball.physics2d.StaticLine;
import br.ol.rollerball.physics2d.PhysicsVec2;

/**
 * PaddleLeftBody class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class PaddleLeftBody extends RigidBody {
    
    private final double pivotX;
    private final double pivotY;
    private double flipperAngle;
    private final StaticLine staticLine;
    private final PhysicsVec2 p1 = new PhysicsVec2();
    private final PhysicsVec2 p2 = new PhysicsVec2();
    private boolean activated;
    private boolean rotating;
    private final int side; // -1=top 1=bottom

    public PaddleLeftBody(String name, RollerBallGameModel model, double pivotX, double pivotY, int side) {
        super(name, model.getWorld(), new StaticLine(0, 0, 0, 0), true);
        getVelocity().set(0, 0);
        getPosition().set(0, 0);
        setMass(99999999);
        this.flipperAngle = 0.45;
        this.pivotX = pivotX;
        this.pivotY = pivotY;
        this.staticLine = (StaticLine) getShape();
        this.side = side;
    }

    public double getPivotX() {
        return pivotX;
    }

    public double getPivotY() {
        return pivotY;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public boolean isRotating() {
        return rotating;
    }

    public double getFlipperAngle() {
        return flipperAngle;
    }
    
    @Override
    public void update() {
        if (activated) {
            rotating = true;
            flipperAngle -= PhysicsTime.getDelta() * 0.00000001;
            if (flipperAngle <= -0.5) {
                flipperAngle = -0.5;
                rotating = false;
            }
        }
        else {
            rotating = true;
            flipperAngle += PhysicsTime.getDelta() * 0.00000001;
            if (flipperAngle >= 0.45) {
                flipperAngle = 0.45;
                rotating = false;
            }
        }
        if (side < 0) { // top wall flipper
            p1.set(0, -15);
            p2.set(100, 0);
        }
        else { // bottom wall flipper
            p1.set(100, 5);
            p2.set(0, 15);
        }
        p1.rotate(flipperAngle);
        p2.rotate(flipperAngle);
        p1.translate(pivotX, pivotY);
        p2.translate(pivotX, pivotY);
        staticLine.getP1().set(p1);
        staticLine.getP2().set(p2);
        getPosition().set(0, 0);
        
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
        velocityTmp.set(0, 0);
        if (isRotating()) {
            PhysicsVec2 vec2Tmp = new PhysicsVec2();
            vec2Tmp.set(contact.getPosition());
            vec2Tmp.sub(staticLine.getP1());
            double dist = vec2Tmp.getLength() * 7;
            vec2Tmp.set(contact.getNormal());
            vec2Tmp.scale(dist);
            velocityTmp.sub(vec2Tmp);
        }
        return velocityTmp;
    }
    
}
