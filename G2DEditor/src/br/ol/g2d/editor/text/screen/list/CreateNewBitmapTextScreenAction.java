package br.ol.g2d.editor.text.screen.list;

import br.ol.g2d.editor.text.font.list.*;
import br.ol.g2d.G2DContext;
import br.ol.g2d.TextBitmapFont;
import br.ol.g2d.TextBitmapScreen;
import br.ol.ui.model.ActionPanel;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

    
/**
 * CreateNewBitmapTextScreenAction class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class CreateNewBitmapTextScreenAction extends ActionPanel<BitmapFontScreensListPanel>  {

    protected G2DContext context;

    public CreateNewBitmapTextScreenAction(BitmapFontScreensListPanel panel, G2DContext context) {
        super(panel);
        this.context = context;
    }
    
    @Override
    public boolean isActivated() {
        return context != null && context.getSelectedFont() != null && context.getTextScreens() != null && context.getAnimations()!= null;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_N) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    Object newBitmapTextScreenName = JOptionPane.showInputDialog(JOptionPane.getFrameForComponent(panel)
                            , "Enter new bitmap text screen name:", "Create new bitmap text screen"
                            , JOptionPane.INFORMATION_MESSAGE, null, null, "new_bitmap_text_screen_name");
                    
                    if (newBitmapTextScreenName != null) {
                        if (newBitmapTextScreenName.toString().trim().isEmpty()) {
                            panel.showError("Invalid bitmap text screen name !", null);
                        }
                        else if (context.getTextScreens().get(newBitmapTextScreenName.toString().trim()) != null) {
                            panel.showError("Bitmap text screen \'" + newBitmapTextScreenName + "' already exists !", null);
                        }
                        else {
                            String textScreenName = newBitmapTextScreenName.toString().trim();
                            int width = context.getAnimations().getDefaultScreenWidth();
                            int height = context.getAnimations().getDefaultScreenHeight();
                            context.getTextScreens().create(textScreenName, context.getSelectedFont(), width, height);
                            TextBitmapScreen newBitmapScreen = context.getTextScreens().get(textScreenName);
                            context.getTextScreens().setSelectedScreen(newBitmapScreen);
                            context.update();
                        }
                    }
                }
            });
        }
    }
    
}
