package br.ol.g2d.editor.text.screen.preview;

import br.ol.g2d.G2DContext;
import br.ol.g2d.Sprite;
import br.ol.g2d.TextBitmapScreen;
import br.ol.ui.view.BasePanel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;

/**
 * BitmapTextScreenPreviewPanel class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class BitmapTextScreenPreviewPanel extends BasePanel {
    
    private G2DContext context;
    private int marginWidth = 200;
    private int marginHeight = 200;
    private final int marginWidthMin = 200;
    private final int marginHeightMin = 200;
    private boolean firstTime = true;
    private Sprite backgroundReferenceSprite;
    
    public int cursorX;
    public int cursorY;
    
    public BitmapTextScreenPreviewPanel() {
        setDesiredSizeInPixels(10, 10);
    }
    
    public G2DContext getContext() {
        return context;
    }

    public void setContext(G2DContext context) {
        this.context = context;
    }

    @Override
    public void init() {
        super.init();
        registerBlink();
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
        addAction(new EditBitmapScreenTextAction(this, context));
        addAction(new ShowCursorPositionInfoAction(this, context));
        addAction(new SetBackgroundReferenceSpriteAction(this, context));
    }
    
    @Override
    public void installTools() {
    }

    public void update() {
        setEnabled(context != null && context.getSelectedTextScreen() != null);
        if (isEnabled()) {
            marginWidth = (getParent().getWidth() - context.getSelectedTextScreen().getWidth()) / 2;
            marginHeight = (getParent().getHeight() - context.getSelectedTextScreen().getHeight()) / 2;
            marginWidth = marginWidth < marginWidthMin ? marginWidthMin : marginWidth;
            marginHeight = marginHeight < marginHeightMin ? marginHeightMin : marginHeight;
            final int dWidth = context.getSelectedTextScreen().getWidth() + marginWidth * 2; 
            final int dHeight = context.getSelectedTextScreen().getHeight() + marginHeight * 2;
            setDesiredSizeInPixels(dWidth, dHeight);
            
            if (firstTime) {
                firstTime = false;
                if (getParent() instanceof JViewport) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            JViewport viewport = (JViewport) getParent();
                            viewport.scrollRectToVisible(new Rectangle(marginWidth, marginHeight, context.getSelectedTextScreen().getWidth(), context.getSelectedTextScreen().getHeight()));
                        }
                    });
                }
            }
                
        }
        else {
            setDesiredSizeInPixels(10, 10);
        }
        repaint();
    }

    @Override
    public boolean canDraw() {
        return context != null && context.getSelectedTextScreen() != null;
    }
    
    @Override
    public void drawScaled(Graphics2D g) {
        TextBitmapScreen selectedTextScreen = context.getSelectedTextScreen();
        if (selectedTextScreen == null) {
            return;
        }
        
        
        AffineTransform at = g.getTransform();
        
        g.translate(marginWidth, marginHeight);
        
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, selectedTextScreen.getWidth(), selectedTextScreen.getHeight());
        
        if (backgroundReferenceSprite != null) {
            backgroundReferenceSprite.draw(g);
        }
        
        selectedTextScreen.draw(g);
        
        if (selectedTextScreen.showGridEditor) {
            selectedTextScreen.drawGrid(g);
        }
        
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, selectedTextScreen.getWidth(), selectedTextScreen.getHeight());
        
        g.setTransform(at);
    }

    @Override
    public void updateBlink(boolean blinking) {
        repaint();
    }

    
}
