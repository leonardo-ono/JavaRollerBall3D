package br.ol.g2d.editor.text.font.list;

import br.ol.g2d.G2DContext;
import br.ol.g2d.TextBitmapFont;
import br.ol.ui.view.BasePanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 * BitmapFontsListPanel class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class BitmapFontsListPanel extends BasePanel {
    
    private G2DContext context;
    private final static Font fontNormal = new Font("Arial", Font.PLAIN, 12);
    private final static Font fontBold = new Font("Arial", Font.BOLD, 12);
    
    public BitmapFontsListPanel() {
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
        addAction(new CreateNewBitmapFontAction(this, context));
        addAction(new RenameBitmapFontNameAction(this, context));
        addAction(new DeleteBitmapFontAction(this, context));
        addAction(new SelectBitmapFontAction(this, context));
        addAction(new CreateNewBitmapFontBySpriteBaseNameAction(this, context));
    }
    
    @Override
    public void installTools() {
    }

    public void update() {
        setEnabled(context != null && context.getFonts() != null);
        if (isEnabled()) {
            int textHeight = getGraphics().getFontMetrics(fontNormal).getHeight();
            int dWidth = getParent().getWidth();
            int dHeight = textHeight * (context.getFonts().getFonts().size() + 1);
            setDesiredSizeInPixels(dWidth, dHeight);
        }
        repaint();
    }

    @Override
    public boolean canDraw() {
        return context != null && context.getFonts() != null;
    }
    
    @Override
    public void drawScaled(Graphics2D g) {
        Font originalFont = g.getFont();
        g.setFont(fontNormal);
        int textHeight = g.getFontMetrics().getHeight();
        int y = 1;
        for (TextBitmapFont font : context.getFonts().getFonts()) {
            if (font == context.getSelectedFont()) {
                g.setFont(fontBold);
            }
            else {
                g.setFont(fontNormal);
            }
            g.drawString(font.getName(), 10, y * textHeight);
            if (font == context.getSelectedFont()) {
                g.setXORMode(Color.WHITE);
                g.fillRect(0, (y - 1) * textHeight + textHeight / 4, getWidth(), textHeight - textHeight / 8);
                g.setPaintMode();
            }
            y++;
        }
        g.setFont(originalFont);
    }
    
    public TextBitmapFont getFontAtPosition(int px, int py) {
        int textHeight = getGraphics().getFontMetrics(fontNormal).getHeight();
        int selectedIndex = py / textHeight;
        int y = 0;
        for (TextBitmapFont font : context.getFonts().getFonts()) {
            if (y == selectedIndex) {
                return font;
            }
            y++;
        }
        return null; 
    }
    
}
