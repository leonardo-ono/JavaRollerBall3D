package br.ol.g2d;

import java.io.Serializable;

/**
 *
 * @author leonardo
 */
public class AnimationObjectFrame implements Serializable {
    
    private transient G2DContext context;
    public boolean tween;
    public double x, y;
    public double px, py;
    public double sx = 1, sy = 1;
    public double angle;
    public double alpha = 1;
    
    public void set(AnimationObjectFrame f) {
        tween = f.tween;
        x = f.x;
        y = f.y;
        px = f.px;
        py = f.py;
        sx = f.sx;
        sy = f.sy;
        angle = f.angle;
        alpha = f.alpha;
    }

    public G2DContext getContext() {
        return context;
    }
    
    public void updateContext(G2DContext context) {
        this.context = context;
    }
    
    public void interpolate(AnimationObjectFrame fa, AnimationObjectFrame fb, int faIndex, int fbIndex, int currentIndex) {
        double p = (currentIndex - faIndex) / (double) (fbIndex - faIndex);
        x = fa.x + p * (fb.x - fa.x);
        y = fa.y + p * (fb.y - fa.y);
        px = fa.px + p * (fb.px - fa.px);
        py = fa.py + p * (fb.py - fa.py);
        sx = fa.sx + p * (fb.sx - fa.sx);
        sy = fa.sy + p * (fb.sy - fa.sy);
        angle = fa.angle + p * (fb.angle - fa.angle);
        alpha = fa.alpha + p * (fb.alpha - fa.alpha);
        tween = true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.px) ^ (Double.doubleToLongBits(this.px) >>> 32));
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.py) ^ (Double.doubleToLongBits(this.py) >>> 32));
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.sx) ^ (Double.doubleToLongBits(this.sx) >>> 32));
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.sy) ^ (Double.doubleToLongBits(this.sy) >>> 32));
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.angle) ^ (Double.doubleToLongBits(this.angle) >>> 32));
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.alpha) ^ (Double.doubleToLongBits(this.alpha) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AnimationObjectFrame other = (AnimationObjectFrame) obj;
        if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x)) {
            return false;
        }
        if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(other.y)) {
            return false;
        }
        if (Double.doubleToLongBits(this.px) != Double.doubleToLongBits(other.px)) {
            return false;
        }
        if (Double.doubleToLongBits(this.py) != Double.doubleToLongBits(other.py)) {
            return false;
        }
        if (Double.doubleToLongBits(this.sx) != Double.doubleToLongBits(other.sx)) {
            return false;
        }
        if (Double.doubleToLongBits(this.sy) != Double.doubleToLongBits(other.sy)) {
            return false;
        }
        if (Double.doubleToLongBits(this.angle) != Double.doubleToLongBits(other.angle)) {
            return false;
        }
        if (Double.doubleToLongBits(this.alpha) != Double.doubleToLongBits(other.alpha)) {
            return false;
        }
        return true;
    }
    
}
