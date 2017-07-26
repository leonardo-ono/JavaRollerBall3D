package br.ol.g2d.editor.spritesheet;

import br.ol.g2d.G2DContext;
import br.ol.g2d.Sprite;
import br.ol.g2d.SpriteSheet;
import br.ol.ui.model.Tool;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * NewGridSpritesTool class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class NewGridSpritesTool extends Tool<SpriteSheetPanel> {

    private G2DContext context;
    //private NewGridSpritesToolDialog newGridSpritesToolDialog;
    private Point startPosition = new Point();
    private int cols = 16;
    private int rows = 16;
    private int spriteWidth = 8;
    private int spriteHeight = 8;
    private boolean started;
    
    public NewGridSpritesTool(SpriteSheetPanel panel, G2DContext context) {
        super(panel);
        this.context = context;
        //newGridSpritesToolDialog = new NewGridSpritesToolDialog(context, this, JOptionPane.getFrameForComponent(panel), false);
    }

    @Override
    public int getActivationKeycode() {
        return KeyEvent.VK_Q;
    }

    @Override
    public boolean isActivated() {
        return context.getData() != null && context.getSpriteSheet() != null;
    }
    
    @Override
    public void start() {
        persistent = true;
        started = true;
        cols = 16;
        rows = 16;
        spriteWidth = 8;
        spriteHeight = 8;
        
        //newGridSpritesToolDialog.setVisible(true);
        name = "new grid sprites (x, y, c, r, w, h): (" + startPosition.x + ", " + startPosition.y + ", " + cols + ", " + rows + ", " + spriteWidth + ", " + spriteHeight + ")";
    }
    
    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getSpriteWidth() {
        return spriteWidth;
    }

    public void setSpriteWidth(int spriteWidth) {
        this.spriteWidth = spriteWidth;
    }

    public int getSpriteHeight() {
        return spriteHeight;
    }

    public void setSpriteHeight(int spriteHeight) {
        this.spriteHeight = spriteHeight;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ADD) {
        }
        else if (e.getKeyCode() == KeyEvent.VK_SUBTRACT) {
        }
        
        else if (e.getKeyCode() == KeyEvent.VK_LEFT && e.isShiftDown()) {
            cols -= 1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT && e.isShiftDown()) {
            cols += 1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_UP && e.isShiftDown()) {
            rows -= 1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN && e.isShiftDown()) {
            rows += 1;
        }
        
        else if (e.getKeyCode() == KeyEvent.VK_LEFT && e.isAltDown()) {
            spriteWidth -= 1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT && e.isAltDown()) {
            spriteWidth += 1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_UP && e.isAltDown()) {
            spriteHeight -= 1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN && e.isAltDown()) {
            spriteHeight += 1;
        }
        
        else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            startPosition.x -= 1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            startPosition.x += 1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_UP) {
            startPosition.y -= 1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            startPosition.y += 1;
        }
        
        else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            confirmCreateGridSprites();
        }
        name = "new grid sprites (x, y, c, r, w, h): (" + startPosition.x + ", " + startPosition.y + ", " + cols + ", " + rows + ", " + spriteWidth + ", " + spriteHeight + ")";
        panel.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e, int px, int py) {
        if ((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) == 0) {
            return;
        }
        startPosition.setLocation(px, py);
        name = "new grid sprites (x, y, c, r, w, h): (" + startPosition.x + ", " + startPosition.y + ", " + cols + ", " + rows + ", " + spriteWidth + ", " + spriteHeight + ")";
    }

    @Override
    public void mouseDragged(MouseEvent e, int px, int py) {
        if ((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) == 0) {
            return;
        }
        startPosition.setLocation(px, py);
        name = "new grid sprites (x, y, c, r, w, h): (" + startPosition.x + ", " + startPosition.y + ", " + cols + ", " + rows + ", " + spriteWidth + ", " + spriteHeight + ")";
    }
    
    @Override
    public void drawScaled(Graphics2D g) {
        if (!started) {
            return;
        }
        g.setXORMode(Color.YELLOW);
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                g.drawRect(startPosition.x + col * spriteWidth, startPosition.y + row * spriteHeight, spriteWidth - 1, spriteHeight - 1);
            }
        }
        g.setPaintMode();
    }

    @Override
    public void cancel() {
        context.getSpriteSheet().setSelectedSprite(null);
        //newGridSpritesToolDialog.setVisible(false);
        started = false;
    }

    private void confirmCreateGridSprites() {
        SpriteSheet spriteSheet = context.getSpriteSheet();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                String availableSpriteName = spriteSheet.getNextAvailableSpriteName();
                Sprite sprite = new Sprite(availableSpriteName, spriteSheet);
                sprite.getRectangle().setBounds(startPosition.x + col * spriteWidth, startPosition.y + row * spriteHeight, spriteWidth, spriteHeight);
                if (sprite.getRectangle().getWidth() > 0 && sprite.getRectangle().getHeight() > 0) {
                    spriteSheet.addSprite(sprite);
                }
            }
        }
        panel.toolUseFinished();
        context.getSpriteSheet().setSelectedSprite(null);
        context.update();
    }
    
}
