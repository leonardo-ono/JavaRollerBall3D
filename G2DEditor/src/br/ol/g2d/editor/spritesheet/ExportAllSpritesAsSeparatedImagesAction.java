package br.ol.g2d.editor.spritesheet;

import br.ol.g2d.G2DContext;
import br.ol.g2d.Sprite;
import br.ol.ui.model.ActionPanel;
import java.awt.event.KeyEvent;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
    
/**
 * ExportAllSpritesAsSeparatedImagesAction class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class ExportAllSpritesAsSeparatedImagesAction extends ActionPanel<SpriteSheetPanel>  {

    protected G2DContext context;

    public ExportAllSpritesAsSeparatedImagesAction(SpriteSheetPanel panel, G2DContext context) {
        super(panel);
        this.context = context;
    }
    
    @Override
    public boolean isActivated() {
        return context != null && context.getSpriteSheet() != null;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_O && !e.isActionKey() && !e.isAltDown() && !e.isAltGraphDown() && !e.isControlDown() && !e.isMetaDown() && !e.isShiftDown()) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    int result = fileChooser.showDialog(JOptionPane.getFrameForComponent(panel), "Export");
                    if (result == JFileChooser.CANCEL_OPTION) {
                        return;
                    }
                    File destinationFolder = fileChooser.getSelectedFile();
                    for (Sprite sprite : context.getSpriteSheet().getSprites()) {
                        try {
                            saveSpriteAsImage(destinationFolder.getPath(), sprite);
                        } catch (Exception ex) {
                            panel.showError("It was not possible to export sprite '" + sprite.getName() + "' as image !", ex);
                        }
                    }
                }
            });
        }
    }
    
    private void saveSpriteAsImage(String folder, Sprite sprite) throws Exception {
        File file = new File(folder, sprite.getName() + ".png");
        ImageIO.write(sprite.createImage(), "png", file);
    }
    
}
