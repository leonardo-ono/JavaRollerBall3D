package br.ol.ui.model;

import br.ol.ui.view.BasePanel;
import br.ol.ui.view.ScrollPane;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JViewport;

/**
 * ScrollByDraggingActionPanel class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class ScrollByDraggingActionPanel extends ActionPanel<BasePanel> {
    
    private final Point mouseStartPosition = new Point();
    private final Point viewStartPosition = new Point();
    
    public ScrollByDraggingActionPanel(BasePanel panel) {
        super(panel);
    }

    @Override
    public void init() {
        if (panel.getParent() instanceof JViewport) {
            JViewport viewPort = (JViewport) panel.getParent();
            ScrollPane scrollPane = (ScrollPane) viewPort.getParent();
            MouseAdapter mouseAdapter = new MouseAdapter() {
                
                @Override
                public void mousePressed(MouseEvent me) {
                    panel.makeActionExclusive(ScrollByDraggingActionPanel.this);
                    panel.repaint();
                }

                @Override
                public void mouseReleased(MouseEvent me) {
                    panel.exclusiveActionUseFinished();
                    panel.repaint();
                }
                
            };
            scrollPane.getHorizontalScrollBar().addMouseListener(mouseAdapter);
            scrollPane.getVerticalScrollBar().addMouseListener(mouseAdapter);
        }
    }

    @Override
    public void mousePressed(MouseEvent e, int px, int py) {
        if ((e.getModifiersEx() & MouseEvent.BUTTON3_DOWN_MASK) == 0) {
            return;
        }
        if (panel.getParent() instanceof JViewport) {
            JViewport viewPort = (JViewport) panel.getParent();
            ScrollPane scrollPane = (ScrollPane) viewPort.getParent();
            int vx = scrollPane.getHorizontalScrollBar().getValue();
            int vy = scrollPane.getVerticalScrollBar().getValue();
            viewStartPosition.setLocation(vx, vy);
            mouseStartPosition.setLocation(e.getXOnScreen(), e.getYOnScreen());
            panel.setCursor(BasePanel.moveCursor);
            panel.makeActionExclusive(this);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e, int px, int py) {
        if ((e.getModifiersEx() & MouseEvent.BUTTON3_DOWN_MASK) == 0) {
            return;
        }
        if (panel.getParent() instanceof JViewport) {
            JViewport viewPort = (JViewport) panel.getParent();
            ScrollPane scrollPane = (ScrollPane) viewPort.getParent();
            int dx = mouseStartPosition.x - e.getXOnScreen();
            int dy = mouseStartPosition.y - e.getYOnScreen();
            scrollPane.getHorizontalScrollBar().setValue(viewStartPosition.x + dx);
            scrollPane.getVerticalScrollBar().setValue(viewStartPosition.y + dy);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e, int px, int py) {
        panel.setCursor(BasePanel.defaultCursor);
        panel.exclusiveActionUseFinished();
    }
    
}
