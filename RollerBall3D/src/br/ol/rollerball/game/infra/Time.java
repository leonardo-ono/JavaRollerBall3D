package br.ol.rollerball.game.infra;

/**
 *
 * @author leonardo
 */
public class Time {

    public static long delta = 0;
    public static int fps = 0;
    
    private static int fpsCount;
    private static long fpsTime;
    private static long lastTime;
    
    private static boolean started;
    
    public static void update() {
        long currentTime = System.nanoTime();
        if (!started) {
            lastTime = currentTime;
            started = true;
        }
        delta = currentTime - lastTime;
        fpsTime += delta;
        if (fpsTime > 1000000000) {
            fps = fpsCount;
            fpsTime = fpsCount = 0;
        }
        else {
            fpsCount++;
        }
        lastTime = currentTime;
    }
    
}
