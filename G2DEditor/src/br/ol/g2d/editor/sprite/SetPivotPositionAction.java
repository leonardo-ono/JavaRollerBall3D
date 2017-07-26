package br.ol.g2d.editor.sprite;

import br.ol.g2d.G2DContext;
import br.ol.ui.model.ActionPanel;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

    
/**
 * SetPivotPositionAction class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class SetPivotPositionAction extends ActionPanel<SpritePanel>  {

    protected G2DContext context;
    protected Point pivotPosition = new Point();
    
    public SetPivotPositionAction(SpritePanel panel, G2DContext context) {
        super(panel);
        this.context = context;
    }
    
    @Override
    public boolean isActivated() {
        return context != null && context.getSpriteSheet() != null 
                && context.getSelectedSprite() != null;
    }

    @Override
    public void mouseMoved(MouseEvent e, int px, int py) {
        pivotPosition.setLocation(px, py);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_P) {
            context.getSelectedSprite().setPivotX(pivotPosition.x);
            context.getSelectedSprite().setPivotY(pivotPosition.y);
            context.update();
        }
    }

}
