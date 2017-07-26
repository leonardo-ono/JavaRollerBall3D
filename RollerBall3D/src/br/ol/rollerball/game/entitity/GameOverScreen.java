package br.ol.rollerball.game.entitity;

import br.ol.g2d.Animation;
import br.ol.g2d.G2DContext;
import br.ol.g2d.TextBitmapScreen;
import br.ol.rollerball.game.RollerBallEntity;
import br.ol.rollerball.game.RollerBallScene;
import br.ol.rollerball.game.infra.Time;
import br.ol.rollerball.renderer3d.core.Renderer;
import java.awt.Graphics2D;
import static br.ol.rollerball.game.RollerBallScene.State.*;

/**
 * GameOverScreen class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class GameOverScreen extends RollerBallEntity {
    
    private final G2DContext g2d;
    private final Animation gameOverAnimation;
    
    public GameOverScreen(String name, RollerBallScene scene) {
        super(name, scene);
        g2d = scene.getG2D();
        gameOverAnimation = g2d.getAnimations().get("game_over");
    }

    @Override
    public void init() throws Exception {
        setVisible(false);
    }
    
    @Override
    public void updateGameOverScreen(Renderer renderer) {
        long timeDelta = Time.delta / 1000000;
        gameOverAnimation.update(timeDelta);
        if (gameOverAnimation.playing) {
            return;
        }
        scene.backToTitle();
    }

    @Override
    public void draw(Renderer renderer, Graphics2D g) {
        if (isVisible()) {
            gameOverAnimation.draw(g);
        }
    }
    
    // broadcast messages

    @Override
    public void stateChanged() {
        setVisible(scene.getState() == GAME_OVER_SCREEN);
        if (scene.getState() == GAME_OVER_SCREEN) {
            gameModel.setLives(0);
            instructionPointer = 0;
            gameOverAnimation.stop();
            gameOverAnimation.currentFrameIndex = 0;
            gameOverAnimation.play();
        }
    }
    
}
