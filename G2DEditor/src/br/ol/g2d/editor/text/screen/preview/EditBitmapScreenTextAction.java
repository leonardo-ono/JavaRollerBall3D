package br.ol.g2d.editor.text.screen.preview;

import br.ol.g2d.G2DContext;
import br.ol.g2d.TextBitmapFont;
import br.ol.g2d.TextBitmapScreen;
import br.ol.ui.model.ActionPanel;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;

    
/**
 * DeleteBitmapTextScreenAction class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class EditBitmapScreenTextAction extends ActionPanel<BitmapTextScreenPreviewPanel>  {

    protected G2DContext context;
    
    public EditBitmapScreenTextAction(BitmapTextScreenPreviewPanel panel, G2DContext context) {
        super(panel);
        this.context = context;
    }

    @Override
    public boolean isActivated() {
        return context != null && context.getSelectedTextScreen() != null;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            panel.cursorX--;
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            panel.cursorX++;
        }
        else if (e.getKeyCode() == KeyEvent.VK_UP) {
            panel.cursorY--;
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            panel.cursorY++;
        }
        else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            TextBitmapScreen selectedScreen = context.getSelectedTextScreen();
            selectedScreen.removeChar(panel.cursorX, panel.cursorY);
            panel.cursorX++;
        }
        else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            TextBitmapScreen selectedScreen = context.getSelectedTextScreen();
            panel.cursorX--;
            selectedScreen.removeChar(panel.cursorX, panel.cursorY);
        }
        else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            panel.cursorY++;
            panel.cursorX = 0;
        }
    }
    
    public boolean isPrintableChar( char c ) {
        Character.UnicodeBlock block = Character.UnicodeBlock.of( c );
        return (!Character.isISOControl(c)) &&
                c != KeyEvent.CHAR_UNDEFINED &&
                block != null &&
                block != Character.UnicodeBlock.SPECIALS;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (!isPrintableChar(e.getKeyChar()) || e.getKeyChar() == ' '){
            return;
        }
        else {
            TextBitmapScreen selectedScreen = context.getSelectedTextScreen();
            selectedScreen.print(panel.cursorX, panel.cursorY, e.getKeyChar() + "");
            panel.cursorX++;
            context.update();
        }
    }

    @Override
    public void mousePressed(MouseEvent e, int px, int py) {
        if ((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) == 0) {
            return;
        }
        int x = (px - panel.getMarginWidth());
        int y = (py - panel.getMarginHeight());
        Point cursorPosition = context.getSelectedTextScreen().getCridCellPosition(x, y);
        panel.cursorX = cursorPosition.x;
        panel.cursorY = cursorPosition.y;
    }
    
    @Override
    public void drawScaled(Graphics2D g) {
        if (panel.isBlinking()) {
            AffineTransform at = g.getTransform();
            g.translate(panel.getMarginWidth(), panel.getMarginHeight());
            
            TextBitmapScreen selectedScreen = context.getSelectedTextScreen();
            selectedScreen.drawCursor(g, panel.cursorX, panel.cursorY);
            
            g.setTransform(at);
        }
    }
    
}
