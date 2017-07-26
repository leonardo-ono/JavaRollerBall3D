package br.ol.rollerball.physics2d;

import java.awt.Graphics2D;

/**
 * StaticLine class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class StaticLine extends Shape {

    private final PhysicsVec2 p1 = new PhysicsVec2();
    private final PhysicsVec2 p2 = new PhysicsVec2();

    public StaticLine(double px1, double py1, double px2, double py2) {
        p1.set(px1, py1);
        p2.set(px2, py2);
    }

    public PhysicsVec2 getP1() {
        return p1;
    }

    public PhysicsVec2 getP2() {
        return p2;
    }
    
    @Override
    public void drawDebug(Graphics2D g) {
        g.setColor(debugColor);
        g.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
    }
    
}
