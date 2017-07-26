package br.ol.rollerball.game.infra;

import br.ol.rollerball.renderer3d.core.Light;
import br.ol.rollerball.renderer3d.core.Renderer;
import static br.ol.rollerball.renderer3d.core.Renderer.MatrixMode.*;
import br.ol.rollerball.renderer3d.core.Shader;
import br.ol.rollerball.renderer3d.shader.GouraudShaderWithTexture;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

/**
 * Display class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class Display extends Canvas {
    
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;

    public static final int SCREEN_WIDTH = 420;
    public static final int SCREEN_HEIGHT = 315;
    
    public boolean running = false;
    public BufferStrategy bs;

    public Renderer renderer;
    public Thread thread;
    
    public Shader gouraudShader = new GouraudShaderWithTexture();
    public Scene scene;
    
    public double sx, sy;

    private final Font fpsFont = new Font("Arial", Font.PLAIN, 10);
    public final Light light = new Light();

    public Display(Scene scene) {
        setBackground(Color.BLACK);
        addKeyListener(new KeyHandler());
        scene.setDisplay(this);
        this.scene = scene;
    }
    
    public void init() throws Exception {
        MouseHandler mouseHandler = new MouseHandler();
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
        
        createBufferStrategy(1);
        bs = getBufferStrategy();
        renderer = new Renderer(SCREEN_WIDTH, SCREEN_HEIGHT);
        
        // light
        light.diffuse.set(1, 1, 1, 1);
        light.position.set(0, 20000, 0, 1);
        renderer.addLight(light);
        
        sx = SCREEN_WIDTH / (double) WINDOW_WIDTH;
        sy = SCREEN_HEIGHT / (double) WINDOW_HEIGHT;
        
        renderer.setShader(gouraudShader);
        
        renderer.setMatrixMode(PROJECTION);
        renderer.setPerspectiveProjection(Math.toRadians(60));
        
        renderer.setClipZNear(-1);
        renderer.setClipZfar(-15000);
        
        scene.init();
        
        running = true;
        thread = new Thread(new MainLoop());
        thread.start();
    }
    
    public void updatePhysics() {
        scene.updatePhysics();
    }
    
    public void update() {
        scene.update(renderer);
    }

    private void draw() {
        renderer.clearAllBuffers(); 
        scene.draw(renderer);
    }
    public void draw(Graphics2D g) {
        Graphics2D g2d = (Graphics2D) renderer.getColorBuffer().getColorBuffer().getGraphics();
        scene.draw(renderer, g2d);
        g2d.setFont(fpsFont);
        g2d.setXORMode(Color.BLACK);
        g2d.drawString("FPS: " + Time.fps, 5, SCREEN_HEIGHT - 20);
        g2d.setPaintMode();
        g2d.setColor(Color.WHITE);
        
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);        
        g2d.drawImage(renderer.getColorBuffer().getColorBuffer(), 0, 0, 400, 300, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
        
        //g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);        
        g.drawImage(renderer.getColorBuffer().getColorBuffer(), 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, 0, 0, 400, 300, null);
    }
    
    private class MainLoop implements Runnable {

        @Override
        public void run() {
            while (running) {
                Time.update();
                updatePhysics();
                update();
                draw();
                Graphics2D g = (Graphics2D) bs.getDrawGraphics();
                draw(g);
                g.dispose();
                bs.show();
                
                try {
                    Thread.sleep(1);
                    Thread.yield();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Display.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    private class MouseHandler extends MouseAdapter {

        @Override
        public void mouseMoved(MouseEvent e) {
            Mouse.x = (e.getX() - WINDOW_WIDTH * 0.5) * sx;
            Mouse.y = (WINDOW_HEIGHT * 0.5 - e.getY()) * sy;
            Mouse.sx = e.getX() * sx;
            Mouse.sy = e.getY() * sy;
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            Mouse.x = (e.getX() - WINDOW_WIDTH * 0.5) * sx;
            Mouse.y = (WINDOW_HEIGHT * 0.5 - e.getY()) * sy;
            Mouse.sx = e.getX() * sx;
            Mouse.sy = e.getY() * sy;
        }
        
        @Override
        public void mousePressed(MouseEvent me) {
            if (SwingUtilities.isLeftMouseButton(me)) {
                Mouse.pressed1 = true;
                Mouse.pressedConsumed1 = false;
            }
            else if (SwingUtilities.isRightMouseButton(me)) {
                Mouse.pressed2 = true;
                Mouse.pressedConsumed2 = false;
            }
        }

        @Override
        public void mouseReleased(MouseEvent me) {
            if (SwingUtilities.isLeftMouseButton(me)) {
                Mouse.pressed1 = false;
                Mouse.pressedConsumed1 = false;
            }
            else if (SwingUtilities.isRightMouseButton(me)) {
                Mouse.pressed2 = false;
                Mouse.pressedConsumed2 = false;
            }
        }
        
    }

    private class KeyHandler extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() > Keyboard.keyDown.length - 1) {
                return;
            }
            Keyboard.keyDown[e.getKeyCode()] = true;
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() > Keyboard.keyDown.length - 1) {
                return;
            }
            Keyboard.keyDown[e.getKeyCode()] = false;
            Keyboard.keyDownConsumed[e.getKeyCode()] = false;
        }
        
    }
    
}
