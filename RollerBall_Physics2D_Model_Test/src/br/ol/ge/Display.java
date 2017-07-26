package br.ol.ge;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

/**
 *
 * @author leonardo
 */
public class Display extends Canvas {
    
    private final Configuration configuration;
    private Scene scene;
    private boolean running;
    private BufferStrategy bs;
    
    public Display(Configuration configuration) {
        this.configuration = configuration;
        int screenWidth = (int) (configuration.getScreenWidth() * configuration.getScaleWidth());
        int screenHeight = (int) (configuration.getScreenHeight() * configuration.getScaleHeight());
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        Mouse mouse = new Mouse(this);
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
        addKeyListener(new Keyboard());
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
    
    public void start() {
        if (running) {
            return;
        }
        scene.internalInit();
        Time.setFrameRate(configuration.getFrameRate());
        Time.start();
        createBufferStrategy(3);
        bs = getBufferStrategy();
        running = true;
        Thread thread = new Thread(new MainLoop());
        thread.start();
    }
    
    private class MainLoop implements Runnable {

        @Override
        public void run() {
            boolean needsRender = false;
            while (running) {
                Time.update();
                while (Time.getUpdatesCount() > 0) {
                    Time.decUpdatesCount();
                    update();
                    needsRender = true;
                }
                if (needsRender) {
                    Graphics2D g = (Graphics2D) bs.getDrawGraphics();
                    g.scale(configuration.getScaleWidth(), configuration.getScaleHeight());
                    g.clearRect(0, 0, (int) (configuration.getScreenWidth() * configuration.getScaleWidth()), (int) (configuration.getScreenHeight() * configuration.getScaleHeight()));
                    draw(g);
                    g.dispose();
                    bs.show();
                    needsRender = false;
                }
                Time.sync();
            }
        }
        
    }
    
    public void update() {
        if (scene != null) {
            scene.internalUpdate();
        }
    }
    
    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.drawString("FPS: " + Time.getFps(), 10, 10);
        if (scene != null) {
            scene.draw(g);
        }
    }
    
}
