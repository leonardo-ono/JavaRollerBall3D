package br.ol.rollerball.game.entitity;

import br.ol.rolllerball.model.SlotWheelBody;
import br.ol.rollerball.game.RollerBallEntity;
import br.ol.rollerball.game.RollerBallScene;
import br.ol.rollerball.renderer3d.core.Renderer;
import br.ol.rollerball.renderer3d.parser.wavefront.WavefrontParser;
import br.ol.rollerball.renderer3d.shader.GouraudShaderWithTexture;
import static br.ol.rollerball.game.RollerBallScene.State.*;

/**
 * PoolTable class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class SlotWheel extends RollerBallEntity {
    
    private final String slotWheelName;
    private SlotWheelBody slotWheelBody;
    
    public SlotWheel(String name, RollerBallScene scene, String slotWheelName) {
        super(name, scene);
        this.slotWheelName = slotWheelName;
    }

    @Override
    public void init() throws Exception {
        mesh = WavefrontParser.load("/res/slot_wheel.obj", 70);
        slotWheelBody = (SlotWheelBody) gameModel.getBodyByName(slotWheelName);
        setVisible(true);
    }
    
    @Override
    public void update(Renderer renderer) {
        transform.setIdentity();
        transform.translate(slotWheelBody.getPosition().getX(), -20, slotWheelBody.getPosition().getY());
        transform.rotateX(Math.toRadians(slotWheelBody.getAngle()));
    }

    @Override
    public void preDraw(Renderer renderer) {
        super.preDraw(renderer);
        GouraudShaderWithTexture.minIntensity = 1;
        double hitColorIntensityDouble = slotWheelBody.isLightOn() || !slotWheelBody.isAwardObtained() ? 1.0 : 0.5;
        GouraudShaderWithTexture.maxIntensity = hitColorIntensityDouble;
    }
    
    // broadcast messages

    @Override
    public void stateChanged() {
        if (scene.getState() == TITLE) {
            setVisible(true);
        }
    }
    
}
