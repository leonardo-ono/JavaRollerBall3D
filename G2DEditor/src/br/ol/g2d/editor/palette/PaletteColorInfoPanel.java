package br.ol.g2d.editor.palette;

import br.ol.g2d.G2DContext;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * PaletteColorInfoPanel class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class PaletteColorInfoPanel extends JPanel {
    
    private G2DContext context;
    private static Font fontNormal = new Font("Arial", Font.PLAIN, 12);
    private static Font fontBold = new Font("Arial", Font.BOLD, 12);
    private Dimension size = new Dimension();
    private int colorSize = 12;
    
    public PaletteColorInfoPanel() {
    }

    public G2DContext getContext() {
        return context;
    }

    public void setContext(G2DContext context) {
        this.context = context;
    }

    public void init() {
    }

    public int getColorSize() {
        return colorSize;
    }

    public void setColorSize(int colorSize) {
        this.colorSize = colorSize;
    }
    
    public void update() {
        setEnabled(context != null && context.getSpriteSheet() != null);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        
        if (context == null || context.getSpriteSheet() == null || context.getPalette() == null) {
            return;
        }
        
        Font originalFont = g.getFont();
        g.setFont(fontBold);
        int fontHeight = g.getFontMetrics().getHeight();
        
        String foregroundText = "Foreground: ";
        String backgroundText = "Background: ";
        int foregroundTextWidth = g.getFontMetrics().stringWidth(foregroundText);
        int backgroundTextWidth = g.getFontMetrics().stringWidth(foregroundText);
        
        if (context.getSelectedPaletteColor() != null) {
            g.setColor(context.getSelectedPaletteColor().getColor());
            g.fillRect(foregroundTextWidth, 4, colorSize, colorSize);
        }
        else {
            g.setColor(Color.BLACK);
            g.drawLine(foregroundTextWidth, 4, foregroundTextWidth + colorSize, 4 + colorSize);
            g.drawLine(foregroundTextWidth + colorSize, 4, foregroundTextWidth, 4 + colorSize);
        }
        g.setColor(Color.BLACK);
        g.drawRect(foregroundTextWidth, 4, colorSize, colorSize);
        g.drawString(foregroundText, 2, fontHeight);

        g.setColor(context.getSpriteSheet().getBackgroundColor());
        g.fillRect(foregroundTextWidth + backgroundTextWidth + colorSize + 22, 4, colorSize, colorSize);
        g.setColor(Color.BLACK);
        g.drawRect(foregroundTextWidth + backgroundTextWidth + colorSize + 22, 4, colorSize, colorSize);
        g.drawString(backgroundText, foregroundTextWidth + colorSize + 20, fontHeight);
        
        g.setFont(originalFont);
    }

}
