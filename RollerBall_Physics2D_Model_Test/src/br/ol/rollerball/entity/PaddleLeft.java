package br.ol.rollerball.entity;

import br.ol.ge.Entity;
import br.ol.ge.Keyboard;
import br.ol.rollerball.scene.RBScene;
import br.ol.rolllerball.model.PaddleLeftBody;
import br.ol.rolllerball.model.SensorBody;
import br.ol.rolllerball.model.SlotWheelBody;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

/**
 *
 * @author leonardo
 */
public class PaddleLeft extends Entity<RBScene> {
    
    private final PaddleLeftBody model;
    
    public PaddleLeft(RBScene scene, String name) {
        super(scene);
        model = (PaddleLeftBody) getScene().getModel().getBodyByName(name);
    }

    @Override
    public void update() {
        if (Keyboard.isKeyPressed(KeyEvent.VK_Z)) {
            getScene().getModel().getTable().leftSensorGroup();
        }
        getScene().getModel().getTable().activateLeftPaddles(Keyboard.isKeyDown(KeyEvent.VK_Z));
        
        if (Keyboard.isKeyPressed(KeyEvent.VK_A)) {
            SensorBody s1 = (SensorBody) getScene().getModel().getBodyByName("sensor_1");
            SensorBody s2 = (SensorBody) getScene().getModel().getBodyByName("sensor_2");
            SensorBody s3 = (SensorBody) getScene().getModel().getBodyByName("sensor_3");
            s1.setActivated(true);
            s2.setActivated(true);
            s3.setActivated(true);
        }

        if (Keyboard.isKeyPressed(KeyEvent.VK_S)) {
            getScene().getModel().start();
        }

        getScene().getModel().getTable().getShooter().hold(Keyboard.isKeyDown(KeyEvent.VK_SPACE));

        if (Keyboard.isKeyPressed(KeyEvent.VK_1)) {
            SlotWheelBody slot1 = (SlotWheelBody) getScene().getModel().getBodyByName("slot_1");
            slot1.rotate();
        }
        else if (Keyboard.isKeyPressed(KeyEvent.VK_2)) {
            SlotWheelBody slot2 = (SlotWheelBody) getScene().getModel().getBodyByName("slot_2");
            slot2.rotate();
        }
        else if (Keyboard.isKeyPressed(KeyEvent.VK_3)) {
            SlotWheelBody slot3 = (SlotWheelBody) getScene().getModel().getBodyByName("slot_3");
            slot3.rotate();
        }
    }

    @Override
    public void draw(Graphics2D g) {
    }

}
