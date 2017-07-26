package br.ol.rollerball.game.entitity;

import br.ol.rolllerball.model.BallBody;
import br.ol.rollerball.game.RollerBallEntity;
import br.ol.rollerball.game.RollerBallScene;
import static br.ol.rollerball.game.RollerBallScene.State.GAME_START_PREPARATIONS;
import static br.ol.rollerball.game.RollerBallScene.State.TITLE;
import br.ol.rollerball.game.infra.Time;
import br.ol.rollerball.math.MathUtil;
import br.ol.rollerball.renderer3d.core.Renderer;

/**
 * Camera class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class Camera extends RollerBallEntity {
    
    private double currentAngleY;
    private double currentTranslateX = -378;
    private double currentTranslateZ = -2760;
    private final OLPresents olpresents;
    
    private int titleCameraDirection = 1;
    private double titleCameraTargetY;
    
    public Camera(String name, RollerBallScene scene, OLPresents olpresents) {
        super(name, scene);
        this.olpresents = olpresents;
    }
        
    @Override
    public void init() throws Exception {
        transform.setIdentity();
    }

    @Override
    public void updateOlPresents(Renderer renderer) {
        transform.setIdentity();
        double p = olpresents.getSynchronizationP();
        p = p > 0.60 ? 1 : p < 0.20 ? 0 : (p - 0.20) / 0.40;
        transform.rotateY(2.5 * p);
        transform.rotateX(Math.toRadians(-90));
    }
    
    @Override
    public void updateTitle(Renderer renderer) {
        transform.setIdentity();
        transform.rotateX(Math.toRadians(-45));
        titleCameraTargetY += titleCameraDirection * Time.delta * 0.00000002;
        if (titleCameraDirection < 0 && titleCameraTargetY < -2760) {
            titleCameraTargetY = -2760;
            titleCameraDirection = 1;
        }
        else if (titleCameraDirection > 0 && titleCameraTargetY > - 900) {
            titleCameraTargetY = -900;
            titleCameraDirection = -1;
        }
        currentTranslateX = currentTranslateX + (-380 - currentTranslateX) * 0.1;
        currentTranslateZ = currentTranslateZ + (titleCameraTargetY - currentTranslateZ) * 0.1;
        transform.translate(currentTranslateX, -550, currentTranslateZ);
    }

    @Override
    public void updateGameStartPreparations(Renderer renderer) {
        updateLookAtBall();
    }

    @Override
    public void updatePlaying(Renderer renderer) {
        updateLookAtBall();
    }

    private void updateLookAtBall() {
        double bx = 0;
        double by = 0;
        final BallBody ball = (BallBody) scene.getGameModel().getBodyByName("ball_1");
        double ballPositionX = MathUtil.clamp(ball.getPosition().getX(), 0, 700);
        ballPositionX = ball.getPosition().getY() > 2600 ? 350 : MathUtil.clamp(ballPositionX, 0, 700);
        bx = 50 * (ballPositionX / 700.0 - 0.5);
        by = ball.getPosition().getY();
        transform.setIdentity();
        transform.rotateX(Math.toRadians(-45));
        
        double targetRotationAngleY = -0.05 * (ballPositionX / 700.0 - 0.5);
        currentAngleY = currentAngleY + (targetRotationAngleY - currentAngleY) * 0.05;
        transform.rotateY(currentAngleY);

        double targetX = -380 - bx;
        double targetZ = -1000 - by + 200;
        
        if (ballPositionX > 630) {
            // just ignore
        }
        else if (targetZ > -1450) {
            targetZ = -950;
        }
        else if (targetZ > -2080) {
            targetZ = -1520;
        }
        else if (targetZ > -2650) {
            targetZ = -2100;
        }
        
        if (targetZ > -900) {
            targetZ = -900;
        }
        else if (targetZ < -2760) {
            targetZ = -2760;
        }
        
        currentTranslateX = currentTranslateX + (targetX - currentTranslateX) * 0.1;
        currentTranslateZ = currentTranslateZ + (targetZ - currentTranslateZ) * 0.1;
        transform.translate(currentTranslateX, -550, currentTranslateZ);
    }
    
    public void reset() {
        currentTranslateX = -378;
        currentTranslateZ = -2760;
    }

    // broadcast messages

    @Override
    public void stateChanged() {
        if (scene.getState() == TITLE) {
            titleCameraTargetY = - 2760;
            titleCameraDirection = 1;
        }
    }
    
}
