package br.ol.g2d;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;

/**
 * Animation class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public interface AnimationObjectInterface extends Comparable<AnimationObjectInterface> {
    
    public String getName();
    public void updateContext(G2DContext context);
    public void setAnimation(Animation aThis);
    public void draw(int currentFrameIndex, Graphics2D g);
    public void drawBorder(int currentFrameIndex, Graphics2D ig, Color xorColor);
    public boolean updateBorder(int currentFrameIndex);
    public Path2D getBorder();
    public int getRealStartFrameIndex();
    public void setZOrder(int zOrder);
    public int getZOrder();
    
}
