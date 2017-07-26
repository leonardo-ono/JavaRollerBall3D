package br.ol.rolllerball.model;

import br.ol.rollerball.physics2d.Contact;
import br.ol.rollerball.physics2d.RigidBody;
import br.ol.rollerball.physics2d.StaticLine;
import br.ol.rollerball.physics2d.PhysicsVec2;
import java.awt.Graphics2D;

/**
 * SensorBody class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class SensorBody extends RigidBody {
    
    protected RollerBallGameModel model;
    protected StaticLine staticLine;
    protected boolean activated;
    protected long blinkStartTime;
    protected int bonusIndex;
    protected int points;
    
    public SensorBody(String name, RollerBallGameModel model, double x1, double y1, double x2, double y2) {
        super(name, model.getWorld(), new StaticLine(x1, y1, x2, y2), false);
        this.model = model;
        getVelocity().set(0, 0);
        getPosition().set(0, 0);
        setMass(99999999);
        setSensor(true);
        staticLine = (StaticLine) getShape();
    }

    public int getBonusIndex() {
        return bonusIndex;
    }

    public void setBonusIndex(int bonusIndex) {
        this.bonusIndex = bonusIndex;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int bonusPoints) {
        this.points = bonusPoints;
    }
    
    public boolean isLightOn() {
        if (isBlinking()){
            return (int) (System.nanoTime() * 0.0000000075) % 2 == 0;
        }
        return isActivated();
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
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
        super.update();
    }
    
    @Override
    public void onCollisionEnter(RigidBody rb, Contact contact) {
        model.addScore(ScoreTable.SENSOR);
        if (!isBlinking()) {
            activated = true;
        }
        super.onCollisionEnter(rb, contact);
    }

    public void blink() {
        blinkStartTime = System.currentTimeMillis();
        activated = false;
    }
    
    public boolean isBlinking() {
        return System.currentTimeMillis() - blinkStartTime < 3000;
    }

    @Override
    public void drawDebug(Graphics2D g) {
        super.drawDebug(g);
        if (isLightOn()) {
            g.fillRect((int) (staticLine.getP1().getX() - 10), (int) (staticLine.getP2().getY() - 10), 20, 20);
        }
        else {
            g.drawRect((int) (staticLine.getP1().getX() - 10), (int) (staticLine.getP2().getY() - 10), 20, 20);
        }
    }
    
}
