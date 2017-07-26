package br.ol.rollerball.game.entitity;

import br.ol.rollerball.game.RollerBallEntity;
import br.ol.rollerball.game.RollerBallScene;
import br.ol.rollerball.game.RollerBallScene.State;
import br.ol.rollerball.renderer3d.core.Renderer;

/**
 * Initializer class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class Initializer extends RollerBallEntity {

    public Initializer(String name, RollerBallScene scene) {
        super(name, scene);
    }

    @Override
    public void updateInitializing(Renderer renderer) {
        yield:
        while (true) {
            switch (instructionPointer) {
                case 0:
                    waitTime = System.currentTimeMillis();
                    instructionPointer = 1;
                case 1:
                    while (System.currentTimeMillis() - waitTime < 3000) {
                        break yield;
                    }
                    scene.changeState(State.OL_PRESENTS);
                    break yield;
            }
        }
    }
    
}
