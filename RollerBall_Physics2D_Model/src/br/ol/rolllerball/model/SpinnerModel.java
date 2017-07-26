package br.ol.rolllerball.model;

import br.ol.rollerball.physics2d.Contact;
import br.ol.rollerball.physics2d.ContactListener;
import br.ol.rollerball.physics2d.PhysicsTime;
import br.ol.rollerball.physics2d.RigidBody;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 * SpinnerModel class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class SpinnerModel implements ContactListener<WallBody> {
    
    private final String name;
    private final RollerBallGameModel model;
    private final WallBody sensorTop;
    private final WallBody sensorBottom;
    private double torque;
    private double angle;
    private double points;
    
    private final double staticX;
    private final double staticY;
    
    public SpinnerModel(String name, RollerBallGameModel model, double x1, double y1, double x2, double y2) {
        this.name = name;
        this.model = model;
        sensorTop = new WallBody(name + "_sensor_top", model, x1, y1, x2, y2);
        sensorBottom = new WallBody(name + "_sensor_bottom", model, x2, y2, x1, y1);
        sensorTop.setSensor(true);
        sensorBottom.setSensor(true);
        sensorTop.addListener(this);
        sensorBottom.addListener(this);
        staticX = (x1 + x2) / 2;
        staticY = (y1 + y2) / 2;
    }

    public String getName() {
        return name;
    }

    public RollerBallGameModel getModel() {
        return model;
    }

    public WallBody getSensorTop() {
        return sensorTop;
    }

    public WallBody getSensorBottom() {
        return sensorBottom;
    }

    public double getTorque() {
        return torque;
    }

    public double getAngle() {
        return angle;
    }

    public double getStaticX() {
        return staticX;
    }

    public double getStaticY() {
        return staticY;
    }

    public void update() {
        torque = torque * 0.97;
        if (Math.abs(torque) < 1) {
            torque = 0;
        }
        double da = torque * PhysicsTime.getDelta() * 0.00000000005; 
        angle += da;
        points += Math.abs(da * 0.5);
        while (points > 1) {
            model.addScore(1);
            points -= 1;
        }
    }
    

    public void drawDebug(Graphics2D g) {
        // super.drawDebug(g); 
        AffineTransform at = g.getTransform();
        g.translate(staticX, staticY);
        g.rotate(angle);
        g.drawLine(-20, 0, 20, 0);
        g.setTransform(at);
    }
    
    // --- ContactListener implementation ---

    @Override
    public void onCollision(RigidBody rb, Contact contact) {
        torque = contact.getNormal().dot(rb.getVelocity());
        //System.out.println("onCollision torque=" + torque);
    }
    
    @Override
    public void onCollisionEnter(RigidBody rb, Contact contact) {
    }

    @Override
    public void onCollisionOut(RigidBody rb, Contact contact) {
    }
    
}
