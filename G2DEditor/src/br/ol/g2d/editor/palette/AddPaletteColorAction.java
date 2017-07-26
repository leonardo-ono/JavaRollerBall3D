package br.ol.g2d.editor.palette;

import br.ol.g2d.G2DContext;
import br.ol.g2d.Palette;
import br.ol.g2d.PaletteColor;
import br.ol.ui.model.ActionPanel;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.JColorChooser;

    
/**
 * AddPaletteColorAction class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class AddPaletteColorAction extends ActionPanel<PalettePanel>  {

    protected G2DContext context;
    private static final JColorChooser colorChooser = new JColorChooser();
    private final Point insertPosition = new Point();

    public AddPaletteColorAction(PalettePanel panel, G2DContext context) {
        super(panel);
        this.context = context;
    }
    
    @Override
    public boolean isActivated() {
        return context != null && context.getSelectedPaletteColor() != null;
    }

    @Override
    public void mouseMoved(MouseEvent e, int px, int py) {
        insertPosition.setLocation(px, py);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A) {
            Palette palette = context.getPalette();
            
            PaletteColor closeColor = palette.getColorByPosition(insertPosition.x, insertPosition.y);
            
            PaletteColor newColor = new PaletteColor(Color.WHITE, 0);
            if (context.getSelectedPaletteColor() != null) {
                newColor.setColor(context.getSelectedPaletteColor().getColor());
            }
            
            Color choosedColor = JColorChooser.showDialog(panel, "Add new color", newColor.getColor());
            if (choosedColor == null) {
                return;
            }
            newColor.setColor(choosedColor);
            
            if (closeColor == null) {
                palette.addColor(newColor);
            }
            else {
                int insertIndex = palette.getColors().lastIndexOf(closeColor);
                if (insertPosition.x - closeColor.getRectangle().x < closeColor.getRectangle().width / 2) {
                    palette.getColors().add(insertIndex, newColor);
                }
                else {
                    palette.getColors().add(insertIndex + 1, newColor);
                }
            }
            
            palette.setSelectedColor(newColor);
            context.update();
        }
    }
    
}
