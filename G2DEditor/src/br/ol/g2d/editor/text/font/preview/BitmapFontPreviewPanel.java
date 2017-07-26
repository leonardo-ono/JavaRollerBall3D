package br.ol.g2d.editor.text.font.preview;

import br.ol.g2d.G2DContext;
import br.ol.g2d.TextBitmapFont;
import br.ol.ui.view.BasePanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * BitmapFontPreviewPanel class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class BitmapFontPreviewPanel extends BasePanel {
    
    private G2DContext context;
    private final static Font fontNormal = new Font("arial", Font.PLAIN, 12);
    private final Rectangle charRectangleAtPosition = new Rectangle();
    
    public BitmapFontPreviewPanel() {
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
    
    @Override
    public void installActions() {
        addAction(new SetSpriteToBitmapFontCharAction(this, context));
        addAction(new RemoveSpriteToBitmapFontCharAction(this, context));
    }
    
    @Override
    public void installTools() {
    }

    public void update() {
        setEnabled(context != null && context.getSelectedFont()!= null);
        if (isEnabled()) {
            TextBitmapFont selectedFont = context.getSelectedFont();
            int textHeight = getGraphics().getFontMetrics(fontNormal).getHeight() + 4 + selectedFont.height;
            int textWidth = getGraphics().getFontMetrics(fontNormal).stringWidth("@ (@@@)") + 4; // + selectedFont.width;
            int dWidth = textWidth * 16;
            int dHeight = textHeight * 16;
            setDesiredSizeInPixels(dWidth, dHeight);
        }
        repaint();
    }

    @Override
    public boolean canDraw() {
        return context != null && context.getSelectedFont() != null;
    }
    
    @Override
    public void drawScaled(Graphics2D g) {
        Font originalFont = g.getFont();
        g.setFont(fontNormal);
        TextBitmapFont selectedFont = context.getSelectedFont();
        int textHeight = getGraphics().getFontMetrics(fontNormal).getHeight() + 4 + selectedFont.height;
        int textWidth = getGraphics().getFontMetrics(fontNormal).stringWidth("@ (@@@)") + 4; // + selectedFont.width;
        for (int row = 0; row < 16; row++) {
            for (int col = 0; col < 16; col++) {
                g.setColor(Color.BLACK);
                g.drawRect(col * textWidth, row * textHeight, textWidth, textHeight);
                char c = ((char) ((col * 16) + row));
                g.drawString(c + " (" + ((int) c) + ")" , col * textWidth + 2, row * textHeight + textHeight - 4);
                if (selectedFont.isCharAvailable(c)) {
                    selectedFont.drawChar(g, c, col * textWidth, row * textHeight);
                }
                else {
                    g.setColor(Color.RED);
                    g.fillRect(col * textWidth, row * textHeight, selectedFont.getWidth(), selectedFont.getHeight());
                }
            }
        }
        g.setFont(originalFont);
    }
    
    public Character getCharAtPosition(int px, int py) {
        TextBitmapFont selectedFont = context.getSelectedFont();
        int textHeight = getGraphics().getFontMetrics(fontNormal).getHeight() + 4 + selectedFont.height;
        int textWidth = getGraphics().getFontMetrics(fontNormal).stringWidth("@ (@@@)") + 4; // + selectedFont.width;
        int col = px / textWidth;
        int row = py / textHeight;
        int ci = ((col * 16) + row);
        Character c = ci > 255 ? null : (char) ci;
        return c;
    }

    public Rectangle getCharRectangleAtPosition(int px, int py) {
        TextBitmapFont selectedFont = context.getSelectedFont();
        int textHeight = getGraphics().getFontMetrics(fontNormal).getHeight() + 4 + selectedFont.height;
        int textWidth = getGraphics().getFontMetrics(fontNormal).stringWidth("@ (@@@)") + 4; // + selectedFont.width;
        int col = px / textWidth;
        int row = py / textHeight;
        charRectangleAtPosition.setBounds(col * textWidth, row * textHeight, textWidth, textHeight);
        return charRectangleAtPosition;
    }

    @Override
    public void updateBlink(boolean blinking) {
        repaint();
    }
    
}
