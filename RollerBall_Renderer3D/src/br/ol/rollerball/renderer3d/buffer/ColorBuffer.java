package br.ol.rollerball.renderer3d.buffer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;

/**
 *
 * @author leonardo
 */
public class ColorBuffer {

    private BufferedImage colorBuffer;
    private Graphics2D g2d;
    private WritableRaster raster;
    private int[] data;
    
    private int width;
    private int height;
    private int halfWidth;
    private int halfHeight;
    
    public ColorBuffer(int width, int height) {
        this.width = width;
        this.height = height;
        this.halfWidth = width / 2;
        this.halfHeight = height / 2;
        
        colorBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        g2d = (Graphics2D) colorBuffer.getGraphics();
        raster = colorBuffer.getRaster();
        data = ((DataBufferInt) raster.getDataBuffer()).getData();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public BufferedImage getColorBuffer() {
        return colorBuffer;
    }

    public void setBackgroundColor(Color color) {
        g2d.setBackground(color);
    }
    
    public void clear() {
        g2d.clearRect(0, 0, colorBuffer.getWidth(), colorBuffer.getHeight());
    }

    public void setPixel(int x, int y, int[] c) {
        x += halfWidth;
        y = halfHeight - y;
        data[x + y * width] = c[3] + (c[2] << 8) + (c[1] << 16) + (c[0] << 24);
    }

    public int getPixel(int x, int y) {
        x += halfWidth;
        y = halfHeight - y;
        return data[x + y * width];
    }

    public void getPixel(int x, int y, int[] color) {
        x += halfWidth;
        y = halfHeight - y;
        int c = data[x + y * width];
        color[0] = (c & 0xff000000) >> 24;
        color[1] = (c & 0x00ff0000) >> 16;
        color[2] = (c & 0x0000ff00) >> 8;
        color[3] = (c & 0x000000ff);
    }
    
}
