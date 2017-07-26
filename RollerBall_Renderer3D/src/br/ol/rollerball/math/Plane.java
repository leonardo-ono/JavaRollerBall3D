package br.ol.rollerball.math;

/**
 *
 * @author leonardo
 */
public class Plane {
    
    public Vec4 normal = new Vec4(0, 1, 0, 0);
    public Vec4 p = new Vec4(0, 0, 0, 1);

    public Plane() {
    }

    public Plane(double x1, double y1, double z1, double x2, double y2, double z2) {
        normal.set(x1, y1, z1, 0);
        p.set(x2, y2, z2, 1);
    }

    public void set(double x1, double y1, double z1, double x2, double y2, double z2) {
        normal.set(x1, y1, z1, 0);
        p.set(x2, y2, z2, 1);
    }
    
    private final Vec4 provV1 = new Vec4();
    private final Vec4 provV2 = new Vec4();
    
    public Vec4 getIntersectionPoint(Line line) {
        provV1.set(line.p2);
        provV1.sub(line.p1);
        double d1 = provV1.dot(normal);
        
        if (d1 == 0) {
            return null;
        }
        
        provV2.set(p);
        provV2.sub(line.p1);
        
        double d2 = provV2.dot(normal);
        double scaleFactor = d2 / d1;

        provV1.multiply(scaleFactor);
        provV1.add(line.p1);
        
        return provV1;
    }
    
}
