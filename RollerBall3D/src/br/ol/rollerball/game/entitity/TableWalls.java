package br.ol.rollerball.game.entitity;

import br.ol.rollerball.game.RollerBallEntity;
import br.ol.rollerball.game.RollerBallScene;
import br.ol.rollerball.renderer3d.core.Renderer;
import br.ol.rollerball.renderer3d.parser.wavefront.WavefrontParser;
import br.ol.rollerball.renderer3d.shader.GouraudShaderWithTexture;
import static br.ol.rollerball.game.RollerBallScene.State.*;

/**
 * TableWalls class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class TableWalls extends RollerBallEntity {

    public TableWalls(String name, RollerBallScene scene) {
        super(name, scene);
    }

    @Override
    public void init() throws Exception {
        mesh = WavefrontParser.load("/res/table_walls.obj", 70);
        transform.setIdentity();
        setVisible(true);
    }
    
    @Override
    public void preDraw(Renderer renderer) {
        super.preDraw(renderer);
        GouraudShaderWithTexture.minIntensity = 0.65;
    }
    
    // broadcast messages

    @Override
    public void stateChanged() {
        if (scene.getState() == TITLE) {
            setVisible(true);
        }
    }
    
}
