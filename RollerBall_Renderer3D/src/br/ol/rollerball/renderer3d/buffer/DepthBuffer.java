package br.ol.rollerball.renderer3d.buffer;

import java.util.Arrays;

/**
 *
 * @author leonardo
 */
public class DepthBuffer {

    private double[] data;

    private int width;
    private int height;
    private int halfWidth;
    private int halfHeight;
    
    private double clearValue = Double.POSITIVE_INFINITY;
            
    public DepthBuffer(int width, int height) {
        this.width = width;
        this.height = height;
        this.halfWidth = width / 2;
        this.halfHeight = height / 2;
        
        data = new double[width * height];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public double getClearValue() {
        return clearValue;
    }

    public void setClearValue(double clearValue) {
        this.clearValue = clearValue;
    }
    
    public void clear() {
        Arrays.fill(data, clearValue);
    }

    public void set(int x, int y, double depth) {
        x += halfWidth;
        y = halfHeight - y;
        data[x + y * width] = depth;
    }
    
    public double get(int x, int y) {
        x += halfWidth;
        y = halfHeight - y;
        double r = 0;
        r = data[x + y * width];
        return r;
    }
    
}
