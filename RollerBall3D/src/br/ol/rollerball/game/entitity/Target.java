package br.ol.rollerball.game.entitity;

import br.ol.rolllerball.model.TargetBody;
import br.ol.rollerball.physics2d.StaticLine;
import br.ol.rollerball.physics2d.PhysicsVec2;
import br.ol.rollerball.game.RollerBallEntity;
import br.ol.rollerball.game.RollerBallScene;
import br.ol.rollerball.renderer3d.core.Renderer;
import br.ol.rollerball.renderer3d.parser.wavefront.WavefrontParser;
import br.ol.rollerball.renderer3d.shader.GouraudShaderWithTexture;
import static br.ol.rollerball.game.RollerBallScene.State.*;

/**
 * Target class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class Target extends RollerBallEntity {
    
    private final String targetName;
    private TargetBody targetBody;
    private double angle;
    
    public Target(String name, RollerBallScene scene, String targetName) {
        super(name, scene);
        this.targetName = targetName;
    }

    @Override
    public void init() throws Exception {
        mesh = WavefrontParser.load("/res/target.obj", 70);
        targetBody = (TargetBody) gameModel.getBodyByName(targetName);
        StaticLine sl = (StaticLine) targetBody.getShape();
        PhysicsVec2 v = new PhysicsVec2();
        v.set(sl.getP2());
        v.sub(sl.getP1());
        v.normalize();
        angle = Math.atan2(v.getX(), v.getY()) + Math.toRadians(90);
        setVisible(true);
    }
    
    @Override
    public void update(Renderer renderer) {
        transform.setIdentity();
        double ty = targetBody.isVisible() ? 0 : -15;
        transform.translate(targetBody.getPivotX(), ty, targetBody.getPivotY());
        transform.rotateY(angle);
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
