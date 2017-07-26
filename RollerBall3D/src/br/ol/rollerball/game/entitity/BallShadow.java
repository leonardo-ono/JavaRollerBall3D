package br.ol.rollerball.game.entitity;

import br.ol.rolllerball.model.BallBody;
import br.ol.rollerball.game.RollerBallEntity;
import br.ol.rollerball.game.RollerBallScene;
import br.ol.rollerball.renderer3d.core.Renderer;
import br.ol.rollerball.renderer3d.parser.wavefront.WavefrontParser;

/**
 * BallShadow class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class BallShadow extends RollerBallEntity {
    
    private BallBody ballBody;
    
    public BallShadow(String name, RollerBallScene scene) {
        super(name, scene);
    }

    @Override
    public void init() throws Exception {
        mesh = WavefrontParser.load("/res/ball_shadow.obj", 150);
        ballBody = (BallBody) gameModel.getBodyByName("ball_1");
        setVisible(true);
    }
    
    @Override   
    public void update(Renderer renderer) {
        setVisible(ballBody.isVisible());
        transform.setIdentity();
        transform.translate(ballBody.getPosition().getX(), 20, ballBody.getPosition().getY() + 4);
    }
    
    @Override
    public void preDraw(Renderer renderer) {
        super.preDraw(renderer);
        renderer.setShader(RollerBallScene.shadowShader);
    }
    
    // broadcast messages

    @Override
    public void stateChanged() {
        // do nothing
    }
    
}
