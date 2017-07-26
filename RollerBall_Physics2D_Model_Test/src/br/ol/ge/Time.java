package br.ol.ge;

/**
 *
 * @author leonardo
 */
public class Time {

    private static long current; 
    private static long last;
    public static long delta;

    private static int frameRate = 60; // desired fps
    private static long frameRateTime = 1000000000 / frameRate;
    private static long updateAccumulatedTime;
    private static int updatesCount;
    
    private static long fpsAccumulatedTime;
    private static int fpsCount;
    private static int fps;

    public static long getCurrent() {
        return current;
    }

    public static long getDelta() {
        return delta;
    }

    public static int getFrameRate() {
        return frameRate;
    }

    public static void setFrameRate(int frameRate) {
        Time.frameRate = frameRate;
        frameRateTime = 1000000000 / frameRate;
    }

    public static long getFrameRateTime() {
        return frameRateTime;
    }

    static int getUpdatesCount() {
        return updatesCount;
    }

    static void decUpdatesCount() {
        updatesCount--;
    }

    public static int getFps() {
        return fps;
    }
    
    static void start() {
        current = last = System.nanoTime();
        delta = fpsAccumulatedTime = updateAccumulatedTime = 0;
        updatesCount = fps = fpsCount = 0;
    }
    
    static void update() {
        current = System.nanoTime();
        delta = current - last;
        last = current;
        // update
        updateAccumulatedTime += delta;
        while (updateAccumulatedTime > frameRateTime) {
            updateAccumulatedTime -= frameRateTime;
            updatesCount++;
            fpsCount++;
        }
        // fps
        fpsAccumulatedTime += delta;
        if (fpsAccumulatedTime > 1000000000) {
            fpsAccumulatedTime -= 1000000000;
            fps = fpsCount;
            fpsCount = 0;
        }
    }
    
    static void sync() {
        while (System.nanoTime() - current < frameRateTime) {
            try {
                Thread.yield();
                Thread.sleep(0);
            } catch (InterruptedException ex) {
            }
        }
    }
    
}
