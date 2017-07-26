package br.ol.g2d.editor.palette;

import br.ol.g2d.G2DContext;
import br.ol.g2d.Palette;
import br.ol.g2d.PaletteColor;
import br.ol.ui.model.Tool;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

    
/**
 * MovePaletteColorPositionTool class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class MovePaletteColorPositionTool extends Tool<PalettePanel>  {

    protected G2DContext context;
    private PaletteColor draggingPaletteColor;
    private int originalDraggingPaletteColorIndex;
    
    private int dx, dy;
    
    public MovePaletteColorPositionTool(PalettePanel panel, G2DContext context) {
        super(panel);
        this.context = context;
        this.name = "move palette color";
    }

    @Override
    public int getActivationKeycode() {
        return KeyEvent.VK_M;
    }
    
    @Override
    public boolean isActivated() {
        return context != null && context.getPalette() != null;
    }

    @Override
    public void mousePressed(MouseEvent e, int px, int py) {
        if ((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) == 0) {
            return;
        }
        draggingPaletteColor = context.getPalette().getColorByPosition(px, py);
        if (draggingPaletteColor != null && draggingPaletteColor != context.getPalette().getSelectedColor()) {
            draggingPaletteColor = null;
        }
        else {
            dx = px - draggingPaletteColor.getRectangle().x;
            dy = py - draggingPaletteColor.getRectangle().y;
            originalDraggingPaletteColorIndex = context.getPalette().getColors().indexOf(draggingPaletteColor);
            context.getPalette().removeColor(draggingPaletteColor);
            persistent = true;
            panel.repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e, int px, int py) {
        if ((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) == 0) {
            return;
        }
        if (draggingPaletteColor == null) {
            mousePressed(e, px, py);
        }
        if (draggingPaletteColor != null) {
            draggingPaletteColor.getRectangle().setLocation(px - dx, py - dy);
            panel.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e, int px, int py) {
        if (draggingPaletteColor != null) {
            Palette palette = context.getPalette();
            PaletteColor closeColor = palette.getColorByPosition(px, py);
            if (closeColor == null) {
                palette.addColor(draggingPaletteColor);
            }
            else {
                int insertIndex = palette.getColors().lastIndexOf(closeColor);
                if (px - closeColor.getRectangle().x < closeColor.getRectangle().width / 2) {
                    palette.getColors().add(insertIndex, draggingPaletteColor);
                }
                else {
                    palette.getColors().add(insertIndex + 1, draggingPaletteColor);
                }
            }
            draggingPaletteColor = null;
            persistent = false;
            context.update();
        }
    }

    @Override
    public void drawScaled(Graphics2D g) {
        if (draggingPaletteColor != null) {
            draggingPaletteColor.draw(g);
        }
    }

    @Override
    public void cancel() {
        if (draggingPaletteColor != null) {
            context.getPalette().getColors().add(originalDraggingPaletteColorIndex, draggingPaletteColor);
            draggingPaletteColor = null;
            originalDraggingPaletteColorIndex = 0;
            context.update();
        }
    }
    
}
