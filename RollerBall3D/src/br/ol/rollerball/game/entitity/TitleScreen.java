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
import br.ol.rollerball.game.infra.Keyboard;
import java.awt.event.KeyEvent;

/**
 * TitleScreen class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class TitleScreen extends RollerBallEntity {
    
    private final G2DContext g2d;
    private final Animation title;
    private final TextBitmapScreen screen;
    private double screenOffsetPercentY = 1;
    private final String[] pressSpaceToStart = { "                    ", "PRESS SPACE TO START" };
    
    public TitleScreen(String name, RollerBallScene scene) {
        super(name, scene);
        g2d = scene.getG2D();
        title = g2d.getAnimations().get("title");
        screen = g2d.getTextScreens().get("title_screen");
    }

    @Override
    public void init() throws Exception {
        setVisible(false);
    }
    
    @Override
    public void updateTitle(Renderer renderer) {
        long timeDelta = Time.delta / 1000000;
        
        title.update(timeDelta);
        if (title.currentFrameIndex > 130) {
            title.pause();
        }
        
        if (screenOffsetPercentY > 0 && title.currentFrameIndex > 95) {
            screenOffsetPercentY -= Time.delta * 0.000000002;
            screen.setOffsetY((int) (50 * screenOffsetPercentY));
        }
        int pressSpaceToStartIndex = title.currentFrameIndex > 130 && ((int) (System.nanoTime() * 0.000000005) % 3) < 2 ? 1 : 0;
        screen.print(16, 31, pressSpaceToStart[pressSpaceToStartIndex]);
        
        if (Keyboard.isKeyPressed(KeyEvent.VK_SPACE) && title.currentFrameIndex > 130) {
            scene.startGame();
        }
    }

    @Override
    public void updateGameStartPreparations(Renderer renderer) {
        long timeDelta = Time.delta / 1000000;
        // frames 131~155 fade out 
        title.update(timeDelta);
        if (title.playing) {
            return;
        }
        scene.changeState(PLAYING);
    }
    
    @Override
    public void draw(Renderer renderer, Graphics2D g) {
        if (visible) {
            title.draw(g);
            if (scene.getState() == TITLE) {
                screen.draw(g);
            }
        }
    }
    
    // broadcast messages

    @Override
    public void stateChanged() {
        setVisible(scene.getState() == TITLE || scene.getState() == GAME_START_PREPARATIONS);
        switch (scene.getState()) {
            case TITLE:
                screen.setOffsetY(50);
                screen.print(16, 31, pressSpaceToStart[0]);
                title.currentFrameIndex = 0;
                title.play();
                screenOffsetPercentY = 1;
                break;
            case GAME_START_PREPARATIONS:
                title.play();
                break;
        }
    }
    
}
