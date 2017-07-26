package br.ol.ge;

import javax.swing.JFrame;

/**
 *
 * @author leonardo
 */
public class Window extends JFrame {
    
    private final Configuration configuration;
    private final Display display;
    
    public Window(Display display) {
        this.configuration = display.getConfiguration();
        this.display = display;
        config();
    }

    private void config() {
        getContentPane().add(display);
        pack();
        setTitle(configuration.getTitle());
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void start() {
        display.start();
        setVisible(true);
    }
    
}
