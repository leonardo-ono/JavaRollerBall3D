package br.ol.g2d.editor.sprite;

import br.ol.g2d.G2DContext;
import br.ol.g2d.Sprite;
import br.ol.g2d.editor.PickBackgroundColorAction;
import br.ol.g2d.editor.PickColorAction;
import br.ol.ui.view.BasePanel;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * SpritePanel class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class SpritePanel extends BasePanel {
    
    private G2DContext context;

    public SpritePanel() {
        scale = 10;
        setDesiredSizeInPixels(10, 10);
    }
    
    public G2DContext getContext() {
        return context;
    }

    public void setContext(G2DContext context) {
        this.context = context;
    }

    @Override
    public void installActions() {
        addAction(new PickColorAction(this, context));
        addAction(new PickBackgroundColorAction(this, context));
        addAction(new ShowHideGridAction(this, context));
        addAction(new PutPixelAction(this, context));
        addAction(new FloodFillAction(this, context));
        addAction(new SetPivotPositionAction(this, context));
    }
    
    @Override
    public void installTools() {
    }

    public void update() {
        setEnabled(context != null && context.getSelectedSprite() != null);
        if (isEnabled()) {
            Sprite selectedSprite = context.getSelectedSprite();
            int dWidth = (int) selectedSprite.getRectangle().getWidth();
            int dHeight = (int) selectedSprite.getRectangle().getHeight();
            setDesiredSizeInPixels(dWidth, dHeight);
        }
        else {
            setDesiredSizeInPixels(1, 1);
        }
        repaint();
    }

    @Override
    public boolean canDraw() {
        return context != null && context.getSelectedSprite()!= null;
    }
    
    @Override
    public void drawScaled(Graphics2D g) {
        Sprite selectedSprite = context.getSelectedSprite();
        int px = selectedSprite.getPivotX();
        int py = selectedSprite.getPivotY();
        selectedSprite.setPivotX(0);
        selectedSprite.setPivotY(0);
        selectedSprite.draw(g);
        selectedSprite.setPivotX(px);
        selectedSprite.setPivotY(py);
    }

    @Override
    public Color getPixelColor(int x, int y) {
        if (context.getSelectedSprite()== null) {
            return super.getPixelColor(x, y); 
        }
        else {
            return new Color(context.getSelectedSprite().getPixel(x, y));
        }
    }

    
    
}
