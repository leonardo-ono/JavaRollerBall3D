package br.ol.g2d.editor.animation.preview;

import br.ol.g2d.Animation;
import br.ol.g2d.G2DContext;
import br.ol.g2d.Sprite;
import br.ol.ui.view.BasePanel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;

/**
 * AnimationPreviewPanel class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class AnimationPreviewPanel extends BasePanel {
    
    private G2DContext context;
    private BufferedImage image;
    private int marginWidth = 200;
    private int marginHeight = 200;
    private final int marginWidthMin = 200;
    private final int marginHeightMin = 200;
    private boolean firstTime = true;
    private Sprite backgroundReferenceSprite;
    
    public AnimationPreviewPanel() {
        setDesiredSizeInPixels(10, 10);
    }
    
    public G2DContext getContext() {
        return context;
    }

    public void setContext(G2DContext context) {
        this.context = context;
    }

    public int getMarginWidth() {
        return marginWidth;
    }

    public int getMarginHeight() {
        return marginHeight;
    }

    public int getMarginWidthMin() {
        return marginWidthMin;
    }

    public int getMarginHeightMin() {
        return marginHeightMin;
    }
    
    public Sprite getBackgroundReferenceSprite() {
        return backgroundReferenceSprite;
    }

    public void setBackgroundReferenceSprite(Sprite backgroundReferenceSprite) {
        this.backgroundReferenceSprite = backgroundReferenceSprite;
    }
    
    @Override
    public void installActions() {
        addAction(new SelectAnimationObjectAction(this, context));
        addAction(new NewAnimationObjectAction(this, context));
        addAction(new SetSpriteAtCurrentFrameAction(this, context));
        addAction(new RemoveSpriteAtCurrentFrameAction(this, context));
        addAction(new DeleteAnimationObjectAction(this, context));
        addAction(new BringAnimationObjectToFrontOrBackAction(this, context));
        addAction(new SetBackgroundReferenceSpriteAction(this, context));
    }
    
    @Override
    public void installTools() {
        addTool(new MoveAnimationObjectTool(this, context));
    }

    public void update() {
        setEnabled(context != null && context.getSelectedAnimation() != null);
        if (isEnabled()) {
            if (image == null || image.getWidth() !=  context.getSelectedAnimation().screenWidth || image.getHeight() != context.getSelectedAnimation().screenHeight) {
                image = new BufferedImage(context.getSelectedAnimation().screenWidth, context.getSelectedAnimation().screenHeight, BufferedImage.TYPE_INT_ARGB);
            }
            marginWidth = (getParent().getWidth() - context.getSelectedAnimation().screenWidth) / 2;
            marginHeight = (getParent().getHeight() - context.getSelectedAnimation().screenHeight) / 2;
            marginWidth = marginWidth < marginWidthMin ? marginWidthMin : marginWidth;
            marginHeight = marginHeight < marginHeightMin ? marginHeightMin : marginHeight;
            final int dWidth = context.getSelectedAnimation().screenWidth + marginWidth * 2; 
            final int dHeight = context.getSelectedAnimation().screenHeight + marginHeight * 2;
            setDesiredSizeInPixels(dWidth, dHeight);
            
            if (firstTime) {
                firstTime = false;
                if (getParent() instanceof JViewport) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            JViewport viewport = (JViewport) getParent();
                            viewport.scrollRectToVisible(new Rectangle(marginWidth, marginHeight, context.getSelectedAnimation().screenWidth, context.getSelectedAnimation().screenHeight));
                        }
                    });
                }
            }
                
        }
        repaint();
    }

    @Override
    public boolean canDraw() {
        return context != null && context.getSelectedAnimation() != null;
    }
    
    @Override
    public void drawScaled(Graphics2D g) {
        Animation selectedAnimation = context.getSelectedAnimation();
        if (selectedAnimation == null) {
            return;
        }
        AffineTransform at = g.getTransform();
        g.translate(marginWidth, marginHeight);
        
        Graphics2D ig = (Graphics2D) image.getGraphics();

        ig.setColor(Color.WHITE);
        ig.fillRect(0, 0, context.getSelectedAnimation().screenWidth, context.getSelectedAnimation().screenHeight);
        
        if (backgroundReferenceSprite != null) {
            backgroundReferenceSprite.draw(ig);
        }
        
        selectedAnimation.draw(ig);
        selectedAnimation.drawBorders(ig);
        g.drawImage(image, 0, 0, null);
        
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, context.getSelectedAnimation().screenWidth, context.getSelectedAnimation().screenHeight);
        g.setTransform(at);
    }
    
    
}
