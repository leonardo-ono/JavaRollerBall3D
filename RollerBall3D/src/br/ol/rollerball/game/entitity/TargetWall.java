package br.ol.rollerball.game.entitity;

import br.ol.rolllerball.model.WallBody;
import br.ol.rollerball.physics2d.StaticLine;
import br.ol.rollerball.physics2d.PhysicsVec2;
import br.ol.rollerball.game.RollerBallEntity;
import br.ol.rollerball.game.RollerBallScene;
import br.ol.rollerball.renderer3d.core.Renderer;
import br.ol.rollerball.renderer3d.parser.wavefront.WavefrontParser;
import br.ol.rollerball.renderer3d.shader.GouraudShaderWithTexture;
import static br.ol.rollerball.game.RollerBallScene.State.*;

/**
 * TargetWall class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class TargetWall extends RollerBallEntity {
    
    private final String targetWallName;
    private WallBody wallBody;
    private double angle;
    private double pivotX;
    private double pivotY;
    
    public TargetWall(String name, RollerBallScene scene, String targetWallName) {
        super(name, scene);
        this.targetWallName = targetWallName;
    }

    @Override
    public void init() throws Exception {
        mesh = WavefrontParser.load("/res/target.obj", 70);
        wallBody = (WallBody) gameModel.getBodyByName(targetWallName);
        StaticLine sl = (StaticLine) wallBody.getShape();
        PhysicsVec2 v = new PhysicsVec2();
        v.set(sl.getP2());
        v.sub(sl.getP1());
        v.normalize();
        angle = Math.atan2(v.getX(), v.getY()) + Math.toRadians(90);
        pivotX = (sl.getP1().getX() + sl.getP2().getX()) / 2;
        pivotY = (sl.getP1().getY() + sl.getP2().getY()) / 2;
        setVisible(true);
    }
    
    @Override
    public void update(Renderer renderer) {
        transform.setIdentity();
        double ty = wallBody.isVisible() ? 0 : -15;
        transform.translate(pivotX, ty, pivotY);
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
