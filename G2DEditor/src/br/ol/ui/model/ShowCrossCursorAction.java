package br.ol.ui.model;

import br.ol.ui.view.BasePanel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;

/**
 * ShowCrossCursorAction class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class ShowCrossCursorAction extends ActionPanel<BasePanel> {

    private boolean showWhenMouseOver = false;
    private boolean showCrossCursor = true;
    private final Point mousePosition = new Point();
    private final Color cursorXorColor = Color.BLUE;
    private final Line2D horizontalLine = new Line2D.Double();
    private final Line2D verticalLine = new Line2D.Double();
    
    public ShowCrossCursorAction(BasePanel panel) {
        super(panel);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_X) {
            showCrossCursor = !showCrossCursor;
            panel.repaint();
        }
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
        mousePosition.setLocation(px, py);
        panel.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e, int px, int py) {
        mousePosition.setLocation(px, py);
        panel.repaint();
    }
    
    @Override
    public void drawScaled(Graphics2D g) {
        if (!showCrossCursor || !showWhenMouseOver) {
            return;
        }
        horizontalLine.setLine(mousePosition.x + 0.5, 0 + 0.5, mousePosition.x + 0.5, panel.getWidth() + 0.5);
        verticalLine.setLine(0 + 0.5, mousePosition.y + 0.5, panel.getWidth() + 0.5, mousePosition.y + 0.5);
        
        g.setXORMode(cursorXorColor);
        g.draw(verticalLine);
        g.draw(horizontalLine);
        g.setPaintMode();
        panel.repaint();
    }
    
}
