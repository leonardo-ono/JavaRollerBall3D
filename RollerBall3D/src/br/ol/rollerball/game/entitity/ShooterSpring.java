package br.ol.rollerball.game.entitity;

import br.ol.rollerball.game.RollerBallEntity;
import br.ol.rollerball.game.RollerBallScene;
import br.ol.rollerball.renderer3d.core.Renderer;
import br.ol.rollerball.renderer3d.parser.wavefront.WavefrontParser;
import static br.ol.rollerball.game.RollerBallScene.State.*;

/**
 * ShooterSpring class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class ShooterSpring extends RollerBallEntity {

    public ShooterSpring(String name, RollerBallScene scene) {
        super(name, scene);
    }

    @Override
    public void init() throws Exception {
        mesh = WavefrontParser.load("/res/shooter_spring.obj", 70);
        setVisible(true);
    }
    
    @Override
    public void update(Renderer renderer) {
        transform.setIdentity();
        transform.translate(651, 0, 2562);
        transform.scale(1.5, 1.0, 1.0 - scene.getGameModel().getTable().getShooter().getIntensity() / 2);
    }
    
    @Override
    public void preDraw(Renderer renderer) {
        super.preDraw(renderer);
        renderer.setBackfaceCullingEnabled(false);
        renderer.setShader(RollerBallScene.shooterShader);
    }
    
    // broadcast messages

    @Override
    public void stateChanged() {
        if (scene.getState() == TITLE) {
            setVisible(true);
        }
    }
    
}
