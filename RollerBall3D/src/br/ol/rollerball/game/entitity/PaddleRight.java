package br.ol.rollerball.game.entitity;

import br.ol.rolllerball.model.PaddleRightBody;
import br.ol.rollerball.game.RollerBallEntity;
import br.ol.rollerball.game.RollerBallScene;
import br.ol.rollerball.renderer3d.core.Renderer;
import br.ol.rollerball.renderer3d.parser.wavefront.WavefrontParser;
import br.ol.rollerball.renderer3d.shader.GouraudShaderWithTexture;
import static br.ol.rollerball.game.RollerBallScene.State.*;

/**
 * PaddleRight class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class PaddleRight extends RollerBallEntity {

    private final String paddleRightName;
    private PaddleRightBody paddleRightBody;
    
    public PaddleRight(String name, RollerBallScene scene, String paddleRightName) {
        super(name, scene);
        this.paddleRightName = paddleRightName;
    }

    @Override
    public void init() throws Exception {
        mesh = WavefrontParser.load("/res/paddle_right.obj", 70);
        paddleRightBody = (PaddleRightBody) gameModel.getBodyByName(paddleRightName);
        setVisible(true);
    }
    
    @Override
    public void update(Renderer renderer) {
        transform.setIdentity();
        transform.translate(paddleRightBody.getPivotX(), 0, paddleRightBody.getPivotY());
        transform.rotateY(paddleRightBody.getFlipperAngle());
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
