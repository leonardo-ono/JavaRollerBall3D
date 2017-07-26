package br.ol.rollerball.game.entitity;

import br.ol.g2d.TextBitmapScreen;
import br.ol.rollerball.game.RollerBallEntity;
import br.ol.rollerball.game.RollerBallScene;
import br.ol.rollerball.renderer3d.core.Renderer;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import static br.ol.rollerball.game.RollerBallScene.State.*;
import br.ol.rollerball.game.infra.Time;

/**
 * HUD class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class HUD extends RollerBallEntity {
    
    private BufferedImage gradient;
    private TextBitmapScreen screen;
    
    private long firstShowTime;
    private boolean firstShow = true;
    private double currentPercentY = 1.0; // in percent
    private int hudY;
    
    public HUD(String name, RollerBallScene scene) {
        super(name, scene);
    }

    @Override
    public void init() throws Exception {
        gradient = ImageIO.read(getClass().getResourceAsStream("/res/hud_gradient.png"));
        screen = scene.getG2D().getTextScreens().get("hud");
        setVisible(false);
    }
    
    @Override
    public void updateTitle(Renderer renderer) {
        if (firstShow) {
            if (System.currentTimeMillis() - firstShowTime > 3500) {
                currentPercentY -= currentPercentY * (Time.delta * 0.0000000025);
                if (currentPercentY < 0.005) {
                    currentPercentY = 0;
                    firstShow = false;
                }
            }
            screen.setOffsetY(8 - (int) (50 * currentPercentY));
            hudY = (int) (-126 * currentPercentY);
        }
    }
    
    @Override
    public void posUpdate(Renderer renderer) {
        String scoreStr = scene.getState() == TITLE ? "000000" : scene.getGameModel().getScoreStr();
        String livesStr = scene.getState() == TITLE ? "00" : scene.getGameModel().getLivesStr();
        screen.print(0, 1, scoreStr);
        screen.print(9, 1, scene.getGameModel().getHiscoreStr());
        screen.print(21, 1, livesStr);
    }
    
    @Override
    public void draw(Renderer renderer, Graphics2D g) {
        if (visible) {
            g.drawImage(gradient, 0, hudY, null);
            screen.draw(g);
        }
    }
    
    // broadcast messages

    @Override
    public void stateChanged() {
        if (scene.getState() == TITLE) {
            setVisible(true);
        }
        if (scene.getState() == TITLE && firstShow) {
            firstShowTime = System.currentTimeMillis();
        }
    }
    
}
