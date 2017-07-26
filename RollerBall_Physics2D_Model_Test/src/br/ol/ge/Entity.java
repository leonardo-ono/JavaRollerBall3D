package br.ol.ge;

import br.ol.rollerball.physics2d.PhysicsVec2;
import java.awt.Graphics2D;

/**
 *
 * @author leonardo
 * @param <T>
 */
public class Entity<T extends Scene> {
    
    private final T scene;
    private final PhysicsVec2 position = new PhysicsVec2();
    
    public Entity(T scene) {
        this.scene = scene;
    }
    
    public void init() {
    }
    
    public T getScene() {
        return scene;
    }

    public PhysicsVec2 getPosition() {
        return position;
    }
    
    public void update() {
    }

    public void draw(Graphics2D g) {
    }
    
}
