package br.ol.rollerball.game.entitity;

import br.ol.rolllerball.model.SlingshotBody;
import br.ol.rollerball.physics2d.Contact;
import br.ol.rollerball.physics2d.ContactListener;
import br.ol.rollerball.physics2d.RigidBody;
import br.ol.rollerball.game.RollerBallEntity;
import br.ol.rollerball.game.RollerBallScene;
import br.ol.rollerball.game.infra.Time;
import br.ol.rollerball.renderer3d.core.Renderer;
import br.ol.rollerball.renderer3d.parser.wavefront.WavefrontParser;
import br.ol.rollerball.renderer3d.shader.FlatShader;
import static br.ol.rollerball.game.RollerBallScene.State.*;

/**
 * Slingshot class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class Slingshot extends RollerBallEntity implements ContactListener<SlingshotBody> {
    
    private final String slingshotName;
    private SlingshotBody slingshotBody;
    
    private final double HIT_COLOR_MIN = 64;
    private double hitColor = HIT_COLOR_MIN;
    
    public Slingshot(String name, RollerBallScene scene, String slingshotName) {
        super(name, scene);
        this.slingshotName = slingshotName;
    }

    @Override
    public void init() throws Exception {
        String resourceObjBaseName = slingshotName.split(":")[0];
        mesh = WavefrontParser.load("/res/" + resourceObjBaseName + ".obj", 70);
        slingshotBody = (SlingshotBody) gameModel.getBodyByName(slingshotName);
        slingshotBody.addListener(this);
        setVisible(true);
    }
    
    @Override
    public void update(Renderer renderer) {
        transform.setIdentity();
        transform.translate(slingshotBody.getPivotX(), 0, slingshotBody.getPivotY());
        hitColor -= Time.delta * 0.0000015;
        if (hitColor < HIT_COLOR_MIN) {
            hitColor = HIT_COLOR_MIN;
        }
    }

    @Override
    public void preDraw(Renderer renderer) {
        super.preDraw(renderer);
        renderer.setShader(RollerBallScene.flatShader);
        int hitColorInt = (int) hitColor;
        hitColorInt = hitColorInt > 255 ? 255 : hitColorInt;
        RollerBallScene.flatShader.setColor(255, 255, hitColorInt, hitColorInt);
        FlatShader.minIntensity = 0.5 + 0.5 * ((hitColorInt - HIT_COLOR_MIN) / (255 - HIT_COLOR_MIN));
    }

    // --- ContactListener implementation ---
    
    @Override
    public void onCollisionEnter(RigidBody rb, Contact contact) {
        hitColor = 300;
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
