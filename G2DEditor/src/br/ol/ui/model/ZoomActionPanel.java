package br.ol.ui.model;

import br.ol.ui.view.BasePanel;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import javax.swing.JViewport;

/**
 * ZoomActionPanel class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class ZoomActionPanel extends ActionPanel<BasePanel> {
    
    private Point mousePixelPosition = new Point();
    private Point mousePanelPosition = new Point();
    
    public ZoomActionPanel(BasePanel panel) {
        super(panel);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        switch (e.getKeyChar()) {
            case '+':
                panel.setScale(panel.getScale() + 1);
                updatePanel();
                break;
            case '-':
                panel.setScale(panel.getScale() - 1);
                updatePanel();
                break;
        }
    }
    
    public void updatePanel() {
        panel.updatePreferredSize();
        if (panel.getParent() instanceof JViewport) {
            JViewport viewPort = (JViewport) panel.getParent();
            Point viewPosition = viewPort.getViewPosition();
            int rx = mousePanelPosition.x - viewPosition.x;
            int ry = mousePanelPosition.y - viewPosition.y;
            int nx = mousePixelPosition.x * panel.getScale() - rx;
            int ny = mousePixelPosition.y * panel.getScale() - ry;
            nx = nx < 0 ? 0 : nx;
            ny = ny < 0 ? 0 : ny;
            viewPort.doLayout();
            viewPosition.setLocation(nx, ny);
            viewPort.setViewPosition(viewPosition);
            mousePanelPosition.x = mousePixelPosition.x * panel.getScale();
            mousePanelPosition.y = mousePixelPosition.y * panel.getScale();
        }
        panel.revalidate();
        panel.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e, int px, int py) {
        mousePixelPosition.setLocation(px, py);
        mousePanelPosition.setLocation(e.getX(), e.getY());
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e, int px, int py) {
        if (e.getWheelRotation() < 0) {
            panel.setScale(panel.getScale() + 1);
            updatePanel();
        }
        else {
            panel.setScale(panel.getScale() - 1);
            updatePanel();
        }
    }
    
}
