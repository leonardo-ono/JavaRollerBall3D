package br.ol.g2d;

import java.awt.Color;
import java.awt.Composite;
import java.awt.CompositeContext;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.Serializable;

/**
 * Sprite class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class Sprite implements Serializable {

    private transient G2DContext context;
    private String name;
    private SpriteSheet sheet;
    private final Rectangle rectangle = new Rectangle();
    private final Rectangle lastRectangle = new Rectangle();
    private int pivotX, pivotY;
    private boolean transparentEnabled = true;
    private AffineTransform transform = new AffineTransform();
    private transient BufferedImage spriteImage;
    private final TransparentComposite transparentComposite = new TransparentComposite();
    
    public Sprite(String name, SpriteSheet sheet) {
        this.name = name;
        this.sheet = sheet;
        this.context = sheet.getContext();
    }
    
    public G2DContext getContext() {
        return context;
    }
    
    public void updateContext(G2DContext context) {
        this.context = context;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public SpriteSheet getSheet() {
        return sheet;
    }

    public void setSheet(SpriteSheet sheet) {
        this.sheet = sheet;
    }
    
    public Rectangle getRectangle() {
        return rectangle;
    }
    
    public void setRectangle(int x, int y, int width, int height) {
        rectangle.setBounds(x, y, width, height);
    }

    public int getPivotX() {
        return pivotX;
    }

    public void setPivotX(int pivotX) {
        this.pivotX = pivotX;
    }

    public int getPivotY() {
        return pivotY;
    }

    public void setPivotY(int pivotY) {
        this.pivotY = pivotY;
    }

    public boolean isTransparentEnabled() {
        return transparentEnabled;
    }

    public void setTransparentEnabled(boolean transparentEnabled) {
        this.transparentEnabled = transparentEnabled;
    }

    public int getWidth() {
        return rectangle.width;
    }

    public int getHeight() {
        return rectangle.height;
    }
    
    public void setPixel(int x, int y, int c) {
        if (!isOutOfRectangle(x, y)) {
            sheet.setPixel(x + rectangle.x, y + rectangle.y, c);
            context.update();
        }
    }

    public int getPixel(int x, int y) {
        if (!isOutOfRectangle(x, y)) {
            return sheet.getPixel(x + rectangle.x, y + rectangle.y);
        }
        return 0;
    }

    public boolean isOutOfRectangle(int x, int y) {
        return x < 0 || x > rectangle.width - 1 || y < 0 || y > rectangle.height - 1;
    }
    
    public BufferedImage createImage() {
        BufferedImage image = new BufferedImage(rectangle.width, rectangle.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.drawImage(sheet.getImage(), 0, 0, rectangle.width, rectangle.height
                , rectangle.x, rectangle.y, rectangle.x + rectangle.width, rectangle.y + rectangle.height, null);
        return image;
    }
    
    public void draw(Graphics2D g) {
        draw(g, 0, 0);
    }

    public void draw(Graphics2D g, int dx, int dy) {
        draw(g, dx, dy, 1);
    }
    
    public void draw(Graphics2D g, int dx, int dy, double alpha) {
        //AffineTransform at = g.getTransform();
        //g.translate(-pivotX, -pivotX);
        transform.setToIdentity();
        transform.translate(-pivotX + dx, -pivotY + dy);
        if (spriteImage == null || !rectangle.equals(lastRectangle)) {
            spriteImage = sheet.getImage().getSubimage(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
            lastRectangle.setBounds(rectangle);
        }
        Composite originalComposite = g.getComposite();
        if (transparentEnabled) {
            transparentComposite.alpha = alpha;
            g.setComposite(transparentComposite);
        }
        g.drawImage(spriteImage, transform, null);
        g.setComposite(originalComposite);
        //g.drawImage(sheet.getImage(), 0, 0, rectangle.width, rectangle.height
        //        , rectangle.x, rectangle.y, rectangle.x + rectangle.width, rectangle.y + rectangle.height, null);
        //g.setTransform(at);
    }
    
    private class TransparentComposite implements Serializable, Composite {
        
        double alpha = 1;
        
        @Override
        public CompositeContext createContext(ColorModel srcColorModel, ColorModel dstColorModel, RenderingHints hints) {
            return new TransparentCompositeContext();
        }
        
        private class TransparentCompositeContext implements CompositeContext {

            int[] srcData = new int[4];
            int[] dstData = new int[4];
            int[] finalData = new int[4];

            @Override
            public void dispose() {
            }

            @Override
            public void compose(Raster src, Raster dst, WritableRaster dstOut) {
                if (alpha == 0) {
                    return;
                }
                Color background = context.getSpriteSheet().getBackgroundColor();
                int r = background != null ? background.getRed() : 0;
                int g = background != null ? background.getGreen() : 0;
                int b = background != null ? background.getBlue() : 0;
                for (int y = 0; y < dst.getHeight(); y++) {
                    for (int x = 0; x < dst.getWidth(); x++) {
                        dst.getPixel(x, y, dstData);
                        src.getPixel(x, y, srcData);
                        if (srcData[0] != r || srcData[1] != g || srcData[2] != b) {
                            if (alpha == 1) {
                                dstOut.setPixel(x, y, srcData);
                            }
                            else {
                                finalData[0] = (int) (dstData[0] + (srcData[0] - dstData[0]) * alpha);
                                finalData[1] = (int) (dstData[1] + (srcData[1] - dstData[1]) * alpha);
                                finalData[2] = (int) (dstData[2] + (srcData[2] - dstData[2]) * alpha);
                                finalData[3] = 255;
                                dstOut.setPixel(x, y, finalData);
                            }
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public String toString() {
        return "Sprite{" + "name=" + name + ", sheet=" 
                + sheet + ", rectangle=" + rectangle + '}';
    }

}
