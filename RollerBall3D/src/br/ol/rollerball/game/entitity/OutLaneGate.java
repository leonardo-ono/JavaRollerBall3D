package br.ol.rollerball.game.entitity;

import br.ol.rolllerball.model.WallBody;
import br.ol.rollerball.game.RollerBallEntity;
import br.ol.rollerball.game.RollerBallScene;
import br.ol.rollerball.renderer3d.core.Renderer;
import br.ol.rollerball.renderer3d.parser.wavefront.WavefrontParser;
import br.ol.rollerball.renderer3d.shader.GouraudShaderWithTexture;
import static br.ol.rollerball.game.RollerBallScene.State.*;

/**
 * OutLaneGate class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class OutLaneGate extends RollerBallEntity {
    
    private final String gateBaseName = "gate";
    private WallBody gateOpenedBody;
    
    private double angle;
    private double targetAngle;
    
    public OutLaneGate(String name, RollerBallScene scene) {
        super(name, scene);
    }

    @Override
    public void init() throws Exception {
        mesh = WavefrontParser.load("/res/out_lane_gate.obj", 70);
        gateOpenedBody = (WallBody) gameModel.getBodyByName(gateBaseName + "_opened_4_0");
        setVisible(true);
    }
    
    @Override
    public void update(Renderer renderer) {
        targetAngle = gateOpenedBody.isVisible() ? Math.toRadians(-135) : Math.toRadians(-30);
        angle += (targetAngle - angle) * 0.2;
        transform.setIdentity();
        transform.translate(565, -1, 2380);
        transform.rotateY(angle);
    }

    @Override
    public void preDraw(Renderer renderer) {
        super.preDraw(renderer);
        GouraudShaderWithTexture.minIntensity = 0.5;
        GouraudShaderWithTexture.scale = 2.0;
    }
    
    // broadcast messages

    @Override
    public void stateChanged() {
        if (scene.getState() == TITLE) {
            setVisible(true);
        }
    }
    
}
