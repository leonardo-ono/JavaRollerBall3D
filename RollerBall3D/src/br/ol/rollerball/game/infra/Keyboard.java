package br.ol.rollerball.game.infra;

/**
 *
 * @author leonardo
 */
public class Keyboard {
 
    public static boolean[] keyDown = new boolean[256];
    public static boolean[] keyDownConsumed = new boolean[256];
    
    public static boolean isKeyDown(int keyCode) {
        return keyDown[keyCode];
    }

    public static boolean isKeyPressed(int keyCode) {
        if (isKeyDown(keyCode) && !keyDownConsumed[keyCode]) {
            keyDownConsumed[keyCode] = true;
            return true;
        }
        return false;
    }
    
}
