package br.ol.ge;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;

/**
 *
 * @author leonardo
 */
public class Mouse extends MouseAdapter {

    private Display display;
    public static double x;
    public static double y;
    public static boolean pressed;
    public static boolean pressedConsumed;

    public static boolean rightPressed;

    public Mouse(Display display) {
        this.display = display;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        x = e.getX() / 0.275;
        y = e.getY() / 0.275;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        x = e.getX() / 0.275;
        y = e.getY() / 0.275;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            pressed = true;
            pressedConsumed = false;
        }
        else if (SwingUtilities.isRightMouseButton(e)) {
            rightPressed = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            pressed = false;
        }
        else if (SwingUtilities.isRightMouseButton(e)) {
            rightPressed = false;
        }
    }
    
}
