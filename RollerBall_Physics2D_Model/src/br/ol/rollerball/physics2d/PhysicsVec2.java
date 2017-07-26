package br.ol.rollerball.physics2d;

/**
 * PhysicsVec2 class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class PhysicsVec2 {
    
    private double x, y;

    public PhysicsVec2() {
    }

    public PhysicsVec2(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public void set(PhysicsVec2 v) {
        this.x = v.x;
        this.y = v.y;
    }
    
    public void set(double x, double y) {
        this.x = x;
        this.y = y;
        
        if (Double.isNaN(x) || Double.isNaN(y)) {
            System.out.println("");
        }
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
        if (Double.isNaN(x) || Double.isNaN(y)) {
            System.out.println("");
        }
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
        if (Double.isNaN(x) || Double.isNaN(y)) {
            System.out.println("");
        }
    }

    public void scale(double d) {
        this.x *= d;
        this.y *= d;
    }
    
    public void add(PhysicsVec2 v) {
        this.x += v.x;
        this.y += v.y;
    }

    public void sub(PhysicsVec2 v) {
        this.x -= v.x;
        this.y -= v.y;
    }
    
    public double getLength() {
        return Math.sqrt(x * x + y * y);
    }
    
    public void normalize() {
        double length = getLength();
        if (length == 0) {
            return;
        }
        scale(1 / length);
    }

    public double dot(PhysicsVec2 v) {
        return x * v.x + y * v.y;
    }

    public void setPerp() {
        set(-y, x);
    }
    
    public PhysicsVec2 perp() {
        return new PhysicsVec2(-y, x);
    }
    
    private static final PhysicsVec2 auxPerpVec = new PhysicsVec2();
    
    public double perpDot(PhysicsVec2 v) {
        auxPerpVec.set(this);
        auxPerpVec.setPerp();
        return auxPerpVec.dot(v);
    }
    
    public double cross(PhysicsVec2 v) {
        return x * v.y - y * v.x;
    }
    
    public void translate(double tx, double ty) {
        x += tx;
        y += ty;
    }
    
    public void rotate(double angle) {
        double s = Math.sin(angle);
        double c = Math.cos(angle);
        double nx = x * c - y * s;
        double ny = x * s + y * c;
        set(nx, ny);
    }
    
    @Override
    public String toString() {
        return "Vec2{" + "x=" + x + ", y=" + y + '}';
    }

}
