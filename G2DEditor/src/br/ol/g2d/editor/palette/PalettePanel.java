package br.ol.g2d.editor.palette;

import br.ol.g2d.G2DContext;
import br.ol.g2d.Palette;
import br.ol.g2d.PaletteColor;
import br.ol.g2d.editor.PickBackgroundColorAction;
import br.ol.g2d.editor.PickColorAction;
import br.ol.ui.view.BasePanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;

/**
 * PalettePanel class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class PalettePanel extends BasePanel {
    
    private G2DContext context;
    private int colorSize = 20;
    private final Stroke stroke = new BasicStroke(4);
    
    public PalettePanel() {
        setDesiredSizeInPixels(10, 10);
    }
    
    public G2DContext getContext() {
        return context;
    }

    public void setContext(G2DContext context) {
        this.context = context;
    }

    public int getColorSize() {
        return colorSize;
    }

    public void setColorSize(int colorSize) {
        this.colorSize = colorSize;
    }

    @Override
    public void installActions() {
        addAction(new PickColorAction(this, context));
        addAction(new PickBackgroundColorAction(this, context));
        addAction(new SelectPaletteColorAction(this, context));
        addAction(new AddPaletteColorAction(this, context));
        addAction(new DeletePaletteColorAction(this, context));
    }
    
    @Override
    public void installTools() {
        addTool(new MovePaletteColorPositionTool(this, context));
    }

    int provWidth = 200; // TODO
    
    public void update() {
        setEnabled(context != null && context.getPalette()!= null);
        if (isEnabled()) {
            int dWidth = provWidth; // getParent().getWidth() / scale;
            int dHeight = calculateDesiredHeight();
            setDesiredSizeInPixels(dWidth, dHeight);
        }
        repaint();
    }

    private int calculateDesiredHeight() {
        int colorsCount = context.getPalette().getColors().size();
        int cols = (provWidth - colorSize) / colorSize;
        int rows = colorsCount / cols + 1;
        return rows * colorSize;
    }
    
    @Override
    public boolean canDraw() {
        return context != null && context.getPalette()!= null;
    }
    
    @Override
    public void drawScaled(Graphics2D g) {
        Palette palette = context.getPalette();
        int x = 0, y = 0;
        for (PaletteColor color : palette.getColors()) {
            color.setRectangle(x, y, colorSize, colorSize);
            color.draw(g);
            x += colorSize;
            if (x > getDesiredSize().width - colorSize) {
                x = 0;
                y += colorSize;
            }
        }
        if (palette.getSelectedColor() != null && palette.getSelectedColor() != palette.getNotInPaletteColor()) {
            Stroke originalStroke = g.getStroke();
            g.setColor(Color.BLACK);
            g.setStroke(stroke);
            Rectangle r = palette.getSelectedColor().getRectangle();
            g.drawRect(r.x - 2, r.y - 2, r.width + 4, r.height + 4);
            g.setStroke(originalStroke);
        }
    }
    
}
