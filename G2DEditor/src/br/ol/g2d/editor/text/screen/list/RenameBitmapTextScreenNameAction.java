package br.ol.g2d.editor.text.screen.list;

import br.ol.g2d.G2DContext;
import br.ol.g2d.TextBitmapScreen;
import br.ol.ui.model.ActionPanel;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

    
/**
 * RenameBitmapTextScreenNameAction class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class RenameBitmapTextScreenNameAction extends ActionPanel<BitmapFontScreensListPanel>  {

    protected G2DContext context;

    public RenameBitmapTextScreenNameAction(BitmapFontScreensListPanel panel, G2DContext context) {
        super(panel);
        this.context = context;
    }
    
    @Override
    public boolean isActivated() {
        return context != null && context.getSelectedTextScreen() != null;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_R) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    TextBitmapScreen selectedScreen = context.getSelectedTextScreen();
                    Object newScreenName = JOptionPane.showInputDialog(JOptionPane.getFrameForComponent(panel)
                            , "Enter new bitmap text screen name:", "Rename bitmap text screen"
                            , JOptionPane.INFORMATION_MESSAGE, null, null, selectedScreen.getName());
                    
                    if (newScreenName != null && !selectedScreen.name.trim().equals(newScreenName.toString().trim())) {
                        if (newScreenName.toString().trim().isEmpty()) {
                            panel.showError("Invalid bitmap text screen name !", null);
                        }
                        else if (context.getTextScreens().get(newScreenName.toString().trim()) != null) {
                            panel.showError("Bitmap text screen \'" + newScreenName + "' already exists !", null);
                        }
                        else {
                            context.getTextScreens().remove(selectedScreen.getName());
                            selectedScreen.setName(newScreenName.toString().trim());
                            context.getTextScreens().getScreens().add(selectedScreen);
                            context.update();
                        }
                    }
                }
            });
        }
    }
    
}
