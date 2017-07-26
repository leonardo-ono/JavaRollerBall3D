package main;

import br.ol.rollerball.game.RollerBallScene;
import br.ol.rollerball.game.infra.Display;
import br.ol.rollerball.game.infra.Scene;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Main class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class Main {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Scene scene = new RollerBallScene();
                Display engine = new Display(scene);

                JFrame view = new JFrame();
                view.setTitle("Roller Ball 3D");
                view.setSize(800, 600);
                view.setLocationRelativeTo(null);
                view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                view.setResizable(false);
                view.getContentPane().add(engine);
                view.setVisible(true);

                engine.requestFocus();
                try {        
                    engine.init();
                } catch (Exception ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    System.exit(-1);
                }
            }
        });
    }
    
}
