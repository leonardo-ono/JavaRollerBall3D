package br.ol.ui.model;

import br.ol.ui.view.BasePanel;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/**
 * Tool class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class Tool<T extends BasePanel> {

    protected T panel;
    protected String name;
    protected int activationKeycode;
    protected boolean persistent;
    
    public Tool(T panel) {
        this.panel = panel;
    }
    public void init() {
        
        
    }
    public String getName() {
        return name;
    }

    public int getActivationKeycode() {
        return activationKeycode;
    }
    
    public boolean isActivated() {
        return true;
    }
    
    // if true, allow to change to another tool just with 'ESC' or panel.toolUseFinished()
    public boolean isPersistent() {
        return persistent;
    }

    public void start() {
    }
    
    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void mouseClicked(MouseEvent e, int px, int py) {
    }

    public void mousePressed(MouseEvent e, int px, int py) {
    }

    public void mouseReleased(MouseEvent e, int px, int py) {
    }

    public void mouseEntered(MouseEvent e, int px, int py) {
    }

    public void mouseExited(MouseEvent e, int px, int py) {
    }

    public void mouseDragged(MouseEvent e, int px, int py) {
    }

    public void mouseMoved(MouseEvent e, int px, int py) {
    }

    public void mouseWheelMoved(MouseWheelEvent e, int px, int py) {
    }
    
    public void cancel() {
    }

    public void draw(Graphics2D g2d) {
    }
    
    public void drawScaled(Graphics2D g) {
    }
    
    public void exclusiveActionStarted(ActionPanel action) {
    }

    public void exclusiveActionFinished(ActionPanel action) {
    }
    
}
