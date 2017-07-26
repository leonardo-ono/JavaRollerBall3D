package br.ol.rollerball.physics2d;

/**
 * Contact class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class Contact {

    private RigidBody rba;
    private RigidBody rbb;
    private double penetration;
    private final PhysicsVec2 position = new PhysicsVec2();
    private final PhysicsVec2 normal = new PhysicsVec2();
    private final PhysicsVec2 auxVec = new PhysicsVec2();

    public Contact() {
    }

    public Contact(RigidBody rba, RigidBody rbb) {
        this.rba = rba;
        this.rbb = rbb;
    }

    public Contact(RigidBody rba, RigidBody rbb, PhysicsVec2 position, PhysicsVec2 normal, double penetration) {
        this.rba = rba;
        this.rbb = rbb;
        this.position.set(position);
        this.normal.set(normal);
        this.penetration = penetration;
    }

    public RigidBody getRba() {
        return rba;
    }

    public void setRba(RigidBody rba) {
        this.rba = rba;
    }

    public RigidBody getRbb() {
        return rbb;
    }

    public void setRbb(RigidBody rbb) {
        this.rbb = rbb;
    }

    public double getPenetration() {
        return penetration;
    }

    public void setPenetration(double penetration) {
        this.penetration = penetration;
    }

    public PhysicsVec2 getPosition() {
        return position;
    }

    public PhysicsVec2 getNormal() {
        return normal;
    }
    
    public void resolveCollision() {
        if (rba.isSensor() || rbb.isSensor()) {
            return;
        }
        auxVec.set(rba.getVelocity(this));
        auxVec.sub(rbb.getVelocity(this));
        if (auxVec.dot(normal) >= 0) {
            return;
        }
        double restitution = Math.min(rba.getRestitution(), rbb.getRestitution());
        double j = -(1 + restitution) * auxVec.dot(normal);
        double totalMass = 1 / rba.getMass() + 1 / rbb.getMass();
        j /= totalMass;
        auxVec.set(normal);
        auxVec.scale(j);
        rba.applyImpulse(auxVec);
        
        auxVec.scale(-1);
        rbb.applyImpulse(auxVec);
    }
    
    public void correctPosition() {
        if (rba.isSensor() || rbb.isSensor()) {
            return;
        }
        double totalMass = rba.getMass() + rba.getMass();
        double r1 = rbb.getMass() / totalMass;
        double r2 = rba.getMass() / totalMass;
        auxVec.set(normal);
        auxVec.scale(penetration * r1 * 0.2);
        rba.getPosition().add(auxVec);
        auxVec.set(normal);
        auxVec.scale(-penetration * r2 * 0.2);
        rbb.getPosition().add(auxVec);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Contact other = (Contact) obj;
        if (this.rba == other.rba && this.rbb == other.rbb) {
            return true;
        }
        else if (this.rbb == other.rba && this.rba == other.rbb) {
            return true;
        }
        return false;
    }
    
}
