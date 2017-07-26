package br.ol.ge;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author leonardo
 */
public class Scene {
    
    private Configuration configuration;
    private final List<Entity> entities = new ArrayList<Entity>();
    private final List<Entity> entitiesAdd = new ArrayList<Entity>();

    public Scene(Configuration configuration) {
        this.configuration = configuration;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public List<Entity> getEntities() {
        return entities;
    }
    
    public void addEntity(Entity entity) {
        entitiesAdd.add(entity);
        entity.init();
    }
    
    void internalInit() {
        init();
        initAllEntities();
    }
    
    public  void init() {
    }

    public void initAllEntities() {
        for (Entity entity : entities) {
            entity.init();
        }
    }
    
    public void internalUpdate() {
        update();
        updatePhysics();
        if (!entitiesAdd.isEmpty()) {
            entities.addAll(entitiesAdd);
            entitiesAdd.clear();
        }
    }
    
    public void update() {
        for (Entity entity : entities) {
            entity.update();
        }
    }

    public void updatePhysics() {
    }

    public void draw(Graphics2D g) {
        for (Entity entity : entities) {
            entity.draw(g);
        }
    }

}
