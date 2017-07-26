package br.ol.g2d.editor.text.screen.preview;

import br.ol.g2d.G2DContext;
import br.ol.ui.model.ActionPanel;
import br.ol.ui.model.Tool;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import javax.swing.JViewport;

/**
 * ShowToolInfoAction class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class ShowCursorPositionInfoAction extends ActionPanel<BitmapTextScreenPreviewPanel> {

    protected G2DContext context;
    private static Font fontNormal = new Font("Arial", Font.PLAIN, 12);
    private static Font fontBold = new Font("Arial", Font.BOLD, 12);
    private static final int NORTH = 1;
    private static final int SOUTH = 2;
    private int showLocation = NORTH;
    private boolean showWhenNotExclusiveAction = true;
    private boolean showWhenMouseOver = true;
    
    public ShowCursorPositionInfoAction(BitmapTextScreenPreviewPanel panel, G2DContext context) {
        super(panel);
        this.context = context;
    }
    
    @Override
    public boolean isActivated() {
        return context != null && context.getSelectedTextScreen() != null;
    }

    @Override
    public void mouseEntered(MouseEvent e, int px, int py) {
        showWhenMouseOver = true;
        panel.repaint();
    }

    @Override
    public void mouseExited(MouseEvent e, int px, int py) {
        showWhenMouseOver = false;
        panel.repaint();
    }
    
    @Override
    public void mouseMoved(MouseEvent e, int px, int py) {
        if (panel.getParent() instanceof JViewport) {
            JViewport viewport = (JViewport) panel.getParent();
            int cy = e.getY() - viewport.getViewPosition().y;
            int nShowLocation = showLocation;
            if (cy > viewport.getHeight() * 0.75 ) {
                nShowLocation = NORTH;
            }
            else if (cy < viewport.getHeight() * 0.25) {
                nShowLocation = SOUTH;
            }
            if (nShowLocation != showLocation) {
                showLocation = nShowLocation;
                panel.repaint();
            }
        }
    }
    
    @Override
    public void draw(Graphics2D g) {
        if (!showWhenNotExclusiveAction || !showWhenMouseOver) {
            return;
        }
        
        Font originalFont = g.getFont();
        g.setFont(fontBold);
        int fontHeight = g.getFontMetrics().getHeight();
        
        int tx = 0, ty = 0, tw = panel.getWidth(), th = fontHeight + fontHeight / 2;
        if (panel.getParent() instanceof JViewport) {
            JViewport viewport = (JViewport) panel.getParent();
            tx = viewport.getViewPosition().x;
            ty = viewport.getViewPosition().y;
            tw = viewport.getWidth();
            if (showLocation == SOUTH) {
                ty += viewport.getHeight() - th;
            }
        }
        
        g.setColor(Color.WHITE);
        g.fillRect(tx, ty, tw, th);
        g.setColor(Color.BLACK);
        g.drawRect(tx - 1, ty, tw + 2, th);
        String cursorPositionText = "Cursor position (col, row): ";
        int currentToolTextWidth = g.getFontMetrics().stringWidth(cursorPositionText);
        g.drawString(cursorPositionText, tx + 5, ty + th - fontHeight / 2);
        g.setFont(fontNormal);
        g.drawString("(" + panel.cursorX + ", " + panel.cursorY + ")", tx + currentToolTextWidth + 5, ty + th - fontHeight / 2);
        g.setFont(originalFont);
    }

    @Override
    public void exclusiveActionStarted(ActionPanel action) {
        showWhenNotExclusiveAction = false;
        panel.repaint();
    }

    @Override
    public void exclusiveActionFinished(ActionPanel action) {
        showWhenNotExclusiveAction = true;
        panel.repaint();
    }
    
}
