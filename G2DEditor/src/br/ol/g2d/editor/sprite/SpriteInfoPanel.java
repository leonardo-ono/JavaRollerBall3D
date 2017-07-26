package br.ol.g2d.editor.sprite;

import br.ol.g2d.G2DContext;
import br.ol.g2d.Sprite;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JPanel;

/**
 * SpriteInfoPanel class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class SpriteInfoPanel extends JPanel {
    
    private G2DContext context;
    private final static Font fontNormal = new Font("Arial", Font.PLAIN, 12);
    private final static Font fontBold = new Font("Arial", Font.BOLD, 12);
    private final Dimension size = new Dimension();
    
    public SpriteInfoPanel() {
    }

    public G2DContext getContext() {
        return context;
    }

    public void setContext(G2DContext context) {
        this.context = context;
    }

    public void init() {
    }

    public void update() {
        setEnabled(context != null && context.getSelectedSprite() != null);
        FontMetrics fm = getGraphics().getFontMetrics(fontNormal);
        size.setSize(100, 4 * fm.getHeight() + 4);
        setPreferredSize(size);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        
        if (context == null || context.getSpriteSheet() == null) {
            return;
        }
        Font originalFont = g.getFont();
        g.setFont(fontBold);
        int fontHeight = g.getFontMetrics().getHeight();
        if (context.getSelectedSprite() != null) {
            Sprite selectedSprite = context.getSelectedSprite();
            Rectangle r = selectedSprite.getRectangle();

            String nameText = "Sprite name: ";
            int nameTextWidth = g.getFontMetrics().stringWidth(nameText);

            String rectangleText = "Sprite rectangle (x, y, w, h): ";
            int rectangleTextWidth = g.getFontMetrics().stringWidth(rectangleText);
            String rectangleInfoText = "(" + r.x + ", " + r.y + ", " + r.width + ", " + r.height + ")";

            String pivotText = "Sprite pivot (x, y): ";
            int pivotTextWidth = g.getFontMetrics().stringWidth(pivotText);
            String pivotInfoText = "(" + selectedSprite.getPivotX() + ", " + selectedSprite.getPivotY() + ") ";

            g.setFont(fontBold);
            g.drawString(nameText, 2, fontHeight);
            g.setFont(fontNormal);
            g.drawString(selectedSprite.getName(), nameTextWidth + 2, fontHeight);

            g.setFont(fontBold);
            g.drawString(rectangleText, 2, fontHeight * 2);
            g.setFont(fontNormal);
            g.drawString(rectangleInfoText, rectangleTextWidth + 2, fontHeight * 2);

            g.setFont(fontBold);
            g.drawString(pivotText, 2, fontHeight * 3);
            g.setFont(fontNormal);
            g.drawString(pivotInfoText, pivotTextWidth + 2, fontHeight * 3);
        }
        
        String spriteBaseNameText = "Sprite base name: ";
        int spriteBaseNameTextWidth = g.getFontMetrics().stringWidth(spriteBaseNameText);
        g.setFont(fontBold);
        g.drawString(spriteBaseNameText, 2, fontHeight * 4);
        g.setFont(fontNormal);
        g.drawString(context.getSpriteSheet().getSpriteBaseName(), spriteBaseNameTextWidth + 2, fontHeight * 4);
        
        g.setFont(originalFont);
    }

}
