package br.ol.rolllerball.model;

import br.ol.rollerball.physics2d.PhysicsTime;
import java.awt.Graphics2D;

/**
 * Shooter class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class Shooter {
    
    protected RollerBallGameModel model;
    protected double intensity;
    protected double releasedIntensity;
    protected boolean holding;
    protected int releasedCount;
    protected boolean blocked;
    
    public Shooter(RollerBallGameModel model) {
        this.model = model;
    }

    public double getIntensity() {
        return intensity;
    }

    public void update() {
        if (!blocked) {
            return;
        }
        intensity -= PhysicsTime.getDelta() * 0.00000001;
        if (intensity <= 0) {
            intensity =0;
        }
        else {
            return;
        }
        model.getTable().shoot(releasedIntensity);
        blocked = false;
    }

    public void hold(boolean holding) {
        if (blocked) {
            return;
        }
        if (!holding) {
            releasedCount++;
            if (releasedCount < 5) {
                return;
            }
            releasedCount = 0;
        }
        this.holding = holding;
        if (holding) {
            intensity += PhysicsTime.getDelta() * 0.000000001;
            if (intensity > 1) {
                intensity = 1;
            }
        }
        else {
            releasedIntensity = intensity;
            blocked = true;
        }
    }
    
    public void drawDebug(Graphics2D g) {
        int height = (int) (170 * (1 - intensity));
        g.fillRect(629, 2375 + (170 - height), 40, height);
    }
    
}
