package br.ol.rolllerball.model;

import br.ol.rollerball.physics2d.PhysicsTime;
import br.ol.rollerball.physics2d.RigidBody;
import br.ol.rollerball.physics2d.StaticLine;
import br.ol.rollerball.physics2d.PhysicsVec2;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;

/**
 * SlotWheelBody class.
 * 
 * Slot machine.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class SlotWheelBody extends RigidBody {
    
    protected RollerBallGameModel model;
    
    private double remainingAngle; // in degrees
    private boolean rotating;
    private boolean enabled = true;
    private boolean awardObtained = true;
    protected long blinkStartTime;
    private final Polygon polygon = new Polygon();
    private final double staticX;
    private final double staticY;
    
    public SlotWheelBody(String name, RollerBallGameModel model, double x, double y) {
        super(name, model.getWorld(), new StaticLine(0, 0, 1, 1), true);
        this.model = model;
        staticX = x;
        staticY = y;
        getVelocity().set(0, 0);
        getPosition().set(staticX, staticY);
        setMass(99999999);
        setSensor(true);
        createDebugPolygon();
    }

    private void createDebugPolygon() {
        double scale = 40;
        double a = Math.toRadians(60);
        double s = Math.sin(a);
        double c = Math.cos(a);
        polygon.addPoint((int) (-1 * scale), (int) (0 * scale));
        polygon.addPoint((int) (c * scale), (int) (s * scale));
        polygon.addPoint((int) (c * scale), (int) (-s * scale));
    }
    
    public RollerBallGameModel getModel() {
        return model;
    }

    public boolean isRotating() {
        return rotating;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAwardObtained() {
        return awardObtained;
    }

    public void setAwardObtained(boolean awardObtained) {
        this.awardObtained = awardObtained;
    }

    public boolean isLightOn() {
        if (isBlinking()){
            return (int) (System.nanoTime() * 0.0000000075) % 2 == 0;
        }
        return false;
    }
    
    /*
     * 0 = cherry
     * 1 = bell
     * 2 = eggplant
     */
    public int getResult() {
        return ((int) angle / 120) % 3;
    }
    
    public void setResult(int r) {
        angle = r * 120;
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
    public void update() {
        getPosition().set(staticX, staticY);
        if (!enabled) {
            if (isBlinking()) {
                return;
            }
            enabled = true;
        }
        if (rotating) {
            double da = PhysicsTime.getDelta() * 0.00000075;
            if (remainingAngle > da) {
                remainingAngle -= da;
            }
            else {
                da = remainingAngle;
                remainingAngle = 0;
            }
            angle += da;
            if (remainingAngle == 0) {
                angle = ((int) Math.ceil(angle) % 360) / 120 * 120;
                rotating = false;
            }
        }
    }

    public void rotate() {
        if (!enabled) {
            return;
        }
        remainingAngle = 3 * 360 + 120 * (int) (10 * Math.random());
        remainingAngle -= (int) angle % 120;
        rotating = true;
        awardObtained = false;
    }

    @Override
    public void drawDebug(Graphics2D g) {
        super.drawDebug(g);
        AffineTransform at = g.getTransform();
        g.translate(getPosition().getX(), getPosition().getY());
        g.rotate(Math.toRadians(angle));
        g.drawLine(0, 0, 20, 0);
        if (isLightOn()) {
            g.fill(polygon);
        }
        else {
            g.draw(polygon);
        }
        g.setTransform(at);
    }

    public void blink() {
        blinkStartTime = System.currentTimeMillis();
        enabled = false;
    }

    public boolean isBlinking() {
        return System.currentTimeMillis() - blinkStartTime < 3000;
    }
    
}
