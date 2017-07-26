package br.ol.g2d.editor.spritesheet;

import br.ol.g2d.G2DContext;
import br.ol.ui.model.ActionPanel;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

    
/**
 * ExportSpriteSheetAsImageAction class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class ExportSpriteSheetAsImageAction extends ActionPanel<SpriteSheetPanel>  {

    protected G2DContext context;

    public ExportSpriteSheetAsImageAction(SpriteSheetPanel panel, G2DContext context) {
        super(panel);
        this.context = context;
    }
    
    @Override
    public boolean isActivated() {
        return context != null && context.getSpriteSheet() != null;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_Y && !e.isActionKey() && !e.isAltDown() && !e.isAltGraphDown() && !e.isControlDown() && !e.isMetaDown() && !e.isShiftDown()) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    int result = fileChooser.showDialog(JOptionPane.getFrameForComponent(panel), "Export sprite sheet as image");
                    if (result == JFileChooser.CANCEL_OPTION) {
                        return;
                    }
                    File destinationFile = fileChooser.getSelectedFile();
                    try {
                        saveAsImage(destinationFile, context.getSpriteSheet().getImage());
                    } catch (Exception ex) {
                        panel.showError("It was not possible to export sprite '" + destinationFile.getParent() + "' as image !", ex);
                    }
                }
            });
        }
    }
    
    private void saveAsImage(File file, BufferedImage image) throws Exception {
        ImageIO.write(image, "png", file);
    }
    
}
