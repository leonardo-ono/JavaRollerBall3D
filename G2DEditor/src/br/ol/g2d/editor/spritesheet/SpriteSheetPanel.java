package br.ol.g2d.editor.spritesheet;

import br.ol.g2d.G2DContext;
import br.ol.g2d.Sprite;
import br.ol.g2d.editor.PickBackgroundColorAction;
import br.ol.g2d.editor.PickColorAction;
import br.ol.ui.view.BasePanel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

/**
 * SpritePanel class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class SpriteSheetPanel extends BasePanel {

    private G2DContext context;
    private final Rectangle2D auxiliarRectangle = new Rectangle2D.Double();
    
    public SpriteSheetPanel() {
        setDesiredSizeInPixels(10, 10);
    }

    @Override
    public void init() {
        super.init();
        registerBlink();
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
        addAction(new SelectSpriteAction(this, context));
        addAction(new DeleteSelectedSpriteAction(this, context));
        addAction(new ChangeSpriteNameAction(this, context));
        addAction(new ChangeSpriteBasenameAction(this, context));
        addAction(new ExportAllSpritesAsSeparatedImagesAction(this, context));
        addAction(new ExportSpriteSheetAsImageAction(this, context));
        addAction(new ReplaceSpriteSheetImageAction(this, context));
    }
    
    @Override
    public void installTools() {
        addTool(new NewSpriteTool(this, context));
        addTool(new ExtractSpriteByFloodFillMethodTool(this, context));
        addTool(new MoveResizeSpriteTool(this, context));
        addTool(new NewGridSpritesTool(this, context));
        addTool(new SetSpriteToBitmapTextFontCharTool(this, context));
    }
    
    public void update() {
        setEnabled(context != null && context.getSpriteSheet() != null);
        if (isEnabled()) {
            int dWidth = context.getSpriteSheet().getImage().getWidth();
            int dHeight = context.getSpriteSheet().getImage().getHeight();
            setDesiredSizeInPixels(dWidth, dHeight);
        }
        else {
            setDesiredSizeInPixels(1, 1);
        }
        repaint();
    }

    @Override
    public boolean canDraw() {
        return context != null && context.getSpriteSheet() != null;
    }
    
    @Override
    public void drawScaled(Graphics2D g) {
        g.drawImage(context.getData().getSpriteSheet().getImage(), 0, 0, null);
        for (Sprite sprite : context.getSprites()) {
            g.setXORMode(Color.WHITE);
            if (context.getSelectedSprite() == sprite) {
                if (isBlinking()) {
                    g.setXORMode(Color.BLACK);
                }
            }
            Rectangle r = sprite.getRectangle();
            double aux = scale > 1 ? 0.5 : 0;
            double rw = r.width - 1 < 1 ? 1 : r.width - 1;
            double rh = r.height - 1 < 1 ? 1 : r.height - 1;
            auxiliarRectangle.setRect(r.x + aux, r.y + aux, rw, rh);
            g.draw(auxiliarRectangle);
            g.setPaintMode();
        }
    }

    @Override
    public void updateBlink(boolean blinking) {
        repaint();
    }

    @Override
    public Color getPixelColor(int x, int y) {
        if (context.getSpriteSheet() == null) {
            return super.getPixelColor(x, y); //To change body of generated methods, choose Tools | Templates.
        }
        else {
            return new Color(context.getSpriteSheet().getImage().getRGB(x, y));
        }
    }
    
}
