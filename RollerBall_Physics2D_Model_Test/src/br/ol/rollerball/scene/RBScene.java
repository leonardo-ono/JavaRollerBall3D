package br.ol.rollerball.scene;

import br.ol.ge.Configuration;
import br.ol.ge.Scene;
import br.ol.rollerball.entity.Ball;
import br.ol.rollerball.entity.PaddleLeft;
import br.ol.rollerball.entity.PaddleRight;
import br.ol.rolllerball.model.RollerBallGameModel;
import java.awt.Graphics2D;

/**
 *
 * @author leonardo
 */
public class RBScene extends Scene {
    
    private final RollerBallGameModel model = new RollerBallGameModel();
    
    public RBScene(Configuration configuration) {
        super(configuration);
    }

    public RollerBallGameModel getModel() {
        return model;
    }

    @Override
    public void init() {
        addEntity(new Ball(this, "ball_1"));
        //addEntity(new BallHolder(this, 250, 200));
        //addEntity(new Wall(this, 50, 400, 500, 450));
        //addEntity(new Bumper(this, 270, 200));
        //addEntity(new Slingshot(this, 550, 300, 650, 350));
        addEntity(new PaddleLeft(this, "paddle_left_1"));
        addEntity(new PaddleRight(this, "paddle_right_1"));
    }

    @Override
    public void updatePhysics() {
        model.update();
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawString("BALLS: " + model.getLives(), 200, 10);
        g.drawString("SCORE: " + model.getScore(), 200, 20);
        g.drawString("BONUS 1: " + model.getBonus(0), 200, 30);
        g.drawString("BONUS 2: " + model.getBonus(1), 200, 40);
        g.drawString("BONUS 3: " + model.getBonus(2), 200, 50);
        g.drawString("BONUS 4: " + model.getBonus(3), 200, 60);
        g.scale(0.275, 0.275);
        //g.scale(0.75, 0.75);
        super.draw(g);
        model.drawDebug(g);
    }
    
}
