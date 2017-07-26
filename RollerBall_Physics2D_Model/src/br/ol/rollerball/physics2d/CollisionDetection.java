package br.ol.rollerball.physics2d;

/**
 * CollisionDetection class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class CollisionDetection {
    
    private static final PhysicsVec2 auxVec = new PhysicsVec2();
    private static final PhysicsVec2 auxVec2 = new PhysicsVec2();
    private static final PhysicsVec2 auxVec3 = new PhysicsVec2();
    
    public static boolean checkCollisionCircleCircle(RigidBody rba, RigidBody rbb, Contact contact) {
        contact.setRba(rba);
        contact.setRbb(rbb);
        Circle csa = (Circle) rba.getShape();
        Circle csb = (Circle) rbb.getShape();
        auxVec.set(rba.getPosition());
        auxVec.sub(rbb.getPosition());
        double penetration = csa.getRadius() + csb.getRadius() - auxVec.getLength();
        if (penetration > 0) {
            contact.getNormal().set(auxVec);
            contact.getNormal().normalize();
            auxVec.scale(csb.getRadius() / (csa.getRadius() + csb.getRadius()));
            auxVec.add(rbb.getPosition());
            contact.getPosition().set(auxVec);
            contact.setPenetration(penetration);
        }
        return penetration > 0;
    }
    
    public static boolean checkCollisionCircleStaticLine(RigidBody rba, RigidBody rbb, Contact contact) {
        contact.setRba(rbb);
        contact.setRbb(rba);
        Circle csa = (Circle) rba.getShape();
        StaticLine lsb = (StaticLine) rbb.getShape();
        auxVec.set(lsb.getP1());
        auxVec.sub(lsb.getP2());
        auxVec2.set(lsb.getP1());
        auxVec2.sub(rba.getPosition());
        double p1 = auxVec2.dot(auxVec);
        auxVec3.set(lsb.getP2());
        auxVec3.sub(rba.getPosition());
        double p2 = auxVec3.dot(auxVec);
        if (p1 * p2 < 0) {
            auxVec.setPerp();
            contact.getNormal().set(auxVec);
            contact.getNormal().normalize();
            contact.getNormal().scale(-1);
            contact.getPosition().set(contact.getNormal());
            contact.getPosition().scale(csa.getRadius());
            contact.getPosition().add(rba.getPosition());
            double penetration = csa.getRadius() - Math.abs(contact.getNormal().dot(auxVec2));
            contact.setPenetration(penetration);
            return penetration > 0;
        }
        else {
            double penetration = csa.getRadius() - auxVec2.getLength();
            if (penetration > 0) {
                contact.getNormal().set(auxVec2);
                contact.getNormal().normalize();
                contact.setPenetration(penetration);
                contact.getPosition().set(lsb.getP1());
                return true;
            }
            penetration = csa.getRadius() - auxVec3.getLength();
            if (penetration > 0) {
                contact.getNormal().set(auxVec3);
                contact.getNormal().normalize();
                contact.setPenetration(penetration);
                contact.getPosition().set(lsb.getP2());
                return true;
            }
        }
        return false;
    }
    
}
