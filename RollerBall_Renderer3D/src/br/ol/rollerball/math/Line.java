package br.ol.rollerball.math;

/**
 *
 * @author leonardo
 */
public class Line {
    
    public Vec4 p1 = new Vec4(0, 1, 0, 1);
    public Vec4 p2 = new Vec4(0, 0, 0, 1);

    public Line() {
    }

    public Line(double x1, double y1, double z1, double x2, double y2, double z2) {
        p1.set(x1, y1, z1, 1);
        p2.set(x2, y2, z2, 1);
    }

    public void set(double x1, double y1, double z1, double x2, double y2, double z2) {
        p1.set(x1, y1, z1, 1);
        p2.set(x2, y2, z2, 1);
    }
    
}
