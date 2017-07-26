package br.ol.rollerball.game.entitity;

import br.ol.rolllerball.model.BumperBody;
import br.ol.rollerball.physics2d.Contact;
import br.ol.rollerball.physics2d.ContactListener;
import br.ol.rollerball.physics2d.RigidBody;
import br.ol.rollerball.game.RollerBallEntity;
import br.ol.rollerball.game.RollerBallScene;
import br.ol.rollerball.game.infra.Time;
import br.ol.rollerball.renderer3d.core.Renderer;
import br.ol.rollerball.renderer3d.parser.wavefront.WavefrontParser;
import br.ol.rollerball.renderer3d.shader.GouraudShaderWithTexture;
import static br.ol.rollerball.game.RollerBallScene.State.*;

/**
 * Bumper class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class Bumper extends RollerBallEntity implements ContactListener<BumperBody>{

    private final String bumperName;
    private BumperBody bumperBody;
    private double hitColorIntensity;
    
    public Bumper(String name, RollerBallScene scene, String bumper) {
        super(name, scene);
        this.bumperName = bumper;
    }

    @Override
    public void init() throws Exception {
        mesh = WavefrontParser.load("/res/bumper.obj", 70);
        bumperBody = (BumperBody) gameModel.getBodyByName(bumperName);
        bumperBody.addListener(this);
        transform.setIdentity();
        transform.translate(bumperBody.getPosition().getX(), 10, bumperBody.getPosition().getY());
        setVisible(true);
    }
    
    @Override
    public void update(Renderer renderer) {
        hitColorIntensity += Time.delta * 0.000000015;
        if (hitColorIntensity > 1) {
            hitColorIntensity = 1;
        }
    }

    @Override
    public void preDraw(Renderer renderer) {
        super.preDraw(renderer);
        GouraudShaderWithTexture.minIntensity = 1;
        double hitColorIntensityDouble = hitColorIntensity;
        hitColorIntensityDouble = hitColorIntensityDouble < 0 ? 0 : hitColorIntensityDouble;
        GouraudShaderWithTexture.maxIntensity = hitColorIntensityDouble;
    }

    // --- ContactListener implementation ---
    
    @Override
    public void onCollisionEnter(RigidBody rb, Contact contact) {
        hitColorIntensity = -0.2;
    }

    @Override
    public void onCollision(RigidBody rb, Contact contact) {
    }

    @Override
    public void onCollisionOut(RigidBody rb, Contact contact) {
    }
    
    // broadcast messages

    @Override
    public void stateChanged() {
        if (scene.getState() == TITLE) {
            setVisible(true);
        }
    }
    
}
