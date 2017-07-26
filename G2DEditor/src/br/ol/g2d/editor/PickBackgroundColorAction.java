package br.ol.g2d.editor;

import br.ol.g2d.G2DContext;
import br.ol.ui.model.ActionPanel;
import br.ol.ui.view.BasePanel;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * PickBackgroundColorAction class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class PickBackgroundColorAction extends ActionPanel<BasePanel> {

    protected G2DContext context;
    protected Robot robot;
    protected Point mousePosition = new Point();
    
    public PickBackgroundColorAction(BasePanel panel, G2DContext context) {
        super(panel);
        this.context = context;
    }

    @Override
    public void init() {
        try {
            robot = new Robot();
        } catch (AWTException ex) {
        }
        
    }
    
    @Override
    public boolean isActivated() {
        return robot != null && context.getData() != null && context.getPalette() != null;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_B && !e.isActionKey() && !e.isAltDown() && !e.isAltGraphDown() && !e.isControlDown() && !e.isMetaDown() && !e.isShiftDown()) {
            pickBackgroundColor();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e, int px, int py) {
        mousePosition.setLocation(px, py);
    }

    public void pickBackgroundColor() {
        Color pickedColor = panel.getPixelColor(mousePosition.x, mousePosition.y);
        context.getSpriteSheet().setBackgroundColor(pickedColor);
        panel.toolUseFinished();
        context.update();
    }

}
