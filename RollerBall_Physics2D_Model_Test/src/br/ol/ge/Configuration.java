package br.ol.ge;

/**
 *
 * @author leonardo
 */
public class Configuration {

    private final String title;
    private final int screenWidth;
    private final int screenHeight;
    private final double scaleWidth;
    private final double scaleHeight;
    private final int frameRate; // desired fps

    public Configuration(String title, int screenWidth, int screenHeight, double scaleWidth, double scaleHeight, int frameRate) {
        this.title = title;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.scaleWidth = scaleWidth;
        this.scaleHeight = scaleHeight;
        this.frameRate = frameRate;
    }

    public String getTitle() {
        return title;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public double getScaleWidth() {
        return scaleWidth;
    }

    public double getScaleHeight() {
        return scaleHeight;
    }

    public int getFrameRate() {
        return frameRate;
    }
    
}
