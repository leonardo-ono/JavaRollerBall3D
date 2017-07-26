package br.ol.g2d.editor.text.font.list;

import br.ol.g2d.G2DContext;
import br.ol.g2d.TextBitmapFont;
import br.ol.ui.model.ActionPanel;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

    
/**
 * RenameBitmapFontNameAction class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class RenameBitmapFontNameAction extends ActionPanel<BitmapFontsListPanel>  {

    protected G2DContext context;

    public RenameBitmapFontNameAction(BitmapFontsListPanel panel, G2DContext context) {
        super(panel);
        this.context = context;
    }
    
    @Override
    public boolean isActivated() {
        return context != null && context.getSelectedFont() != null;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_R) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    TextBitmapFont selectedFont = context.getSelectedFont();
                    Object newFontName = JOptionPane.showInputDialog(JOptionPane.getFrameForComponent(panel)
                            , "Enter new bitmap font name:", "Rename bitmap font"
                            , JOptionPane.INFORMATION_MESSAGE, null, null, selectedFont.getName());
                    
                    if (newFontName != null && !selectedFont.name.trim().equals(newFontName.toString().trim())) {
                        if (newFontName.toString().trim().isEmpty()) {
                            panel.showError("Invalid bitmap font name !", null);
                        }
                        else if (context.getFonts().get(newFontName.toString().trim()) != null) {
                            panel.showError("Animation \'" + newFontName + "' already exists !", null);
                        }
                        else {
                            context.getFonts().remove(selectedFont.name);
                            selectedFont.setName(newFontName.toString().trim());
                            context.getFonts().getFonts().add(selectedFont);
                            context.update();
                        }
                    }
                }
            });
        }
    }
    
}
