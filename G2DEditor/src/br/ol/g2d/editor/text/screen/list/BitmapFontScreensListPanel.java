package br.ol.g2d.editor.text.screen.list;

import br.ol.g2d.G2DContext;
import br.ol.g2d.TextBitmapScreen;
import br.ol.ui.view.BasePanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 * BitmapFontScreensListPanel class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class BitmapFontScreensListPanel extends BasePanel {
    
    private G2DContext context;
    private final static Font fontNormal = new Font("Arial", Font.PLAIN, 12);
    private final static Font fontBold = new Font("Arial", Font.BOLD, 12);
    
    public BitmapFontScreensListPanel() {
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
        addAction(new CreateNewBitmapTextScreenAction(this, context));
        addAction(new RenameBitmapTextScreenNameAction(this, context));
        addAction(new DeleteBitmapTextScreenAction(this, context));
        addAction(new SelectBitmapTextScreenAction(this, context));
    }
    
    @Override
    public void installTools() {
    }

    public void update() {
        setEnabled(context != null && context.getTextScreens() != null);
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
        return context != null && context.getTextScreens() != null;
    }
    
    @Override
    public void drawScaled(Graphics2D g) {
        Font originalFont = g.getFont();
        g.setFont(fontNormal);
        int textHeight = g.getFontMetrics().getHeight();
        int y = 1;
        for (TextBitmapScreen screen : context.getTextScreens().getScreens()) {
            if (screen == context.getSelectedTextScreen()) {
                g.setFont(fontBold);
            }
            else {
                g.setFont(fontNormal);
            }
            g.drawString(screen.getName(), 10, y * textHeight);
            if (screen == context.getSelectedTextScreen()) {
                g.setXORMode(Color.WHITE);
                g.fillRect(0, (y - 1) * textHeight + textHeight / 4, getWidth(), textHeight - textHeight / 8);
                g.setPaintMode();
            }
            y++;
        }
        g.setFont(originalFont);
    }
    
    public TextBitmapScreen getTextScreenAtPosition(int px, int py) {
        int textHeight = getGraphics().getFontMetrics(fontNormal).getHeight();
        int selectedIndex = py / textHeight;
        int y = 0;
        for (TextBitmapScreen screen : context.getTextScreens().getScreens()) {
            if (y == selectedIndex) {
                return screen;
            }
            y++;
        }
        return null; 
    }
    
}
