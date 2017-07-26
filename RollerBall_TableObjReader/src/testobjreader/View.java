/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testobjreader;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author leonardo
 */
public class View extends JPanel {
    
    private TestObjReader or = new TestObjReader();

    public View() {
        try {
            or.read();
        } catch (Exception ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        }
        MouseHandler mouseHandler = new MouseHandler();
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
        addKeyListener(new KeyHandler());
    }
            
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        or.draw((Graphics2D) g);
    }

    public class KeyHandler extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_C) {
                or.useClockwise = !or.useClockwise;
                repaint();
            }
            else if (e.getKeyCode() == KeyEvent.VK_X) {
                or.printSelectedPoint();
            }
        }
        
    }
    
    public class MouseHandler extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            if (SwingUtilities.isRightMouseButton(e)) {
                or.cameraPressed.setLocation(or.camera);
                or.mousePressed.setLocation(e.getX(), e.getY());
            }
            else if (SwingUtilities.isLeftMouseButton(e)) {
                or.generateCode(or.getSelectedPoint());
            }
            repaint();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (SwingUtilities.isRightMouseButton(e)) {
                int x = (int) (or.cameraPressed.getX() - (e.getX() - or.mousePressed.getX()));
                int y = (int) (or.cameraPressed.getY() - (e.getY() - or.mousePressed.getY()));
                or.camera.setLocation(x, y);
            }
            repaint();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            or.mouse.x = (int) (e.getX() + or.camera.getX());
            or.mouse.y = (int) (e.getY() + or.camera.getY());
            repaint();
        }
        
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                View view = new View();
                JFrame frame = new JFrame();
                frame.setTitle("Teste obj reader");
                frame.getContentPane().add(view);
                frame.setSize(800, 800);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                
                view.requestFocus();
                view.requestFocusInWindow();
            }
        });
    }
    
}
