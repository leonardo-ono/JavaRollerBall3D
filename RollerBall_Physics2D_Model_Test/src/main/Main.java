package main;

import br.ol.ge.Configuration;
import br.ol.ge.Display;
import br.ol.ge.Scene;
import br.ol.ge.Window;
import br.ol.rollerball.scene.RBScene;
import javax.swing.SwingUtilities;

/**
 *
 * @author leonardo
 */
public class Main {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Configuration configuration = new Configuration("", 800, 800, 1, 1, 60);
                Display display = new Display(configuration);
                Scene scene = new RBScene(configuration);
                display.setScene(scene);
                Window window = new Window(display);
                window.start();
                display.requestFocus();
            }
        });
    }
    
}
