package br.ol.rollerball.physics2d;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

/**
 * RigidBody class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class RigidBody {
    
    protected String name;
    private final World world;
    private final Shape shape;
    private boolean visible = true;
    private boolean sensor = false;
    private boolean dynamic = true;
    private final PhysicsVec2 position = new PhysicsVec2();
    private final PhysicsVec2 force = new PhysicsVec2();
    private final PhysicsVec2 acceleration = new PhysicsVec2();
    private final PhysicsVec2 velocity = new PhysicsVec2();
    private final PhysicsVec2 ds = new PhysicsVec2();
    private double mass;
    protected double angle;
    private double restitution = 0.5;
    private final List<ContactListener> listeners = new ArrayList<ContactListener>();
    private final PhysicsVec2 vecTmp = new PhysicsVec2();
    
    public RigidBody(String name, World world, Shape shape, boolean dynamic) {
        this.name = name;
        this.world = world;
        this.shape = shape;
        this.dynamic = dynamic;
    }

    public String getName() {
        return name;
    }

    public World getWorld() {
        return world;
    }

    public Shape getShape() {
        return shape;
    }

    public boolean isDynamic() {
        return dynamic;
    }
    
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isSensor() {
        return sensor;
    }

    public void setSensor(boolean sensor) {
        this.sensor = sensor;
    }

    public double getRestitution() {
        return restitution;
    }

    public void setRestitution(double restitution) {
        this.restitution = restitution;
    }
    
    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public PhysicsVec2 getPosition() {
        return position;
    }
    
    public void addForce(PhysicsVec2 force) {
        this.force.add(force);
    }
    
    public PhysicsVec2 getForce() {
        return force;
    }

    public void applyImpulse(PhysicsVec2 impulse) {
        vecTmp.set(impulse);
        vecTmp.scale(1 / mass);
        velocity.add(vecTmp);
    }
    
    public PhysicsVec2 getAcceleration() {
        return acceleration;
    }

    public PhysicsVec2 getVelocity() {
        return velocity;
    }

    public PhysicsVec2 getVelocity(Contact contact) {
        return velocity;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void addListener(ContactListener listener) {
        listeners.add(listener);
    }

    public void updateVelocity() {
        double dt = PhysicsTime.getDelta() / 1000000000.0;
        force.add(getWorld().getGravity());
        // linear motion
        acceleration.set(force);
        acceleration.scale(dt / mass);
        velocity.add(acceleration);
    }
    
    public void update() {
    }
    
    public void updatePosition() {
        double dt = PhysicsTime.getDelta() / 1000000000.0;
        ds.set(velocity);
        ds.scale(dt);
        getPosition().add(ds);
        
        // clear net force
        force.set(0, 0);
    }

    public void drawDebug(Graphics2D g) {
        if (shape != null) {
            AffineTransform at = g.getTransform();
            g.translate(getPosition().getX(), getPosition().getY());
            g.rotate(angle);
            shape.drawDebug(g);
            g.setTransform(at);
        }
    }

    // --- ContactListener implementation ---
    
    public void onCollisionEnter(RigidBody rb, Contact contact) {
        for (ContactListener listener : listeners) {
            listener.onCollisionEnter(rb, contact);
        }
    }

    public void onCollision(RigidBody rb, Contact contact) {
        for (ContactListener listener : listeners) {
            listener.onCollision(rb, contact);
        }
    }

    public void onCollisionOut(RigidBody rb, Contact contact) {
        for (ContactListener listener : listeners) {
            listener.onCollisionOut(rb, contact);
        }
    }
    
    // ---
    
    @Override
    public String toString() {
        return "RigidBody{" + "name=" + name + ", visible=" + visible 
                + ", sensor=" + sensor + ", dynamic=" + dynamic + ", position=" 
                + position + ", mass=" + mass + ", angle=" + angle 
                + ", restitution=" + restitution + '}';
    }
    
}
