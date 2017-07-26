package br.ol.rollerball.game.entitity;

import br.ol.rolllerball.model.ButtonBody;
import br.ol.rolllerball.model.SlingshotBody;
import br.ol.rollerball.physics2d.Contact;
import br.ol.rollerball.physics2d.ContactListener;
import br.ol.rollerball.physics2d.RigidBody;
import br.ol.rollerball.physics2d.StaticLine;
import br.ol.rollerball.game.RollerBallEntity;
import br.ol.rollerball.game.RollerBallScene;
import br.ol.rollerball.renderer3d.core.Renderer;
import br.ol.rollerball.renderer3d.parser.wavefront.WavefrontParser;
import br.ol.rollerball.renderer3d.shader.FlatShader;
import java.awt.Color;
import static br.ol.rollerball.game.RollerBallScene.State.*;

/**
 * Button class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class Button extends RollerBallEntity implements ContactListener<SlingshotBody> {
    
    private final int index;
    private final Color color;
    private final String buttonName;
    private ButtonBody buttonBody;
    private double angle;
    
    public Button(String name, RollerBallScene scene, String buttonName) {
        super(name, scene);
        this.buttonName = buttonName;
        this.index = Integer.parseInt(buttonName.split(":")[1]);
        Color[] colorsTmp = new Color[] { Color.YELLOW, Color.RED, Color.GREEN, Color.GREEN, Color.RED, Color.YELLOW, Color.GREEN, Color.GREEN };
        color = colorsTmp[this.index - 1];
    }

    @Override
    public void init() throws Exception {
        mesh = WavefrontParser.load("/res/button.obj", 70);
        buttonBody = (ButtonBody) gameModel.getBodyByName(buttonName);
        buttonBody.addListener(this);
        StaticLine sl = (StaticLine) buttonBody.getShape();
        angle = Math.atan2(sl.getP1().getY() - sl.getP2().getY(), sl.getP1().getX() - sl.getP2().getX()); 
        setVisible(true);
    }
    
    @Override
    public void update(Renderer renderer) {
        transform.setIdentity();
        transform.translate(buttonBody.getPivotX(), 10, buttonBody.getPivotY());
        transform.rotateY(angle);
    }

    @Override
    public void preDraw(Renderer renderer) {
        super.preDraw(renderer);
        renderer.setShader(RollerBallScene.flatShader);
        RollerBallScene.flatShader.setColor(255, color.getRed(), color.getGreen(), color.getBlue());
        FlatShader.minIntensity = 0.75;
    }

    // --- ContactListener implementation ---
    
    @Override
    public void onCollisionEnter(RigidBody rb, Contact contact) {
        scene.illuminate(color);
    }

    @Override
    public void onCollision(RigidBody rb, Contact contact) {
    }

    @Override
    public void onCollisionOut(RigidBody rb, Contact contact) {
    }
    
    // broadcast messages

    @Override
    public void stateChanged() {
        if (scene.getState() == TITLE) {
            setVisible(true);
        }
    }
    
}
