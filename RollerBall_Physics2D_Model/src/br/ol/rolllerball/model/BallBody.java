package br.ol.rolllerball.model;

import br.ol.rollerball.physics2d.Circle;
import br.ol.rollerball.physics2d.RigidBody;

/**
 * Circle class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class BallBody extends RigidBody {
    
    private static final int RADIUS = 14;
    protected RollerBallGameModel model;
    protected boolean held; // held by some BallHolderBody ?
    
    public BallBody(String name, RollerBallGameModel model, double x, double y) {
        super(name, model.getWorld(), new Circle(RADIUS), true);
        this.model = model;
        getVelocity().set(0, 0);
        getPosition().set(x, y);
        setMass(0.07 * RADIUS);
    }

    public boolean isHeld() {
        return held;
    }

    void setHeld(boolean held) {
        this.held = held;
    }

    @Override
    public void update() {
        setVisible(!model.isGameOver());
    }

    @Override
    public void updatePosition() {
        if (isVisible()) {
            super.updatePosition();
        }
    }

    @Override
    public void updateVelocity() {
        if (isVisible()) {
            super.updateVelocity(); 
        }
    }
    
}
