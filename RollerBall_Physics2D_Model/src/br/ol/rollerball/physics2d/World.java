package br.ol.rollerball.physics2d;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * World class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class World {
    
    private final PhysicsVec2 gravity = new PhysicsVec2(0, 300);
    private final List<Contact> contacts = new ArrayList<Contact>();
    private final List<Contact> contactsCache = new ArrayList<Contact>();
    
    private final List<RigidBody> dynamicBodies = new ArrayList<RigidBody>();
    private final List<RigidBody> allBodies = new ArrayList<RigidBody>();
    
    // workaround to speed up physics simulation for this game
    private RigidBody ballBody;
    
    public World() {
    }

    // workaround to speed up physics simulation for this game
    public RigidBody getBallBody() {
        return ballBody;
    }

    // workaround to speed up physics simulation for this game
    public void setBallBody(RigidBody rigidBody2) {
        this.ballBody = rigidBody2;
    }

    public PhysicsVec2 getGravity() {
        return gravity;
    }

    public List<RigidBody> getAllBodies() {
        return allBodies;
    }

    public List<RigidBody> getDynamicBodies() {
        return dynamicBodies;
    }

    public void addRigidBody(RigidBody rb) {
        allBodies.add(rb);
        if (rb.isDynamic()) {
            dynamicBodies.add(rb);
        }
    }

    public void update() {
        // avoiding tunneling issue using very small time steps :D
        long originalDelta = PhysicsTime.delta;
        int div = 10;
        PhysicsTime.delta = PhysicsTime.delta / div;
        for (int r = 0; r < div; r++) {
            updateInternal();
        }
        PhysicsTime.delta = originalDelta;
    }
    
    private void updateInternal() {
        handleOnCollisionOut();
        handleOnCollisionEnter();
        for (RigidBody rb : dynamicBodies) {
            rb.updateVelocity();
        }
        for (Contact contact : contacts) {
            contact.resolveCollision();
        }
        for (RigidBody rb : dynamicBodies) {
            rb.update();
        }
        for (RigidBody rb : dynamicBodies) {
            rb.updatePosition();
        }
        for (Contact contact : contacts) {
            contact.correctPosition();
        }
    }
    
    private void handleOnCollisionOut() {
        Iterator<Contact> icontact = contacts.iterator();
        while (icontact.hasNext()) {
            Contact contact = icontact.next();
            RigidBody rb1 = contact.getRba();
            RigidBody rb2 = contact.getRbb();
            if (rb1.getShape() instanceof Circle && rb2.getShape() instanceof Circle 
                    && !CollisionDetection.checkCollisionCircleCircle(rb1, rb2, contact)) {
                
                icontact.remove();
                onCollisionOut(contact);
                saveContactToCache(contact);
            }
            else if (rb1.getShape() instanceof Circle && rb2.getShape() instanceof StaticLine 
                    && !CollisionDetection.checkCollisionCircleStaticLine(rb1, rb2, contact)) {
                
                icontact.remove();
                onCollisionOut(contact);
                saveContactToCache(contact);
            }
            else if (rb2.getShape() instanceof Circle && rb1.getShape() instanceof StaticLine 
                    && !CollisionDetection.checkCollisionCircleStaticLine(rb2, rb1, contact)) {
                
                icontact.remove();
                onCollisionOut(contact);
                saveContactToCache(contact);
            }
        }
    }
    
    // handle onCollision & onCollisionEnter 
    private void handleOnCollisionEnter() {
        for (RigidBody rb1 : allBodies) {
            
            // workaround to speed up physics simulation for this game
            RigidBody rb2 = ballBody;
            
            //for (RigidBody rb2 : dynamicBodies) {
                if (!rb1.isDynamic() && !rb2.isDynamic()) {
                    continue;
                }
                if (rb1 == rb2 || !rb1.isVisible() || !rb2.isVisible()) {
                    continue;
                }
                Contact contact = getContactFromCache();
                if (rb1.getShape() instanceof Circle && rb2.getShape() instanceof Circle 
                        && CollisionDetection.checkCollisionCircleCircle(rb1, rb2, contact)) {

                    onCollision(contact);
                    if (!contacts.contains(contact)) {
                        contacts.add(contact);
                        onCollisionEnter(contact);
                    }
                }
                else if (rb1.getShape() instanceof Circle && rb2.getShape() instanceof StaticLine 
                        && CollisionDetection.checkCollisionCircleStaticLine(rb1, rb2, contact)) {

                    onCollision(contact);
                    if (!contacts.contains(contact)) {
                        contacts.add(contact);
                        onCollisionEnter(contact);
                    }
                }
                else if (rb2.getShape() instanceof Circle && rb1.getShape() instanceof StaticLine 
                        && CollisionDetection.checkCollisionCircleStaticLine(rb2, rb1, contact)) {

                    onCollision(contact);
                    if (!contacts.contains(contact)) {
                        contacts.add(contact);
                        onCollisionEnter(contact);
                    }
                }
            //}
        }
    }
    
    public void drawDebug(Graphics2D g) {
        for (RigidBody rb : allBodies) {
            if (rb.isVisible()) {
                rb.drawDebug(g);
            }
        }
    }
    
    // fire contact listeners 
    
    public void onCollisionEnter(Contact contact) {
        contact.getRba().onCollisionEnter(contact.getRbb(), contact);
        contact.getRbb().onCollisionEnter(contact.getRba(), contact);
    }

    public void onCollision(Contact contact) {
        contact.getRba().onCollision(contact.getRbb(), contact);
        contact.getRbb().onCollision(contact.getRba(), contact);
    }

    public void onCollisionOut(Contact contact) {
        contact.getRba().onCollisionOut(contact.getRbb(), contact);
        contact.getRbb().onCollisionOut(contact.getRba(), contact);
    }

    // contacts cache
    
    public Contact getContactFromCache() {
        if (contactsCache.isEmpty()) {
            return new Contact();
        }
        Contact contact = contactsCache.remove(contactsCache.size() - 1);
        return contact;
    }
    
    public void saveContactToCache(Contact contact) {
        contact.setRba(null);
        contact.setRbb(null);
        contactsCache.add(contact);
    }
    
}
