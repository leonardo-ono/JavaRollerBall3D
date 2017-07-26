package br.ol.g2d.editor.sprite;

import br.ol.g2d.G2DContext;
import br.ol.g2d.Sprite;
import br.ol.ui.model.ActionPanel;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

    
/**
 * ShowHideGridAction class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class ShowHideGridAction extends ActionPanel<SpritePanel>  {

    protected G2DContext context;
    private boolean gridVisible = true;

    public ShowHideGridAction(SpritePanel panel, G2DContext context) {
        super(panel);
        this.context = context;
    }
    
    @Override
    public boolean isActivated() {
        return context != null && context.getSelectedSprite() != null;
    }
    
    public boolean isGridVisible() {
        return gridVisible;
    }

    public void setGridVisible(boolean gridVisible) {
        this.gridVisible = gridVisible;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_G) {
            gridVisible = !gridVisible;
            panel.repaint();
        }
    }
    
    @Override
    public void draw(Graphics2D g) {
        if (gridVisible && panel.getScale() > 2) {
            drawGrid(g);
        }
    }

    private void drawGrid(Graphics2D g) {
        Sprite sprite = context.getData().getSpriteSheet().getSelectedSprite();
        for (int x = 0; x <= sprite.getRectangle().getWidth(); x++) {
            g.drawLine(x * panel.getScale(), 0, x * panel.getScale()
                    , (int) sprite.getRectangle().getHeight() * panel.getScale());
        }
        for (int y = 0; y <= sprite.getRectangle().getHeight(); y++) {
            g.drawLine(0, y * panel.getScale()
                    , (int) sprite.getRectangle().getWidth() * panel.getScale(), y * panel.getScale());
        }
    }
        
}
