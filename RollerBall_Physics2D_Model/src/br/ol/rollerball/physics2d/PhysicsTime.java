package br.ol.rollerball.physics2d;

/**
 * PhysicsTime class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class PhysicsTime {
    
    private static boolean started = false;
    
    private static long current; 
    private static long last;
    static long delta;

    private static int frameRate = 45; // desired fps
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
        PhysicsTime.frameRate = frameRate;
        frameRateTime = 1000000000 / frameRate;
    }

    public static long getFrameRateTime() {
        return frameRateTime;
    }

    public static int getUpdatesCount() {
        return updatesCount;
    }

    public static void decUpdatesCount() {
        updatesCount--;
    }

    public static int getFps() {
        return fps;
    }
    
    private static void start() {
        current = last = System.nanoTime();
        fpsAccumulatedTime = updateAccumulatedTime = 0;
        updatesCount = fps = fpsCount = 0;
        delta = frameRateTime;
    }
    
    public static void update() {
        if (!started) {
            start();
            started = true;
        }
        
        current = System.nanoTime();
        long deltaInternal = current - last;
        last = current;
        // update
        updateAccumulatedTime += deltaInternal;
        while (updateAccumulatedTime > frameRateTime) {
            updateAccumulatedTime -= frameRateTime;
            updatesCount++;
            fpsCount++;
        }
        // fps
        fpsAccumulatedTime += deltaInternal;
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
                Thread.sleep(1);
            } catch (InterruptedException ex) {
            }
        }
    }
    
}
