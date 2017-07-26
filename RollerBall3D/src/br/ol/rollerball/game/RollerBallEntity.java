package br.ol.rollerball.game;

import br.ol.rolllerball.model.RollerBallGameModel;
import br.ol.rollerball.game.infra.Entity;
import br.ol.rollerball.renderer3d.core.Renderer;

/**
 * EightBallEntity class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class RollerBallEntity extends Entity<RollerBallScene> {
    
    public int instructionPointer;
    public long waitTime;
    public RollerBallGameModel gameModel;
    
    public RollerBallEntity(String name, RollerBallScene scene) {
        super(name, scene);
        this.gameModel = scene.getGameModel();
    }

    @Override
    public void update(Renderer renderer) {
        update2DPhysics();
        preUpdate(renderer);
        switch(scene.state) {
            case INITIALIZING: updateInitializing(renderer); break;
            case OL_PRESENTS: updateOlPresents(renderer); break;
            case TITLE: updateTitle(renderer); break;
            case GAME_START_PREPARATIONS: updateGameStartPreparations(renderer); break;
            case PLAYING: updatePlaying(renderer); break;
            case BONUS_SCREEN: updateBonusScreen(renderer); break;
            case GAME_OVER_SCREEN: updateGameOverScreen(renderer); break;
        }
        posUpdate(renderer);
    }
    
    public void update2DPhysics() {
    }
    
    public void preUpdate(Renderer renderer) {
    }

    public void updateInitializing(Renderer renderer) {
    }

    public void updateOlPresents(Renderer renderer) {
    }

    public void updateTitle(Renderer renderer) {
    }

    public void updateGameStartPreparations(Renderer renderer) {
    }
    
    public void updatePlaying(Renderer renderer) {
    }

    public void updateBonusScreen(Renderer renderer) {
    }

    public void updateGameOverScreen(Renderer renderer) {
    }

    public void posUpdate(Renderer renderer) {
    }
    
    // broadcast messages
    
    public void stateChanged() {
    }


}
