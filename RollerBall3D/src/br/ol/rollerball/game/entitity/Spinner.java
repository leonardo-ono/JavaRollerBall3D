package br.ol.rollerball.game.entitity;

import br.ol.rollerball.physics2d.StaticLine;
import br.ol.rollerball.physics2d.PhysicsVec2;
import br.ol.rollerball.game.RollerBallEntity;
import br.ol.rollerball.game.RollerBallScene;
import br.ol.rollerball.renderer3d.core.Renderer;
import br.ol.rollerball.renderer3d.parser.wavefront.WavefrontParser;
import br.ol.rollerball.renderer3d.shader.FlatShader;
import br.ol.rollerball.renderer3d.shader.GouraudShaderWithTexture;
import br.ol.rolllerball.model.SpinnerModel;
import static br.ol.rollerball.game.RollerBallScene.State.*;

/**
 * Target class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class Spinner extends RollerBallEntity {
    
    private final SpinnerModel spinnerModel;
    private double angleY;
    
    public Spinner(String name, RollerBallScene scene, SpinnerModel spinnerModel) {
        super(name, scene);
        this.spinnerModel = spinnerModel;
    }

    @Override
    public void init() throws Exception {
        mesh = WavefrontParser.load("/res/spinner.obj", 70);
        StaticLine sl = (StaticLine) spinnerModel.getSensorTop().getShape();
        PhysicsVec2 v = new PhysicsVec2();
        v.set(sl.getP1());
        v.sub(sl.getP2());
        v.normalize();
        angleY = Math.atan2(v.getY(), v.getX());
        setVisible(true);
    }
    
    @Override
    public void update(Renderer renderer) {
        transform.setIdentity();
        transform.translate(spinnerModel.getStaticX(), 20, spinnerModel.getStaticY());
        transform.rotateY(angleY);
        transform.rotateX(spinnerModel.getAngle());
    }
    
    @Override
    public void preDraw(Renderer renderer) {
        super.preDraw(renderer);
        renderer.setBackfaceCullingEnabled(false);
        if (spinnerModel.getName().equals("spinner_2")) {
            // with texture
            GouraudShaderWithTexture.minIntensity = 0.5;
            GouraudShaderWithTexture.scale = 1.75;
        }
        else {
            // green
            renderer.setShader(RollerBallScene.flatShader);
            RollerBallScene.flatShader.setColor(255, 0, 255, 0);
            FlatShader.minIntensity = 0.85;
        }
    }
    
    // broadcast messages

    @Override
    public void stateChanged() {
        if (scene.getState() == TITLE) {
            setVisible(true);
        }
    }
    
}
