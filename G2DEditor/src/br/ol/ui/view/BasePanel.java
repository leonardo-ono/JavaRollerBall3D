package br.ol.ui.view;

import br.ol.ui.model.ActionPanel;
import br.ol.ui.model.ScrollByDraggingActionPanel;
import br.ol.ui.model.ShowCrossCursorAction;
import br.ol.ui.model.ShowToolInfoAction;
import br.ol.ui.model.Tool;
import br.ol.ui.model.ZoomActionPanel;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.dnd.DragSource;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * BasePanel class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class BasePanel extends JPanel {

    public static final Cursor defaultCursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
    public static final Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
    public static final Cursor moveCursor = Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);
    public static final Cursor crossCursor = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
    public static final Cursor dragCursor = DragSource.DefaultMoveDrop;
    
    protected Dimension desiredSize = new Dimension(500, 500);
    protected Dimension scaledSize = new Dimension(500, 500);
    protected int scale = 1;
    protected Map<Integer, Tool> tools = new HashMap<Integer, Tool>();
    protected List<ActionPanel> actions = new ArrayList<ActionPanel>();
    protected Tool selectedTool;
    protected ActionPanel exclusiveAction;
    protected AffineTransform originalTranform;

    private static class BlinkHandler extends TimerTask {
        @Override
        public void run() {
            for (BasePanel panel : registeredBlinkPanels) {
                panel.updateBlink(blinking);
            }
            blinking = !blinking;
            if (!registeredBlinkPanelsAdd.isEmpty()) {
                registeredBlinkPanels.addAll(registeredBlinkPanelsAdd);
                registeredBlinkPanelsAdd.clear();
            }
        }
    }
    private static boolean blinking;
    private static final List<BasePanel> registeredBlinkPanels = new ArrayList<BasePanel>();
    private static final List<BasePanel> registeredBlinkPanelsAdd = new ArrayList<BasePanel>();
    
    static {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new BlinkHandler(), 100, 250);
    }
    
    public BasePanel() {
        setFocusable(true);
        updatePreferredSize();
    }
    
    public void init() {
        KeyHandler keyHandler = new KeyHandler();
        addKeyListener(keyHandler);
        MouseHandler mouseHandler = new MouseHandler();
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
        addMouseWheelListener(mouseHandler);
        installActions();
        installTools();
        installDefaultActions();
        initActions();
        initTools();
    }
    
    private void installDefaultActions() {
        addAction(new ZoomActionPanel(this));
        addAction(new ScrollByDraggingActionPanel(this));
        addAction(new ShowToolInfoAction(this));
        addAction(new ShowCrossCursorAction(this));
    }
    
    public void installActions() {
    }

    public void installTools() {
    }
    
    public void initActions() {
        for (ActionPanel action : actions) {
            action.init();
        }
    }
    
    public void initTools() {
        for (Tool tool : tools.values()) {
            tool.init();
        }
    }
    
    public Dimension getDesiredSize() {
        return desiredSize;
    }

    public void setDesiredSizeInPixels(int desiredWidth, int desiredHeight) {
        if (desiredSize.width != desiredWidth || desiredSize.height != desiredHeight) {
            desiredSize.setSize(desiredWidth, desiredHeight);
            updatePreferredSize();
            revalidate();
        }
    }

    public Dimension getScaledSize() {
        scaledSize.setSize(desiredSize.width * scale, desiredSize.height * scale);
        return scaledSize;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        if (scale < 1) {
            scale = 1;
        }
        if (this.scale != scale) {
            this.scale = scale;
        }
    }

    public void updatePreferredSize() {
        setPreferredSize(getScaledSize());
    }
    
    public List<ActionPanel> getActions() {
        return actions;
    }
    
    public void addAction(ActionPanel action) {
        actions.add(action);
    }

    public Map<Integer, Tool> getTools() {
        return tools;
    }

    public void addTool(Tool tool) {
        tools.put(tool.getActivationKeycode(), tool);
    }

    public Tool getSelectedTool() {
        return selectedTool;
    }

    public ActionPanel getExclusiveAction() {
        return exclusiveAction;
    }

    public int getMousePixelX(MouseEvent me) {
        return me.getX() / scale;
    }

    public int getMousePixelY(MouseEvent me) {
        return me.getY() / scale;
    }

    public AffineTransform getOriginalTranform() {
        return originalTranform;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        originalTranform = g2d.getTransform();
        g2d.scale(scale, scale);
        if (canDraw()) {
            drawScaled(g2d);
            AffineTransform at = g2d.getTransform();
            g2d.setTransform(originalTranform);
            draw(g2d);
            g2d.setTransform(at);
            for (Tool tool : tools.values()) {
                if (tool.isActivated()) {
                    tool.drawScaled(g2d);
                    at = g2d.getTransform();
                    g2d.setTransform(originalTranform);
                    tool.draw(g2d);
                    g2d.setTransform(at);
                }
            }
            for (ActionPanel action : actions) {
                if (action.isActivated()) {
                    action.drawScaled(g2d);
                    at = g2d.getTransform();
                    g2d.setTransform(originalTranform);
                    action.draw(g2d);
                    g2d.setTransform(at);
                }
            }
        }
    }

    public boolean canDraw() {
        return true;
    }
    
    public void draw(Graphics2D g) {
        //g.drawLine(0, 0, scaledSize.width, scaledSize.height);
        //g.drawLine(scaledSize.width, 0, 0, scaledSize.height);
    }
    
    public void drawScaled(Graphics2D g) {
        //g.drawLine(0, 0, desiredSize.width, desiredSize.height);
        //g.drawLine(desiredSize.width, 0, 0, desiredSize.height);
    }
    
    private class MouseHandler extends MouseAdapter {

        @Override
        public void mouseEntered(MouseEvent e) {
            requestFocusInWindow();
            int mpx = getMousePixelX(e);
            int mpy = getMousePixelY(e);
            if (exclusiveAction != null && exclusiveAction.isActivated()) {
                exclusiveAction.mouseEntered(e, mpx, mpy);
                return;
            }
            exclusiveAction = null;
            if (selectedTool != null && selectedTool.isActivated()) {
                selectedTool.mouseEntered(e, mpx, mpy);
            }
            for (ActionPanel action : actions) { 
                if (action.isActivated()) {
                    action.mouseEntered(e, mpx,mpy);
                }
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            int mpx = getMousePixelX(e);
            int mpy = getMousePixelY(e);
            if (exclusiveAction != null && exclusiveAction.isActivated()) {
                exclusiveAction.mouseExited(e, mpx, mpy);
                return;
            }
            exclusiveAction = null;
            if (selectedTool != null && selectedTool.isActivated()) {
                selectedTool.mouseExited(e, mpx, mpy);
            }
            for (ActionPanel action : actions) { 
                if (action.isActivated()) {
                    action.mouseExited(e, mpx, mpy);
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            int mpx = getMousePixelX(e);
            int mpy = getMousePixelY(e);
            if (exclusiveAction != null && exclusiveAction.isActivated()) {
                exclusiveAction.mousePressed(e, mpx, mpy);
                return;
            }
            exclusiveAction = null;
            if (selectedTool != null && selectedTool.isActivated()) {
                selectedTool.mousePressed(e, mpx, mpy);
            }
            for (ActionPanel action : actions) { 
                if (action.isActivated()) {
                    action.mousePressed(e, mpx, mpy);
                }
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            int mpx = getMousePixelX(e);
            int mpy = getMousePixelY(e);
            if (exclusiveAction != null && exclusiveAction.isActivated()) {
                exclusiveAction.mouseClicked(e, mpx, mpy);
                return;
            }
            exclusiveAction = null;
            if (selectedTool != null && selectedTool.isActivated()) {
                selectedTool.mouseClicked(e, mpx, mpy);
            }
            for (ActionPanel action : actions) { 
                if (action.isActivated()) {
                    action.mouseClicked(e, mpx, mpy);
                }
            }
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            int mpx = getMousePixelX(e);
            int mpy = getMousePixelY(e);
            if (exclusiveAction != null && exclusiveAction.isActivated()) {
                exclusiveAction.mouseWheelMoved(e, mpx, mpy);
                return;
            }
            exclusiveAction = null;
            if (selectedTool != null && selectedTool.isActivated()) {
                selectedTool.mouseWheelMoved(e, mpx, mpy);
            }
            for (ActionPanel action : actions) { 
                if (action.isActivated()) {
                    action.mouseWheelMoved(e, mpx, mpy);
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            int mpx = getMousePixelX(e);
            int mpy = getMousePixelY(e);
            if (exclusiveAction != null && exclusiveAction.isActivated()) {
                exclusiveAction.mouseReleased(e, mpx, mpy);
                return;
            }
            exclusiveAction = null;
            if (selectedTool != null && selectedTool.isActivated()) {
                selectedTool.mouseReleased(e, mpx, mpy);
            }
            for (ActionPanel action : actions) { 
                if (action.isActivated()) {
                    action.mouseReleased(e, mpx, mpy);
                }
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            int mpx = getMousePixelX(e);
            int mpy = getMousePixelY(e);
            if (exclusiveAction != null && exclusiveAction.isActivated()) {
                exclusiveAction.mouseDragged(e, mpx, mpy);
                return;
            }
            exclusiveAction = null;
            if (selectedTool != null && selectedTool.isActivated()) {
                selectedTool.mouseDragged(e, mpx, mpy);
            }
            for (ActionPanel action : actions) { 
                if (action.isActivated()) {
                    action.mouseDragged(e, mpx, mpy);
                }
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            int mpx = getMousePixelX(e);
            int mpy = getMousePixelY(e);
            if (exclusiveAction != null && exclusiveAction.isActivated()) {
                exclusiveAction.mouseMoved(e, mpx, mpy);
                return;
            }
            exclusiveAction = null;
            if (selectedTool != null && selectedTool.isActivated()) {
                selectedTool.mouseMoved(e, mpx, mpy);
            }
            for (ActionPanel action : actions) { 
                if (action.isActivated()) {
                    action.mouseMoved(e, mpx, mpy);
                }
            }
        }
        
    }

    private class KeyHandler extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            if (exclusiveAction != null && exclusiveAction.isActivated()) {
                exclusiveAction.keyPressed(e);
                return;
            }
            exclusiveAction = null;
            
            Tool nTool = null;
            
            if (!e.isActionKey() && !e.isAltDown() && !e.isAltGraphDown() && !e.isControlDown() && !e.isMetaDown() && !e.isShiftDown()) {
                nTool = tools.get(e.getKeyCode());
            }
            
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE && selectedTool != null) {
                selectedTool.cancel();
                selectedTool = null;
                repaint();
            }
            
            if ((selectedTool != null && !selectedTool.isPersistent() && nTool != null && nTool.isActivated() && nTool != selectedTool)) {
                selectedTool.cancel();
                selectedTool = nTool;
                selectedTool.start();
                repaint();
            }
            else if (selectedTool != null && selectedTool.isActivated()) {
                selectedTool.keyPressed(e);
            }
            else {
                selectedTool = nTool;
                if (selectedTool != null && !selectedTool.isActivated()) {
                    selectedTool = null;
                }
                else if (selectedTool != null){
                    selectedTool.start();
                    repaint();
                }
            }
            
            for (ActionPanel action : actions) { 
                if (action.isActivated()) {
                    action.keyPressed(e);
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (exclusiveAction != null && exclusiveAction.isActivated()) {
                exclusiveAction.keyReleased(e);
                return;
            }
            exclusiveAction = null;
            if (selectedTool != null && selectedTool.isActivated()) {
                selectedTool.keyReleased(e);
            }
            for (ActionPanel action : actions) { 
                if (action.isActivated()) {
                    action.keyReleased(e);
                }
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {
            if (exclusiveAction != null && exclusiveAction.isActivated()) {
                exclusiveAction.keyTyped(e);
                return;
            }
            exclusiveAction = null;
            if (selectedTool != null && selectedTool.isActivated()) {
                selectedTool.keyTyped(e);
            }
            for (ActionPanel action : actions) { 
                if (action.isActivated()) {
                    action.keyTyped(e);
                }
            }
        }
        
    }
    
    public void makeActionExclusive(ActionPanel actionPanel) {
        exclusiveAction = actionPanel;
        for (Tool tool : tools.values()) {
            tool.exclusiveActionStarted(exclusiveAction);
        }
        for (ActionPanel action : actions) { 
            action.exclusiveActionStarted(exclusiveAction);
        }
    }
    
    public void exclusiveActionUseFinished() {
        for (Tool tool : tools.values()) {
            tool.exclusiveActionFinished(exclusiveAction);
        }
        for (ActionPanel action : actions) { 
            action.exclusiveActionFinished(exclusiveAction);
        }
        exclusiveAction = null;
    }

    public void toolUseFinished() {
        selectedTool = null;
    }
 
    public void showError(String message, Throwable exception) {
        String exceptionMessage = "";
        if (exception != null) {
            exceptionMessage = "\n\nException: " + exception.getMessage();
        }
        JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(this), message + exceptionMessage, "Error", JOptionPane.ERROR_MESSAGE);        
    }

    public void showInfo(String message) {
        JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(this), message, "Info", JOptionPane.INFORMATION_MESSAGE);        
    }
 
    protected void registerBlink() {
        if (!registeredBlinkPanels.contains(this) && !registeredBlinkPanelsAdd.contains(this)) {
            registeredBlinkPanelsAdd.add(this);
        }
    }
    
    public boolean isBlinking() {
        return blinking;
    }
    
    public void updateBlink(boolean blinking) {
    }
    
    public Color getPixelColor(int x, int y) {
        return Color.BLACK;
    }
    

    
}
