package br.ol.rollerball.physics2d;

/**
 * ContactAdapter class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public abstract class ContactAdapter<T extends RigidBody> implements ContactListener {
    
    protected T owner;

    public ContactAdapter(T t) {
        this.owner = t;
    }
    
    @Override
    public void onCollisionEnter(RigidBody rb, Contact contact) {
    }
    
    @Override
    public void onCollision(RigidBody rb, Contact contact) {
    }
    
    @Override
    public void onCollisionOut(RigidBody rb, Contact contact) {
    }

}
