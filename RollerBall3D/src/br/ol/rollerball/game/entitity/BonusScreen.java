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
 * BonusScreen class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class BonusScreen extends RollerBallEntity {
    
    private final G2DContext g2d;

    private final Animation fadeIn;
    private final Animation fadeOut;
    private boolean fadeInVisible;
    private boolean fadeOutVisible;
    
    private final TextBitmapScreen screen;
    private boolean screenVisible;
    
    public BonusScreen(String name, RollerBallScene scene) {
        super(name, scene);
        g2d = scene.getG2D();
        fadeIn = g2d.getAnimations().get("fade_in");
        fadeOut = g2d.getAnimations().get("fade_out");
        screen = g2d.getTextScreens().get("bonus_screen");
    }

    @Override
    public void init() throws Exception {
        setVisible(false);
    }
    
    @Override
    public void updateBonusScreen(Renderer renderer) {
        yield:
        while (true) {
            switch (instructionPointer) {
                case 0:
                    if (gameModel.getTotalBonus() == 0 && gameModel.getLives() > 1) {
                        instructionPointer = 7;
                        break yield;
                    }
                    else if (gameModel.getTotalBonus() > 0 || gameModel.getLives() == 1) {
                        fadeInVisible = true;
                        fadeIn.play();
                        instructionPointer = 1;
                        break yield;
                    }
                    break yield;
                // needs to show fade-in
                case 1:
                    if (fadeIn.playing) {
                        break yield;
                    }
                    
                    // game over ?
                    if (gameModel.getTotalBonus() == 0 && gameModel.getLives() == 1) {
                        waitTime = System.currentTimeMillis();
                        instructionPointer = 45;
                        break yield;
                    }
                    
                    screen.print(15, 4, gameModel.getBonusStr(0));
                    screen.print(15, 6, gameModel.getBonusStr(2));
                    screen.print(14, 10, gameModel.getTotalBonusStr());
                    screenVisible = true;
                    waitTime = System.currentTimeMillis();
                    instructionPointer = 25;
                case 25:
                    if (System.currentTimeMillis() - waitTime < 2000) {
                        break yield;
                    }
                    instructionPointer = 2;
                case 2:
                    screen.print(15, 4, gameModel.getBonusStr(0));
                    screen.print(15, 6, gameModel.getBonusStr(2));
                    screen.print(14, 10, gameModel.getTotalBonusStr());
                    waitTime = System.currentTimeMillis();
                    instructionPointer = 3;
                case 3:
                    if (System.currentTimeMillis() - waitTime < 10) {
                        break yield;
                    }
                    gameModel.transferBonusToScore(250);
                    if (gameModel.getTotalBonus() > 0) {
                        instructionPointer = 2;
                        break yield;
                    }
                    screen.print(15, 4, gameModel.getBonusStr(0));
                    screen.print(15, 6, gameModel.getBonusStr(2));
                    screen.print(14, 10, gameModel.getTotalBonusStr());
                    waitTime = System.currentTimeMillis();
                    instructionPointer = 4;
                case 4:
                    if (System.currentTimeMillis() - waitTime < 1000) {
                        break yield;
                    }
                    screenVisible = false;
                    // needs to show fade out ?
                    if (gameModel.getLives() > 1) {
                        fadeInVisible = false;
                        fadeOutVisible = true;
                        fadeOut.play();
                        instructionPointer = 6;
                        break yield;
                    }
                    waitTime = System.currentTimeMillis();
                    instructionPointer = 45;
                case 45:
                    if (System.currentTimeMillis() - waitTime < 1000) {
                        break yield;
                    }
                    instructionPointer = 5;
                // game over
                case 5:
                    scene.showGameOverScreen();
                    instructionPointer = 8;
                    break yield;
                // next life, needs to show fade-out first
                case 6:
                    if (fadeOut.playing) {
                        break yield;
                    }
                    fadeOutVisible = false;
                    instructionPointer = 7;
                case 7:
                    scene.nextLife();
                    instructionPointer = 8;
                case 8:
                    break yield;
            }
        }
        long timeDelta = Time.delta / 1000000;
        fadeIn.update(timeDelta);
        fadeOut.update(timeDelta);
    }

    @Override
    public void draw(Renderer renderer, Graphics2D g) {
        if (isVisible()) {
            if (fadeInVisible) {
                fadeIn.draw(g);
            }
            if (fadeOutVisible) {
                fadeOut.draw(g);
            }
            if (screenVisible) {
                screen.draw(g);
            }
        }
    }
    
    // broadcast messages

    @Override
    public void stateChanged() {
        setVisible(scene.getState() == BONUS_SCREEN);
        if (scene.getState() == BONUS_SCREEN) {
            instructionPointer = 0;
            fadeIn.stop();
            fadeOut.stop();
            fadeIn.currentFrameIndex = 0;
            fadeOut.currentFrameIndex = 0;
            fadeInVisible = false;
            fadeOutVisible = false;
            screenVisible = false;
        }
    }
    
}
