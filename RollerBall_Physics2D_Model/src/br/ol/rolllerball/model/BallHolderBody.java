package br.ol.rolllerball.model;


import br.ol.rollerball.physics2d.Circle;
import br.ol.rollerball.physics2d.Contact;
import br.ol.rollerball.physics2d.RigidBody;
import br.ol.rollerball.physics2d.PhysicsVec2;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/**
 * BallHolderBody class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class BallHolderBody extends RigidBody {
    
    public static final int UP = -1;
    public static final int DOWN = 1;
    
    private BallBody ballModel;
    private boolean release;
    private int direction = 1; // 1=down -1=up
    private long holdingStartTime;
    
    private final double staticX;
    private final double staticY;
    
    private final List<BallHolderListener> listeners = new ArrayList<BallHolderListener>();
    
    public BallHolderBody(String name, RollerBallGameModel model, double x, double y) {
        super(name, model.getWorld(), new Circle(20), true);
        staticX = x;
        staticY = y;
        getVelocity().set(0, 0);
        getPosition().set(staticX, staticY);
        setMass(99999999);
        setSensor(true);
    }

    public BallBody getBallModel() {
        return ballModel;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public List<BallHolderListener> getListeners() {
        return listeners;
    }

    public void addListener(BallHolderListener listener) {
        listeners.add(listener);
    }

    @Override
    public void update() {
        getPosition().set(staticX, staticY);
        // hold ball
        if (ballModel != null) {
            ballModel.getVelocity().set(0, 0);
            ballModel.getPosition().set(getPosition());
            if (!isHolding()) {
                release();
            }
        }
    }
    
    @Override
    public void applyImpulse(PhysicsVec2 impulse) {
        // ensure not move
    }

    @Override
    public void addForce(PhysicsVec2 force) {
        // ensure not move
    }
    
    private final PhysicsVec2 vecTmp = new PhysicsVec2();
    
    @Override
    public void onCollision(RigidBody rb, Contact contact) {
        super.onCollision(rb, contact);
        if (ballModel != null || release) {
            return;
        }
        if (rb instanceof BallBody) {
            vecTmp.set(rb.getPosition());
            vecTmp.sub(getPosition());
            double dist =  vecTmp.getLength();
            if (dist < 15) {
                BallBody ball = (BallBody) rb;
                ball.getPosition().set(getPosition());
                this.ballModel = ball;
                ballModel.setHeld(true);
                fireOnHold();
                holdingStartTime = System.currentTimeMillis();
            }
        }
    }
    
    public boolean isHolding() {
        return System.currentTimeMillis() - holdingStartTime < 3000;
    }
    
    @Override
    public void onCollisionOut(RigidBody rb, Contact contact) {
        super.onCollisionOut(rb, contact);
        release = false;
    }
    
    public void release() {
        if (direction < 0 && ballModel != null) {
            vecTmp.set(0, -800 - 200 * Math.random());
            ballModel.applyImpulse(vecTmp);
        }
        else if (direction > 0 && ballModel != null) {
            vecTmp.set(0, 200 + 200 * Math.random());
            ballModel.applyImpulse(vecTmp);
        }
        ballModel.setHeld(false);
        ballModel = null;
        release = true;
        fireOnRelease();
    }

    @Override
    public void drawDebug(Graphics2D g) {
        super.drawDebug(g); 
        g.drawString(direction > 0 ? "down" : "up", (int) getPosition().getX(), (int) getPosition().getY());
    }
    
    private void fireOnHold() {
        for (BallHolderListener listener : listeners) {
            listener.onHold();
        }
    }
    
    private void fireOnRelease() {
        for (BallHolderListener listener : listeners) {
            listener.onRelease();
        }
    }
    
}
