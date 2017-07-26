package br.ol.g2d.editor.text.font.list;

import br.ol.g2d.G2DContext;
import br.ol.g2d.TextBitmapFont;
import br.ol.ui.model.ActionPanel;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

    
/**
 * CreateNewBitmapFontAction class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class CreateNewBitmapFontAction extends ActionPanel<BitmapFontsListPanel>  {

    protected G2DContext context;

    public CreateNewBitmapFontAction(BitmapFontsListPanel panel, G2DContext context) {
        super(panel);
        this.context = context;
    }
    
    @Override
    public boolean isActivated() {
        return context != null && context.getFonts() != null && context.getSelectedSprite() != null;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_N) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    Object newBitmapFontName = JOptionPane.showInputDialog(JOptionPane.getFrameForComponent(panel)
                            , "Enter new bitmap font name:", "Create new bitmap font"
                            , JOptionPane.INFORMATION_MESSAGE, null, null, "new_bitmap_font_name");
                    
                    if (newBitmapFontName != null) {
                        if (newBitmapFontName.toString().trim().isEmpty()) {
                            panel.showError("Invalid bitmap font name !", null);
                        }
                        else if (context.getFonts().get(newBitmapFontName.toString().trim()) != null) {
                            panel.showError("Bitmap font \'" + newBitmapFontName + "' already exists !", null);
                        }
                        else {
                            String fontName = newBitmapFontName.toString().trim();
                            int width = context.getSelectedSprite().getWidth();
                            int height = context.getSelectedSprite().getHeight();
                            context.getFonts().create(fontName, width, height);
                            TextBitmapFont newBitmapFont = context.getFonts().get(fontName);
                            context.getFonts().setSelectedFont(newBitmapFont);
                            context.update();
                        }
                    }
                }
            });
        }
    }
    
}
