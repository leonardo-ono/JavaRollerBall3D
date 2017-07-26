package br.ol.rollerball.game.entitity;

import br.ol.g2d.Animation;
import br.ol.rollerball.game.RollerBallEntity;
import br.ol.rollerball.game.RollerBallScene;
import br.ol.rollerball.math.MathUtil;
import br.ol.rollerball.renderer3d.core.Renderer;
import br.ol.rollerball.renderer3d.parser.wavefront.WavefrontParser;
import br.ol.rollerball.renderer3d.shader.GouraudShaderWithTexture;
import static br.ol.rollerball.game.RollerBallScene.State.*;
import br.ol.rollerball.game.infra.Time;
import java.awt.Graphics2D;

/**
 * OLPresents class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class OLPresents extends RollerBallEntity {
    
    private double synchronizationP = 1.0;
    private final double translateZ = 3800;
    private final double translateY = 180;
    private final double rotateX = Math.toRadians(-1);
    
    private boolean presentsAnimationPlayed;
    private Animation presentsAnimation;
    
    public OLPresents(String name, RollerBallScene scene) {
        super(name, scene);
    }

    public double getSynchronizationP() {
        return synchronizationP;
    }

    @Override
    public void init() throws Exception {
        mesh = WavefrontParser.load("/res/ol_presents.obj", 200);
        presentsAnimation = scene.getG2D().getAnimations().get("presents");
        presentsAnimation.stop();
        presentsAnimation.currentFrameIndex = 0;
        setVisible(true);
    }

    @Override
    public void updateOlPresents(Renderer renderer) {
        synchronizationP -= Time.delta * 0.0000000004;
        synchronizationP = MathUtil.clamp(synchronizationP, 0, 1);

        transform.setIdentity();
        transform.translate(0, translateZ * synchronizationP, translateY * synchronizationP);
        transform.rotateX(rotateX);
        
        if (synchronizationP == 0 && !presentsAnimationPlayed) {
            presentsAnimation.play();
            presentsAnimationPlayed = true;
        }
        
        long timeDelta = Time.delta / 1500000;
        presentsAnimation.update(timeDelta);
        
        if (presentsAnimationPlayed && !presentsAnimation.playing) {
            scene.changeState(TITLE);
        }
    }

    @Override
    public void preDraw(Renderer renderer) {
        super.preDraw(renderer); 
        GouraudShaderWithTexture.minIntensity = Math.max(0.25, 1 - synchronizationP);
    }

    @Override
    public void draw(Renderer renderer, Graphics2D g) {
        if (visible) {
            presentsAnimation.draw(g);
        }
    }
    
    // broadcast messages

    @Override
    public void stateChanged() {
        setVisible(scene.getState() == OL_PRESENTS);
    }
    
}
