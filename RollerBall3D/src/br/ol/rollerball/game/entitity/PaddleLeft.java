package br.ol.rollerball.game.entitity;

import br.ol.rolllerball.model.PaddleLeftBody;
import br.ol.rollerball.game.RollerBallEntity;
import br.ol.rollerball.game.RollerBallScene;
import br.ol.rollerball.renderer3d.core.Renderer;
import br.ol.rollerball.renderer3d.parser.wavefront.WavefrontParser;
import br.ol.rollerball.renderer3d.shader.GouraudShaderWithTexture;
import static br.ol.rollerball.game.RollerBallScene.State.*;

/**
 * PaddleLeft class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class PaddleLeft extends RollerBallEntity {
    
    private final String paddleLeftName;
    private PaddleLeftBody paddleLeftBody;
    
    public PaddleLeft(String name, RollerBallScene scene, String paddleLeftName) {
        super(name, scene);
        this.paddleLeftName = paddleLeftName;
    }

    @Override
    public void init() throws Exception {
        mesh = WavefrontParser.load("/res/paddle_left.obj", 70);
        paddleLeftBody = (PaddleLeftBody) gameModel.getBodyByName(paddleLeftName);
        setVisible(true);
    }
    
    @Override
    public void update(Renderer renderer) {
        transform.setIdentity();
        transform.translate(paddleLeftBody.getPivotX(), 0, paddleLeftBody.getPivotY());
        transform.rotateY(paddleLeftBody.getFlipperAngle());
    }
    
    @Override
    public void preDraw(Renderer renderer) {
        super.preDraw(renderer);
        GouraudShaderWithTexture.minIntensity = 0.5;
        GouraudShaderWithTexture.scale = 1.5;
    }
    
    // broadcast messages

    @Override
    public void stateChanged() {
        if (scene.getState() == TITLE) {
            setVisible(true);
        }
    }
    
}
