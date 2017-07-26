package br.ol.ge;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Keyboard class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class Keyboard extends KeyAdapter {

    private static boolean[] keyDown = new boolean[256];
    private static boolean[] keyDownConsumed = new boolean[256];
    
    public static boolean isKeyPressed(int keyCode) {
        if (keyDown[keyCode] && !keyDownConsumed[keyCode]) {
            keyDownConsumed[keyCode] = true;
            return true;
        }
        return false;
    }

    public static boolean isKeyDown(int keyCode) {
        return keyDown[keyCode];
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() > keyDown.length - 1) {
            return;
        }
        keyDown[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() > keyDown.length - 1) {
            return;
        }
        keyDown[e.getKeyCode()] = false;
        keyDownConsumed[e.getKeyCode()] = false;
    }
    
}
