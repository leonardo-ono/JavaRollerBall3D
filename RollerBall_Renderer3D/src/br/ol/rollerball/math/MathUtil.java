package br.ol.rollerball.math;

/**
 *
 * @author leonardo
 */
public class MathUtil {

    public static double clamp(double v, double vMin, double vMax) {
        v = Math.max(v, vMin);
        v = Math.min(v, vMax);
        return v;
    }

    public static double lerp(double a, double b, double p) {
        return a + p * (b - a);
    }
    
}
