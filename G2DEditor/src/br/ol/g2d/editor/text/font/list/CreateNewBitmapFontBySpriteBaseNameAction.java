package br.ol.g2d.editor.text.font.list;

import br.ol.g2d.G2DContext;
import br.ol.g2d.Sprite;
import br.ol.g2d.SpriteSheet;
import br.ol.g2d.TextBitmapFont;
import br.ol.ui.model.ActionPanel;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

    
/**
 * CreateNewBitmapFontBySpriteBaseNameAction class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class CreateNewBitmapFontBySpriteBaseNameAction extends ActionPanel<BitmapFontsListPanel>  {

    protected G2DContext context;

    public CreateNewBitmapFontBySpriteBaseNameAction(BitmapFontsListPanel panel, G2DContext context) {
        super(panel);
        this.context = context;
    }
    
    @Override
    public boolean isActivated() {
        return context != null && context.getFonts() != null && context.getSpriteSheet() != null;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_G) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    Object spriteBaseName = JOptionPane.showInputDialog(JOptionPane.getFrameForComponent(panel)
                            , "Enter sprite base name:", "Create new bitmap font by sprite base name"
                            , JOptionPane.INFORMATION_MESSAGE, null, null, "sprite_base_name");
                    
                    if (spriteBaseName != null) {
                        if (spriteBaseName.toString().trim().isEmpty()) {
                            panel.showError("Invalid sprite base name !", null);
                        }
                        else {
                            confirmCreateNewBitmapFontBySpriteBaseName(spriteBaseName.toString().trim());
                        }
                    }
                }
            });
        }
    }
    
    private void confirmCreateNewBitmapFontBySpriteBaseName(String spriteBaseName) {
        SpriteSheet spriteSheet = context.getSpriteSheet();
        
        String fontName = spriteBaseName;
        TextBitmapFont newFont = null;
        
        for (Sprite sprite : spriteSheet.getSprites()) {
            if (sprite.getName().startsWith(spriteBaseName + "_")) {
                try {
                    int charCode = Integer.parseInt(sprite.getName().replace(spriteBaseName + "_", ""));
                    if (newFont == null) {
                        newFont = new TextBitmapFont(fontName, sprite.getWidth(), sprite.getHeight());
                    }
                    newFont.addChar((char) charCode, sprite);
                }
                catch (Exception e) {
                }
            }
        }

        if (newFont == null) {
            panel.showError("No character sprites with base name '" + spriteBaseName + "' was found !", null);
        }
        else {
            context.getFonts().getFonts().add(newFont);
            context.getFonts().setSelectedFont(newFont);
        }
        context.update();
    }
    
}
