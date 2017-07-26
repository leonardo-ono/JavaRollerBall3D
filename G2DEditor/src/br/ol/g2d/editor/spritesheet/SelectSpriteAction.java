package br.ol.g2d.editor.spritesheet;

import br.ol.g2d.G2DContext;
import br.ol.g2d.Sprite;
import br.ol.ui.model.ActionPanel;
import br.ol.ui.view.ScrollPane;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.JViewport;

    
/**
 * SelectSpriteAction class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class SelectSpriteAction extends ActionPanel<SpriteSheetPanel>  {

    protected G2DContext context;

    public SelectSpriteAction(SpriteSheetPanel panel, G2DContext context) {
        super(panel);
        this.context = context;
    }
    
    @Override
    public boolean isActivated() {
        return context != null && context.getSpriteSheet() != null;
    }

    @Override
    public void mousePressed(MouseEvent e, int px, int py) {
        if ((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) == 0) {
            return;
        }
        Sprite selectedSprite = context.getSpriteSheet().getSprite(px, py);
        if (selectedSprite != null) {
            context.getData().getSpriteSheet().setSelectedSprite(selectedSprite);
            context.update();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_NUMPAD4) {
            selectSpriteAtDirection(-1, 0);
        }
        else if (e.getKeyCode() == KeyEvent.VK_NUMPAD6) {
            selectSpriteAtDirection(1, 0);
        }

        if (e.getKeyCode() == KeyEvent.VK_NUMPAD8) {
            selectSpriteAtDirection(0, -1);
        }
        else if (e.getKeyCode() == KeyEvent.VK_NUMPAD2) {
            selectSpriteAtDirection(0, 1);
        }
    }
    
    private final Rectangle panelRectangle = new Rectangle();
    private final Rectangle selectedRectangle = new Rectangle();
    private void selectSpriteAtDirection(int dx, int dy) {
        Sprite selectedSprite = context.getSelectedSprite();
        if (selectedSprite == null) {
            return;
        }
        panelRectangle.setBounds(0, 0, panel.getWidth(), panel.getHeight());
        selectedRectangle.setBounds(selectedSprite.getRectangle());
        Sprite nextSprite = null;
        outer:
        do {
            for (Sprite sprite : context.getSpriteSheet().getSprites()) {
                if (sprite != selectedSprite && sprite.getRectangle().intersects(selectedRectangle)) {
                    nextSprite = sprite;
                    break outer;
                }
            }
            selectedRectangle.x += dx * selectedRectangle.width; 
            selectedRectangle.y += dy * selectedRectangle.height; 
        }
        while (panelRectangle.intersects(selectedRectangle));
        if (nextSprite != null) {
            context.getSpriteSheet().setSelectedSprite(nextSprite);
            // ensureSpriteVisible(nextSprite);
            context.update();
        }
    }
    
    // TODO not working
    private void ensureSpriteVisible(Sprite sprite) {
        if (panel.getParent() instanceof JViewport) {
            JViewport viewPort = (JViewport) panel.getParent();
            int rx = sprite.getRectangle().x * panel.getScale();
            int ry = sprite.getRectangle().y * panel.getScale();
            int rw = sprite.getRectangle().width * panel.getScale();
            int rh = sprite.getRectangle().height * panel.getScale();
            panelRectangle.setRect(rx, ry, rw, rh);
            viewPort.scrollRectToVisible(panelRectangle);
            viewPort.repaint();
        }
    }
    
}
