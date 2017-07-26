package br.ol.rollerball.physics2d;

/**
 * ContactListener class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public interface ContactListener<T extends RigidBody> {

    public void onCollisionEnter(RigidBody rb, Contact contact);
    public void onCollision(RigidBody rb, Contact contact);
    public void onCollisionOut(RigidBody rb, Contact contact);
    
}
