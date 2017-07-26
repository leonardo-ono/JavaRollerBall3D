package br.ol.rollerball.game.entitity;

import br.ol.rollerball.game.RollerBallEntity;
import br.ol.rollerball.game.RollerBallScene;
import br.ol.rollerball.renderer3d.core.Renderer;
import br.ol.rollerball.renderer3d.parser.wavefront.WavefrontParser;
import br.ol.rollerball.renderer3d.shader.FlatShader;
import static br.ol.rollerball.game.RollerBallScene.State.*;

/**
 * ShooterEnd class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class ShooterEnd extends RollerBallEntity {

    public ShooterEnd(String name, RollerBallScene scene) {
        super(name, scene);
    }

    @Override
    public void init() throws Exception {
        mesh = WavefrontParser.load("/res/shooter_end.obj", 70);
        setVisible(true);
    }
    
    @Override
    public void update(Renderer renderer) {
        transform.setIdentity();
        double ty = 87 * scene.getGameModel().getTable().getShooter().getIntensity();
        transform.translate(651, 0, 2373 + ty);
    }
    
    @Override
    public void preDraw(Renderer renderer) {
        super.preDraw(renderer);
        renderer.setShader(RollerBallScene.flatShader);
        RollerBallScene.flatShader.setColor(255, 255, 0, 0);
        FlatShader.minIntensity = 0.5;
    }
    
    // broadcast messages

    @Override
    public void stateChanged() {
        if (scene.getState() == TITLE) {
            setVisible(true);
        }
    }
    
}
