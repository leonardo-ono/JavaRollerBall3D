package br.ol.rolllerball.model;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/**
 * TargetGroup class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class TargetGroup {
    
    protected RollerBallGameModel model;
    protected String name;
    protected final List<TargetBody> targets = new ArrayList<TargetBody>();
    protected int bonusIndex;
    protected int points; 
    protected boolean activated;
    protected long blinkStartTime;
    protected boolean needsRestore;
    
    protected int debugX;
    protected int debugY;
    
    public TargetGroup(RollerBallGameModel model, String name, int debugX, int debugY, int bonusIndex, int points) {
        this.model = model;
        this.name = name;
        this.points = points;
        this.bonusIndex = bonusIndex;
        this.debugX = debugX;
        this.debugY = debugY;
    }

    public String getName() {
        return name;
    }

    public List<TargetBody> getTargets() {
        return targets;
    }
    
    public void addTarget(TargetBody target) {
        targets.add(target);
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
    
    public void update() {
        if (isBlinking()) {
            return;
        }
        if (needsRestore) {
            for (TargetBody target : targets) {
                target.setVisible(true);
            }
            needsRestore = false;
        }
        for (TargetBody target : targets) {
            if (target.isVisible()) {
                return;
            }
        }
        if (isActivated()) {
            if (bonusIndex < 0) {
                model.addScore(points);
            }
            else {
                model.addBonus(bonusIndex, points);
            }
            blinkStartTime = System.currentTimeMillis();
            activated = false;
        }
        needsRestore = true;
    }

    public boolean isBlinking() {
        return System.currentTimeMillis() - blinkStartTime < 3000;
    }
    
    public void drawDebug(Graphics2D g) {
        if (isLightOn()) {
            g.fillRect(debugX, debugY, 30, 30);
        }
        else {
            g.drawRect(debugX, debugY, 30, 30);
        }
    }
    
}
