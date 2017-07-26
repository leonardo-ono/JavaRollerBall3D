package br.ol.rollerball.game.entitity;

import br.ol.rolllerball.model.BallBody;
import br.ol.rollerball.game.RollerBallEntity;
import br.ol.rollerball.game.RollerBallScene;
import br.ol.rollerball.renderer3d.core.Renderer;
import br.ol.rollerball.renderer3d.parser.wavefront.WavefrontParser;
import br.ol.rollerball.renderer3d.shader.GouraudShaderWithTexture;
import static br.ol.rollerball.game.RollerBallScene.State.*;
import br.ol.rollerball.game.infra.Time;

/**
 * Ball class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class Ball extends RollerBallEntity {
    
    private BallBody ballBody;
    
    public Ball(String name, RollerBallScene scene) {
        super(name, scene);
    }

    @Override
    public void init() throws Exception {
        mesh = WavefrontParser.load("/res/ball.obj", 70);
        ballBody = (BallBody) gameModel.getBodyByName("ball_1");
        setVisible(true);
    }
    
    @Override   
    public void update(Renderer renderer) {
        setVisible(ballBody.isVisible());
        transform.setIdentity();
        int by = ballBody.isHeld() ? 0 : 10;
        transform.translate(ballBody.getPosition().getX(), by, ballBody.getPosition().getY());
        
        // check if still alive
        if (ballBody.getPosition().getY() > 4000 && scene.getState() == PLAYING) {
            scene.showBonusScreen();
        }
    }
    
    @Override
    public void preDraw(Renderer renderer) {
        super.preDraw(renderer);
        GouraudShaderWithTexture.minIntensity = 0.5;
        GouraudShaderWithTexture.scale = 1.25;
    }
    
    // broadcast messages

    @Override
    public void stateChanged() {
        // do nothing
    }
    
}
