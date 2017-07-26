package br.ol.rollerball.renderer3d.core;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author leonardo
 */
public class Image {

    private BufferedImage bi;
    private WritableRaster raster;
    private int[] data;
    
    private int width;
    private int height;
    
    public Image(String resource) {
        try {
            InputStream is = getClass().getResourceAsStream(resource);
            if (is == null) {
                throw new RuntimeException("Could not load resource " + resource + " !");
            }
            BufferedImage biProv = ImageIO.read(is);
            bi = new BufferedImage(biProv.getWidth(), biProv.getHeight(), BufferedImage.TYPE_INT_ARGB);
            bi.getGraphics().drawImage(biProv, 0, 0, null);
        } catch (IOException ex) {
            System.err.println("Could not load resource " + resource + " !");
            Logger.getLogger(Image.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.width = bi.getWidth();
        this.height = bi.getHeight();
        raster = bi.getRaster();
        data = ((DataBufferInt) raster.getDataBuffer()).getData();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public BufferedImage getColorBuffer() {
        return bi;
    }

    public void setPixel(int x, int y, int[] c) {
        data[x + y * width] = c[3] + (c[2] << 8) + (c[1] << 16) + (c[0] << 24);
    }
    
    public int getPixel(int x, int y) {
        return data[x + y * width];
    }

    public void getPixel(int tx, int ty, int[] color) {
        int c = data[tx + ty * width];
        color[0] = (c & 0xff000000) >> 24;
        color[1] = (c & 0x00ff0000) >> 16;
        color[2] = (c & 0x0000ff00) >> 8;
        color[3] = (c & 0x000000ff);
    }
    
}
