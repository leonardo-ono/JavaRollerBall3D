package br.ol.g2d.editor;

import br.ol.g2d.G2DContext;
import br.ol.g2d.Palette;
import br.ol.g2d.PaletteColor;
import br.ol.ui.model.ActionPanel;
import br.ol.ui.view.BasePanel;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * SelectBackgroundColorTool class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class PickColorAction extends ActionPanel<BasePanel> {

    protected G2DContext context;
    protected Robot robot;
    private Point mousePosition = new Point();

    public PickColorAction(BasePanel panel, G2DContext context) {
        super(panel);
        this.context = context;
    }

    @Override
    public void init() {
        try {
            
            robot = new Robot(panel.getGraphicsConfiguration().getDevice());
        } catch (AWTException ex) {
        }
        
    }
    
    @Override
    public boolean isActivated() {
        return robot != null && context.getData() != null && context.getPalette() != null;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_C && !e.isActionKey() && !e.isAltDown() && !e.isAltGraphDown() && !e.isControlDown() && !e.isMetaDown() && !e.isShiftDown()) {
            pickColor();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e, int px, int py) {
        mousePosition.setLocation(px, py);
    }

    public void pickColor() {
        Color pickedColor = panel.getPixelColor(mousePosition.x, mousePosition.y);
        Palette palette = context.getPalette();
        PaletteColor pickedPaletteColor = palette.getColorByRGB(pickedColor.getRGB());
        if (pickedPaletteColor != null) {
            palette.setSelectedColor(pickedPaletteColor);
        }
        else {
            palette.selectNotInPaletteColor(pickedColor);
        }
        panel.toolUseFinished();
        context.update();
    }

}
